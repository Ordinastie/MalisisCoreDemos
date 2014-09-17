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

package net.malisis.demo.test;

import net.malisis.core.renderer.MalisisIcon;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 * 
 */
public class TestBlock extends Block implements ITileEntityProvider
{
	//private int renderId = -1;

	protected TestBlock()
	{
		super(Material.wood);
		setBlockName("testblock");
		setCreativeTab(MalisisDemos.tabDemos);

	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		blockIcon = new MalisisIcon(MalisisDemos.modid + ":test_glass").register((TextureMap) register);
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		MalisisIcon icon = ((MalisisIcon) blockIcon).clone();
		float f = 1F / 3F;
		icon.clip(f, f, 2 * f, 2 * f);
		return icon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ)
	{
		TestTileEntity te = (TestTileEntity) world.getTileEntity(x, y, z);
		if (p.isSneaking())
		{
			te.num = (te.num + 1) % 3;
			return false;
		}
		else
		{
			te.rotate = !te.rotate;
			te.startTime = world.getTotalWorldTime();
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TestTileEntity();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return 0;
	}

}
