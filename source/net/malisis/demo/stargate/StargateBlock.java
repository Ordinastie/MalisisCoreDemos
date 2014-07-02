/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 PaleoCrafter, Ordinastie
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

package net.malisis.demo.stargate;

import java.util.Random;

import net.malisis.core.renderer.IBaseRendering;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class StargateBlock extends BlockContainer implements IBaseRendering
{
	public static int deployTimer = 100;
	private int renderId = -1;

	protected StargateBlock()
	{
		super(Material.iron);
		setBlockName("sgBlock");
		setCreativeTab(MalisisDemos.tabDemos);
		setBlockTextureName(MalisisDemos.modid + ":sgplatformside");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		world.scheduleBlockUpdate(x, y, z, this, deployTimer);
		((StargateTileEntity) world.getTileEntity(x, y, z)).placedTimer = world.getTotalWorldTime();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{

		world.setBlockMetadataWithNotify(x, y, z, 1, 2);
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		StargateTileEntity te = new StargateTileEntity();
		return te;
	}

	@Override
	public boolean isNormalCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return renderId;
	}

	@Override
	public void setRenderId(int id)
	{
		renderId = id;
	}
}
