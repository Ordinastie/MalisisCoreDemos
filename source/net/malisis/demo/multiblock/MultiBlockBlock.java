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

package net.malisis.demo.multiblock;

import net.malisis.core.renderer.icon.MegaTextureIcon;
import net.malisis.core.tileentity.MultiBlockTileEntity;
import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.MultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Ordinastie
 *
 */
//the block need to provide an entity that implement MultiBlock.IProvider.
public class MultiBlockBlock extends Block implements ITileEntityProvider
{
	public MultiBlockBlock()
	{
		//sets the usual properties
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setBlockName("multiBlockDemo");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		//create a MegaTextureIcon() with the right name/path (like for regular icon registering)
		//second parameter for MegaTextureIcon() is the number of block the texture should spread across (default is texture width / 16)
		//and then register the icon
		blockIcon = new MegaTextureIcon(MalisisDemos.modid + ":mb", 4).register((TextureMap) register);
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		//we want the icon for the SOUTH face only
		//this method will be called when not in world so we hard code a specific face for it (one that is visible from inventory)
		if (side == ForgeDirection.SOUTH.ordinal())
			return blockIcon;
		//use obsidian for other faces
		return Blocks.obsidian.getIcon(side, 0);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		//get the MultiBlock for this block
		MultiBlock mb = MultiBlock.getMultiBlock(world, x, y, z);
		ForgeDirection dir;
		if (mb != null)
		{
			//get the direction of the MultiBlock, but take the opposite : if the player was facing the NORTH when placing
			//the MultiBlock, we want to draw the texture for the south Face
			dir = mb.getDirection().getOpposite();
			//we check the face matches the direction first so our MegaTexture is only displayed on the "front"
			if (side == dir.ordinal())
				return ((MegaTextureIcon) blockIcon).getIcon(world, this, x, y, z, side);
		}

		//for the other sides, we use obsidian
		return Blocks.obsidian.getIcon(side, 0);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		//we get the direction the player is facing
		ForgeDirection dir = EntityUtils.getEntityFacing(player);
		//we create the MultiBlock for this coordinates. Those coordinates will be the "origin" of the MultiBlock.
		//any logic should be only done for the origin
		MultiBlock mb = new MultiBlock(world, x, y, z);
		//we define the bounds for the MultiBlock. The bounding box is relative to the player view and to the origin. (Width, Height, Depth)
		//the structure will be place in front of the player, towards up, and extending to its right.
		//Use negative values to change that. Note that providing a negative depth would likely fail if the player is in the way
		mb.setBounds(AxisAlignedBB.getBoundingBox(-1, 0, 0, 3, 4, 1));
		//set the direction of the MultiBlock.
		mb.setDirection(dir);
		//do place the blocks in world. If there is not enough place, the original block will be removed
		mb.placeBlocks();
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{

		//we need to destroy the multiBlock if one of its block gets destroy
		MultiBlock.destroy(world, x, y, z);
		//As safety, set block to air in case the MultiBlock couldn't be found.
		world.setBlockToAir(x, y, z);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		//create the MultiBlock tile entity the tile entity as to implement MultiBlock.IProvider
		//if you don't have any specific logic for your tileEntity you can create the default implementation from MalisisCore : MultiBlockTileEntity
		return new MultiBlockTileEntity();
	}

}
