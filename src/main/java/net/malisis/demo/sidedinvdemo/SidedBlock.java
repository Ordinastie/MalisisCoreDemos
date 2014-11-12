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

import net.malisis.core.inventory.IInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Ordinastie
 *
 */
public class SidedBlock extends Block implements ITileEntityProvider
{
	//Use different icons for sides
	protected IIcon triageIcon;
	protected IIcon stonesIcon;
	protected IIcon ingotsIcon;

	public SidedBlock()
	{
		//set usual properties
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(Block.soundTypeWood);
		setBlockName("sidedBlockDemo");
		setBlockTextureName(MalisisDemos.modid + ":sidedinv");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		//register the icons
		blockIcon = register.registerIcon(getTextureName());
		triageIcon = register.registerIcon(MalisisDemos.modid + ":sidedtriage");
		stonesIcon = register.registerIcon(MalisisDemos.modid + ":sidedstones");
		ingotsIcon = register.registerIcon(MalisisDemos.modid + ":sidedingots");
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		//get the right icon for the right side
		if (side == ForgeDirection.UP.ordinal())
			return triageIcon;
		else if (side == ForgeDirection.EAST.ordinal())
			return stonesIcon;
		else if (side == ForgeDirection.WEST.ordinal())
			return ingotsIcon;
		else
			return blockIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return true;

		//get the InventoryProvider.
		IInventoryProvider te = TileEntityUtils.getTileEntity(IInventoryProvider.class, world, x, y, z);
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
}
