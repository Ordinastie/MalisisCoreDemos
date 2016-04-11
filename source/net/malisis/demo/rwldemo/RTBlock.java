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

package net.malisis.demo.rwldemo;

import net.malisis.core.block.BoundingBoxType;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.DirectionalComponent;
import net.malisis.core.util.AABBUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * @author Ordinastie
 *
 */
public class RTBlock extends MalisisBlock
{
	public RTBlock()
	{
		//set usual properties
		super(Material.IRON);
		setName("rtBlock");
		setCreativeTab(MalisisDemos.tabDemos);
		setTexture(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH));

		addComponent(new DirectionalComponent());
	}

	@Override
	public AxisAlignedBB[] getBoundingBoxes(IBlockAccess world, BlockPos pos, IBlockState state, BoundingBoxType type)
	{
		//for SELECTION, return a 0->1 box
		if (type == BoundingBoxType.SELECTION)
			return AABBUtils.identities();

		//create a stair-like shaped block
		//note that the rotation is automatically handle by MalisisBlock and IBlockDirectional
		int n = 4;
		float f = (float) 1 / n;

		AxisAlignedBB[] aabbs = new AxisAlignedBB[n];

		for (int i = 0; i < n; i++)
			aabbs[i] = new AxisAlignedBB(0, 1 - i * f, i * f, 1, 1 - (i + 1) * f, (i + 1) * f);

		return aabbs;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
}
