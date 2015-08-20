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

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.icon.provider.ConnectedIconsProvider;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
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
		super(Material.glass);
		setHardness(0.6F);
		setStepSound(soundTypeGlass);
		setUnlocalizedName("connected_block");
		setCreativeTab(MalisisDemos.tabDemos);

		//we just need to create a ConnectedTextureIcon
		//the textures, however, need to have a predefined pattern split into 2 files.
		//Both files will be automatically registered and stitched on the block texture sheet.
		setBlockIconProvider(new ConnectedIconsProvider(MalisisDemos.modid + ":blocks/demo_glass_connected"));
	}

	@Override
	public boolean isOpaqueCube()
	{
		//Block is glass, so make it non opaque
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		//block is non opaque but we still want to hide faces between two of this block
		return world.getBlockState(pos).getBlock() != world.getBlockState(pos.offset(side.getOpposite())).getBlock();
	}
}
