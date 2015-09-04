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

package net.malisis.demo.blockdir;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.icon.provider.SidesIconProvider;
import net.malisis.core.util.EntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class BlockDir extends MalisisBlock
{
	public static final PropertyDirection DIRECTION = PropertyDirection.create("direction", EnumFacing.Plane.HORIZONTAL);

	public BlockDir()
	{
		super(Material.clay);
		setCreativeTab(MalisisDemos.tabDemos);
		setUnlocalizedName("blockDirDemo");

		String prefix = MalisisDemos.modid + ":blocks/dirblock_";
		String[] names = new String[] { prefix + "bottom", prefix + "top", prefix + "back", prefix + "front", prefix + "left",
				prefix + "right", };

		if (MalisisCore.isClient())
		{
			SidesIconProvider provider = new SidesIconProvider(names[0], names);
			provider.setPropertyDirection(DIRECTION);
			setBlockIconProvider(provider);
		}
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, DIRECTION);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		EnumFacing dir = EntityUtils.getEntityFacing(placer).getOpposite();
		return getDefaultState().withProperty(DIRECTION, dir);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DIRECTION, EnumFacing.getHorizontal(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(DIRECTION)).getHorizontalIndex();
	}
}
