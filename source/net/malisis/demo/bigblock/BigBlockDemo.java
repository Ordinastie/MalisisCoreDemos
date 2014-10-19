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

package net.malisis.demo.bigblock;

import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.MultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Ordinastie
 *
 */
public class BigBlockDemo extends Block implements ITileEntityProvider
{
	public static int renderId = -1;
	private IIcon handleIcon;

	public BigBlockDemo()
	{
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setBlockName("bigBlockDemo");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return Blocks.planks.getIcon(side, metadata);
	}

	public IIcon getHandleIcon()
	{
		return handleIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		ForgeDirection dir = EntityUtils.getEntityFacing(player);
		MultiBlock mb = new MultiBlock(world, x, y, z);
		mb.setBounds(AxisAlignedBB.getBoundingBox(0, 0, 0, 2, 3, 2));
		mb.setDirection(dir);
		mb.placeBlocks();
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
	{
		MultiBlock.destroy(world, x, y, z);
		world.setBlockToAir(x, y, z);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new BigBlockDemoTileEntity();
	}

}
