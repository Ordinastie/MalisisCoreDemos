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

package net.malisis.demo.collision;

import net.malisis.core.block.BoundingBoxType;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.AABBUtils;
import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.chunkcollision.IChunkCollidable;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Ordinastie
 *
 */
public class CollisionBlock extends MalisisBlock implements IChunkCollidable
{
	public static int renderId = -1;

	public CollisionBlock()
	{
		super(Material.wood);
		setCreativeTab(MalisisDemos.tabDemos);
		setUnlocalizedName("collisionBlock");
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public void registerIcons(IIconRegister register)
	{}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return Blocks.planks.getIcon(side, metadata);
	}

	private int getRotation(ForgeDirection dir)
	{
		switch (dir)
		{
			case SOUTH:
				return 1;
			case WEST:
				return 2;
			case NORTH:
				return 3;
			case EAST:
			default:
				return 0;
		}
	}

	@Override
	public AxisAlignedBB[] getPlacedBoundingBox(IBlockAccess world, int x, int y, int z, int side, EntityPlayer entity, ItemStack itemStack)
	{
		ForgeDirection dir = EntityUtils.getEntityFacing(entity);
		if (entity.isSneaking())
			dir = dir.getOpposite();
		int rotation = getRotation(dir);
		return AABBUtils.rotate(getBoundingBox(null, x, y, z, BoundingBoxType.COLLISION), rotation);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack item)
	{
		ForgeDirection dir = EntityUtils.getEntityFacing(placer);
		int metadata = dir.ordinal();
		if (placer.isSneaking())
			metadata = dir.getOpposite().ordinal();
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}

	@Override
	public AxisAlignedBB[] getBoundingBox(IBlockAccess world, int x, int y, int z, BoundingBoxType type)
	{
		return bucket(world, x, y, z, type);
	}

	public AxisAlignedBB[] bucket(IBlockAccess world, int x, int y, int z, BoundingBoxType type)
	{
		//if (type == BoundingBoxType.SELECTION)
		return new AxisAlignedBB[] { AxisAlignedBB.getBoundingBox(-1, 0, 0, 1, 1, 1) };

	}

	public AxisAlignedBB[] stairs(IBlockAccess world, int x, int y, int z, BoundingBoxType type)
	{
		if (type == BoundingBoxType.SELECTION)
			return new AxisAlignedBB[] { AxisAlignedBB.getBoundingBox(-2, 0, -2, 3, 4.5F, 3) };

		float bx = 0;
		float by = 0;
		float bz = 0;
		AxisAlignedBB[] aabbs = new AxisAlignedBB[10];
		aabbs[0] = AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
		for (int i = 0; i < aabbs.length - 1; i++)
		{
			by = i * 0.5F;
			bx = Math.min(-2 + i, 2);
			bz = 6 - i;
			if (i < 5)
				bz = 2;

			aabbs[i + 1] = AxisAlignedBB.getBoundingBox(bx, by, bz, bx + 1, by + 0.5F, bz + 1);

		}

		if (world != null)
		{
			int a = 0;
			switch (world.getBlockMetadata(x, y, z))
			{
				case 3:
					a = 1;
					break;
				case 4:
					a = 2;
					break;
				case 2:
					a = 3;
					break;
				case 5:
					a = 0;
					break;
			}
			AABBUtils.rotate(aabbs, a);
		}
		return aabbs;
	}

	@Override
	public int blockRange()
	{
		return 5;
	}

	@Override
	public boolean canRenderInPass(int pass)
	{
		return pass == 1;
	}

	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public int getRenderType()
	{
		return renderId;
	}

}
