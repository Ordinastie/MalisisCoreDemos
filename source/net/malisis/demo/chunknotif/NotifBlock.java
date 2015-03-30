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

package net.malisis.demo.chunknotif;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.BlockPos;
import net.malisis.core.util.BlockState;
import net.malisis.core.util.chunklistener.IBlockListener;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class NotifBlock extends MalisisBlock implements IBlockListener
{
	public static int renderId = -1;

	public NotifBlock()
	{
		super(Material.wood);
		setCreativeTab(MalisisDemos.tabDemos);
		setUnlocalizedName("notif");
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
		return Blocks.emerald_block.getIcon(side, metadata);
	}

	@Override
	public int blockRange()
	{
		return 3;
	}

	@Override
	public boolean onBlockSet(World world, BlockPos pos, BlockState blockState)
	{
		boolean b = false;
		if (pos.getX() == blockState.getX() && pos.getY() == blockState.getY())
			b = Math.abs(pos.getZ() - blockState.getZ()) < 3;
		if (pos.getX() == blockState.getX() && pos.getZ() == blockState.getZ())
			b = Math.abs(pos.getY() - blockState.getY()) < 3;
		if (pos.getY() == blockState.getY() && pos.getZ() == blockState.getZ())
			b = Math.abs(pos.getX() - blockState.getX()) < 3;

		if (b && blockState.getBlock() != Blocks.torch)
			return false;

		return true;
	}

	@Override
	public boolean onBlockRemoved(World world, BlockPos pos, BlockPos blockPos)
	{
		return true;
	}
}
