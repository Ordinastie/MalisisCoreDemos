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

package net.malisis.demo.multiblock;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.multiblock.MultiBlockComponent;
import net.malisis.core.util.multiblock.PatternMultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Ordinastie
 *
 */
public class PatternBlock extends MalisisBlock
{
	public PatternBlock()
	{
		//sets the usual properties
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setName("patternMultiBlockDemo");
		setCreativeTab(MalisisDemos.tabDemos);
		setTexture(Blocks.gold_block);

		//create the multiBlock
		IBlockState birchPlanks = Blocks.planks.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
		IBlockState birchStairs = Blocks.birch_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
		IBlockState chest = Blocks.chest.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.SOUTH);

		PatternMultiBlock multiBlock = new PatternMultiBlock();
		multiBlock.addLayer(" A ", "A A", " A ", " B ", " C ").addLayer(" D");
		multiBlock.withRef('A', Blocks.emerald_block).withRef('B', birchPlanks).withRef('C', birchStairs).withRef('D', chest);
		multiBlock.setOffset(new BlockPos(-1, 0, -1));
		multiBlock.setBulkProcess(true, false);

		addComponent(new MultiBlockComponent(multiBlock));
	}

}
