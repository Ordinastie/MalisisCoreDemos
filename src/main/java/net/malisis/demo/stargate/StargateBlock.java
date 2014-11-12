/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 PaleoCrafter, Ordinastie
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

package net.malisis.demo.stargate;

import net.malisis.demo.MalisisDemos;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class StargateBlock extends BlockContainer
{
	//renderId of the renderer (set by BaseRenderer.registerFor())
	public static int renderId = -1;
	//large icon for top face
	private IIcon sgPlatform;
	//icon for the side
	private IIcon sgPlatformSide;

	protected StargateBlock()
	{
		super(Material.iron);
		setBlockName("sgBlock");
		setCreativeTab(MalisisDemos.tabDemos);
		setBlockTextureName(MalisisDemos.modid + ":stargate");
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		//register default icon that will be used for the cube in the inventory
		super.registerBlockIcons(register);
		//register the large icon for top face
		sgPlatform = register.registerIcon(MalisisDemos.modid + ":sgplatform");
		//register icon for side
		sgPlatformSide = register.registerIcon(MalisisDemos.modid + ":sgplatformside");
	}

	public IIcon getPlateformIcon()
	{
		//get the large top face icon
		return sgPlatform;
	}

	public IIcon getPlateformSideIcon()
	{
		//get the side icon
		return sgPlatformSide;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		//sets teh starting time for the animations
		((StargateTileEntity) world.getTileEntity(x, y, z)).placedTimer = world.getTotalWorldTime();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		//return the TE
		return new StargateTileEntity();
	}

	@Override
	public boolean isNormalCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return renderId;
	}
}
