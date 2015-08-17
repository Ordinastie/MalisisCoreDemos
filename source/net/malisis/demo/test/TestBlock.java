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

import net.malisis.core.block.MalisisBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class TestBlock extends MalisisBlock implements ITileEntityProvider
{
	public static int currentPass = 0;
	public static int renderId = 0;

	protected TestBlock()
	{
		super(Material.wood);
		setUnlocalizedName("testblock");
		setCreativeTab(MalisisDemos.tabDemos);

	}

	@Override
	public void registerIcons(IIconRegister register)
	{
		blockIcon = register.registerIcon(MalisisDemos.modid + ":test_frame");
		//overlay = register.registerIcon(MalisisDemos.modid + ":ice_overlay");
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		//		if (metadata == 1)
		//			return Blocks.planks.getIcon(side, metadata);
		//		return Blocks.brick_block.getIcon(side, metadata);
		return super.getIcon(side, metadata);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
			return false;

		//new TestGui().display();
		((TestTileEntity) world.getTileEntity(x, y, z)).progress = 0;

		return false;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int metadata)
	{
		//setBlockBounds(.25F, 0, 0.25F, .75F, 0.5F, .75F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
		lightValue = 15;
		return metadata;
	}

	@Override
	public int getRenderBlockPass()
	{
		return super.getRenderBlockPass();
	}

	@Override
	public boolean canRenderInPass(int pass)
	{
		return super.canRenderInPass(pass);
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

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TestTileEntity();
	}

}
