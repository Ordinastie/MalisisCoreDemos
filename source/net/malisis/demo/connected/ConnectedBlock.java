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

package net.malisis.demo.connected;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.icon.Icon;
import net.malisis.core.renderer.icon.provider.IIconProvider;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class ConnectedBlock extends MalisisBlock
{
	public ConnectedBlock()
	{
		//set the usual properties
		super(Material.GLASS);
		setHardness(0.6F);
		setSoundType(SoundType.GLASS);
		setName("connected_block");
		setCreativeTab(MalisisDemos.tabDemos);

		//we just need to create a ConnectedIconsProvider
		//the textures, however, need to have a predefined pattern split into 2 files.
		//Both files will be automatically registered and stitched on the block texture sheet.
		if (MalisisCore.isClient())
			addComponent(IIconProvider.create(Icon.from(MalisisDemos.modid + ":blocks/demo_glass_connected")).connected().build());
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		//Block is glass, so make it non opaque
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		//block is non opaque but we still want to hide faces between two of this block
		IBlockState neighbor = world.getBlockState(pos.offset(side));
		return neighbor.getBlock() == this ? false : super.shouldSideBeRendered(state, world, pos, side);
	}
}
