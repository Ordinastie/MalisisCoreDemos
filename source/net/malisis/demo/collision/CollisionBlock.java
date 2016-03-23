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
import net.malisis.core.block.component.DirectionalComponent;
import net.malisis.core.renderer.DefaultRenderer;
import net.malisis.core.renderer.MalisisRendered;
import net.malisis.core.util.AABBUtils;
import net.malisis.core.util.chunkcollision.IChunkCollidable;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * @author Ordinastie
 *
 */
@MalisisRendered(block = CollisionBlockRenderer.class, item = DefaultRenderer.Block.class)
public class CollisionBlock extends MalisisBlock implements IChunkCollidable
{

	public CollisionBlock()
	{
		//set the usual stuff
		super(Material.wood);
		setCreativeTab(MalisisDemos.tabDemos);
		setName("collisionBlock");
		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setTexture(MalisisDemos.modid + ":blocks/collision");

		addComponent(new DirectionalComponent());
	}

	@Override
	public AxisAlignedBB[] getBoundingBoxes(IBlockAccess world, BlockPos pos, IBlockState state, BoundingBoxType type)
	{
		//make the selection box encompass the whole stairs
		if (type == BoundingBoxType.SELECTION)
			return new AxisAlignedBB[] { new AxisAlignedBB(-2, 0, -2, 3, 4.5F, 3) };

		//build the AABBs that make up the stairs
		float bx = 0;
		float by = 0;
		float bz = 0;
		AxisAlignedBB[] aabbs = new AxisAlignedBB[10];
		aabbs[0] = AABBUtils.identity();
		for (int i = 0; i < aabbs.length - 1; i++)
		{
			by = i * 0.5F;
			bx = Math.min(-2 + i, 2);
			bz = 6 - i;
			if (i < 5)
				bz = 2;

			aabbs[i + 1] = new AxisAlignedBB(bx, by, bz, bx + 1, by + 0.5F, bz + 1);
		}

		return aabbs;
	}

	@Override
	public int blockRange()
	{
		//define the stair radius
		return 5;
	}

	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer)
	{
		//we will render the stairs translucent
		return layer == BlockRenderLayer.TRANSLUCENT;
	}
}
