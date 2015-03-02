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
import net.malisis.core.util.chunkcollision.IChunkCollidable;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

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
		setBlockName("collisionBlock");
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return Blocks.planks.getIcon(side, metadata);
	}

	@Override
	public AxisAlignedBB[] getBoundingBox(IBlockAccess world, int x, int y, int z, BoundingBoxType type)
	{
		if (type == BoundingBoxType.SELECTION)
			return new AxisAlignedBB[] { AxisAlignedBB.getBoundingBox(-2, 0, -2, 3, 4.5F, 3) };

		float bx = 0;
		float by = 0;
		float bz = 0;
		AxisAlignedBB[] aabbs = new AxisAlignedBB[9];
		for (int i = 0; i < aabbs.length; i++)
		{
			by = i * 0.5F;
			bx = Math.min(-2 + i, 2);
			bz = 6 - i;
			if (i < 5)
				bz = 2;

			aabbs[i] = AxisAlignedBB.getBoundingBox(bx, by, bz, bx + 1, by + 0.5F, bz + 1);

		}
		return aabbs;
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
