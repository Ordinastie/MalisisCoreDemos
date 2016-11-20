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

package net.malisis.demo.lavapool;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.MBlockState;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.core.util.chunklistener.IBlockListener;
import net.malisis.core.util.multiblock.MultiBlockComponent;
import net.malisis.core.util.multiblock.PatternMultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class LavaPoolBlock extends MalisisBlock implements ITileEntityProvider, IBlockListener.Post
{
	PatternMultiBlock multiBlock;

	public LavaPoolBlock()
	{
		super(Material.ANVIL);

		setName("lavapool");
		setHardness(2F);
		setSoundType(SoundType.GLASS);
		setCreativeTab(MalisisDemos.tabDemos);
		setTexture(Blocks.DIAMOND_BLOCK);

		multiBlock = new PatternMultiBlock();
		multiBlock.addLayer("ABBBA", "B   B", "B   B", "B   B", "ABBBA");
		multiBlock.addLayer("ACCCA", "C   C", "C   C", "C   C", "ADDDA");
		multiBlock.addLayer("ACCCA", "C   C", "C   C", "C   C", "ADDDA");
		multiBlock.addLayer("ABBBA", "B   B", "B   B", "B   B", "ABBBA");
		multiBlock.withRef('A', Blocks.OBSIDIAN).withRef('B', Blocks.STONE).withRef('C', Blocks.IRON_BARS).withRef('D', Blocks.GLASS);
		multiBlock.setOffset(new BlockPos(-2, 0, -2));

		addComponent(new MultiBlockComponent(multiBlock));
	}

	public void setActive(World world, BlockPos pos, IBlockState state, MBlockState newState)
	{
		boolean b = multiBlock.isComplete(world, pos, newState);
		LavaPoolTileEntity te = TileEntityUtils.getTileEntity(LavaPoolTileEntity.class, world, pos);
		te.setActive(b);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		setActive(world, pos, state, null);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		LavaPoolTileEntity te = TileEntityUtils.getTileEntity(LavaPoolTileEntity.class, world, pos);
		te.startAnim = true;
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new LavaPoolTileEntity();
	}

	@Override
	public int blockRange()
	{
		return 3;
	}

	@Override
	public void onBlockSet(World world, BlockPos listener, BlockPos modified, IBlockState oldState, IBlockState newState)
	{
		setActive(world, listener, world.getBlockState(listener), new MBlockState(modified, newState));
	}
}
