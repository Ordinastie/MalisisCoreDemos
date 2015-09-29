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

package net.malisis.demo.sidedinvdemo;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.IBlockDirectional;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.inventory.ISidedInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.renderer.icon.provider.SidesIconProvider;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.malisis.demo.tabinv.TabInvTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class SidedBlock extends MalisisBlock implements ITileEntityProvider, IBlockDirectional
{
	public SidedBlock()
	{
		//set usual properties
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(Block.soundTypeWood);
		setUnlocalizedName("sidedBlockDemo");
		setCreativeTab(MalisisDemos.tabDemos);

		if (MalisisCore.isClient())
		{
			//set the icons to use for the sides
			SidesIconProvider provider = new SidesIconProvider(MalisisDemos.modid + ":blocks/sidedinv");
			provider.setSideIcon(EnumFacing.WEST, MalisisDemos.modid + ":blocks/sidedingots");
			provider.setSideIcon(EnumFacing.EAST, MalisisDemos.modid + ":blocks/sidedstones");
			provider.setSideIcon(EnumFacing.UP, MalisisDemos.modid + ":blocks/sidedtriage");
			setBlockIconProvider(provider);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//don't do anything on the client
		if (world.isRemote)
			return true;

		//get the InventoryProvider.
		ISidedInventoryProvider te = TileEntityUtils.getTileEntity(ISidedInventoryProvider.class, world, pos);
		//open the inventory
		MalisisInventory.open((EntityPlayerMP) player, te);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		//Create the TileEntity
		return new SidedTileEntity();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TabInvTileEntity te = TileEntityUtils.getTileEntity(TabInvTileEntity.class, world, pos);
		if (te != null)
			te.breakInventories(world, pos);
	}
}
