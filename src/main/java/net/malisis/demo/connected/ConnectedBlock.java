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

import net.malisis.core.renderer.icon.ConnectedTextureIcon;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class ConnectedBlock extends Block
{
	public ConnectedBlock()
	{
		//set the usual properties
		super(Material.glass);
		setHardness(0.6F);
		setStepSound(soundTypeGlass);
		setBlockName("connected_block");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		//we just need to create a ConnectedTextureIcon
		//the textures, however, need to have a predefined pattern split into 2 files.
		//Both files will be automatically registered and stiched on the block texture sheet.
		blockIcon = new ConnectedTextureIcon((TextureMap) register, MalisisDemos.modid + ":demo_glass_connected");
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		//when not in world, just return the "full" icon (border for the 4 sides)
		return ((ConnectedTextureIcon) blockIcon).getFullIcon();
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		//when in world, let the ConnectedTextureIcon fetch the right icon
		return ((ConnectedTextureIcon) blockIcon).getIcon(world, x, y, z, side);
	}

	@Override
	public boolean isOpaqueCube()
	{
		//Block is glass, so make it non opaque
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		//block is non opaque but we still want to hide faces between two of this block
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return world.getBlock(x, y, z) != world.getBlock(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);
	}
}
