/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Ordinastie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.malisis.demo.lavapool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.malisis.core.util.AABBUtils;
import net.malisis.core.util.BlockPosUtils;
import net.malisis.core.util.MBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author Ordinastie
 *
 */
public class LavaPoolTileEntity extends TileEntity implements IUpdatePlayerListBox
{
	private static FiniteLava lava = LavaPoolDemo.finiteLava;
	private boolean active = false;
	private boolean empty = false;
	private List<BlockPos> fillable = new ArrayList<>();
	private List<BlockPos> emptyable = new ArrayList<>();
	private BlockPos lastPos;

	public boolean startAnim = false;

	public void setActive(boolean active)
	{
		this.active = active;
		this.empty = !active;
		worldObj.markBlockForUpdate(pos);;
	}

	public boolean isActive()
	{
		return active;
	}

	public void getFillable()
	{
		fillable.clear();
		for (BlockPos p : BlockPosUtils.getAllInBox(AABBUtils.offset(pos, new AxisAlignedBB(-1, 0, -1, 2, 1, 2))))
		{
			MBlockState state = new MBlockState(worldObj, p);
			int a = lava.getAmount(state);
			if (a != -1 && a != 15)
				fillable.add(p);
		}
	}

	public void getEmptyable()
	{
		emptyable.clear();
		for (BlockPos p : BlockPosUtils.getAllInBox(AABBUtils.offset(pos, new AxisAlignedBB(-1, 0, -1, 2, 1, 2))))
		{
			MBlockState state = new MBlockState(worldObj, p);
			int a = lava.getAmount(state);
			if (a > 0)
				emptyable.add(p);
		}
	}

	@Override
	public void update()
	{
		//	MalisisCore.message(active + " > " + empty);
		Random rand = worldObj.rand;
		if (worldObj.isRemote)
		{
			int x = pos.getX(), z = pos.getZ();
			if (lastPos != null && !active)
			{
				x = lastPos.getX();
				z = lastPos.getZ();
			}
			if (active || empty)
				worldObj.spawnParticle(empty ? EnumParticleTypes.CRIT : EnumParticleTypes.FLAME, x + rand.nextFloat(), pos.getY() + 1.1F, z
						+ rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}

		if (!worldObj.isRemote)
		{
			if (empty && (worldObj.getTotalWorldTime() % 10) == 0)
				empty();

			if (active && (worldObj.getTotalWorldTime() % 10) == 0)
				fillAll();
		}
	}

	public void empty()
	{
		Random rand = worldObj.rand;
		getEmptyable();
		if (emptyable.size() == 0)
		{
			empty = false;
			worldObj.markBlockForUpdate(pos);
			return;
		}

		BlockPos pos = emptyable.get(rand.nextInt(emptyable.size()));
		MBlockState state = new MBlockState(worldObj, pos);
		lava.setAmount(worldObj, state, lava.getAmount(state) / 2);
		lastPos = pos;
		worldObj.markBlockForUpdate(pos);
	}

	public void fillAll()
	{
		getFillable();
		if (fillable.size() == 0)
		{
			active = false;
			worldObj.markBlockForUpdate(pos);
			return;
		}

		for (BlockPos p : fillable)
			lava.addAmount(worldObj, new MBlockState(worldObj, p), 1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		active = nbt.getBoolean("active");
		empty = nbt.getBoolean("empty");
		if (nbt.hasKey("lx"))
			lastPos = new BlockPos(nbt.getInteger("lx"), nbt.getInteger("ly"), nbt.getInteger("lz"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("active", active);
		nbt.setBoolean("empty", empty);
		if (lastPos != null)
		{
			nbt.setInteger("lx", lastPos.getX());
			nbt.setInteger("ly", lastPos.getY());
			nbt.setInteger("lz", lastPos.getZ());
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AABBUtils.offset(pos, new AxisAlignedBB(-3, 0, -3, 2, 2, 2));
	}

	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 1;
	}
}
