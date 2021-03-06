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

package net.malisis.demo.blocklistener;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.chunklistener.IBlockListener;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This block allows only torches to be placed within 3 blocks inside its alignment
 *
 * @author Ordinastie
 *
 */
public class BlockListenerBlock extends MalisisBlock implements IBlockListener.Pre
{
	public BlockListenerBlock()
	{
		//set usual caracteristics
		super(Material.WOOD);
		setCreativeTab(MalisisDemos.tabDemos);
		setName("blockListener");
		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setTexture(MalisisDemos.modid + ":blocks/blocklistener");
	}

	@Override
	public int blockRange()
	{
		//this block gets notified if another is placed or broken within 3 blocks of its position
		return 3;
	}

	@Override
	public boolean onBlockSet(World world, BlockPos listener, BlockPos modified, IBlockState oldState, IBlockState newState)
	{
		if (newState.getBlock() == Blocks.AIR)
			return true;

		boolean isAligned = false;
		//if aligned on the X and Y, check Z distance
		if (listener.getX() == modified.getX() && listener.getY() == modified.getY())
			isAligned = Math.abs(listener.getZ() - modified.getZ()) <= 3;
		//if aligned on the X and Z, check Y distance
		if (listener.getX() == modified.getX() && listener.getZ() == modified.getZ())
			isAligned = Math.abs(listener.getY() - modified.getY()) <= 3;
		//if aligned on the Y and Z, check X distance
		if (listener.getY() == modified.getY() && listener.getZ() == modified.getZ())
			isAligned = Math.abs(listener.getX() - modified.getX()) <= 3;

		//if at least aligned on two axis and close enough, and block is a torch, then cancel the placement
		if (isAligned && newState.getBlock() != Blocks.TORCH)
			return false;

		return true;
	}
}
