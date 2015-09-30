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

package net.malisis.demo.syncdemo;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class SyncBlock extends MalisisBlock implements ITileEntityProvider
{
	public SyncBlock()
	{
		//set usual properties
		super(Material.wood);
		setUnlocalizedName("syncblock");
		setHardness(2F);
		setStepSound(soundTypeWood);
		setCreativeTab(MalisisDemos.tabDemos);
		//set the icons to use
		setTextureName(MalisisDemos.modid + ":blocks/syncDemo");
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//the action is done server side only
		if (world.isRemote)
			return true;

		//get the TileEntity
		SyncTileEntity syncTe = TileEntityUtils.getTileEntity(SyncTileEntity.class, world, pos);
		if (syncTe == null)
			return true;

		//update the values on the TE
		syncTe.activate();

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new SyncTileEntity();
	}
}
