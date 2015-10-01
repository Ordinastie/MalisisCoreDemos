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
import net.malisis.core.util.multiblock.IMultiBlock;
import net.malisis.core.util.multiblock.MultiBlock;
import net.malisis.core.util.multiblock.PatternMultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class LavaPoolBlock extends MalisisBlock implements ITileEntityProvider, IBlockListener, IMultiBlock
{
	PatternMultiBlock multiBlock;

	public LavaPoolBlock()
	{
		super(Material.anvil);

		setUnlocalizedName("lavapool");
		setHardness(2F);
		setStepSound(soundTypeGlass);
		setCreativeTab(MalisisDemos.tabDemos);
		setTexture(Blocks.command_block);

		multiBlock = new PatternMultiBlock();
		multiBlock.addLayer("ABBBA", "B   B", "B   B", "B   B", "ABBBA");
		multiBlock.addLayer("ACCCA", "C   C", "C   C", "C   C", "ADDDA");
		multiBlock.addLayer("ACCCA", "C   C", "C   C", "C   C", "ADDDA");
		multiBlock.addLayer("ABBBA", "B   B", "B   B", "B   B", "ABBBA");
		multiBlock.withRef('A', Blocks.obsidian).withRef('B', Blocks.stone).withRef('C', Blocks.iron_bars).withRef('D', Blocks.glass);
		multiBlock.setOffset(new BlockPos(-2, 0, -2));
	}

	@Override
	public MultiBlock getMultiBlock(IBlockAccess world, net.minecraft.util.BlockPos pos, IBlockState state)
	{
		return multiBlock;
	}

	public void setActive(World world, BlockPos pos, IBlockState state, MBlockState newState)
	{
		boolean b = getMultiBlock(world, pos, state).isComplete(world, pos, newState);
		LavaPoolTileEntity te = TileEntityUtils.getTileEntity(LavaPoolTileEntity.class, world, pos);
		te.setActive(b);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		setActive(world, pos, state, null);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
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
	public boolean onBlockSet(World world, BlockPos pos, MBlockState blockSet)
	{
		setActive(world, pos, world.getBlockState(pos), blockSet);
		return true;
	}

	@Override
	public boolean onBlockRemoved(World world, BlockPos pos, BlockPos blockPos)
	{
		setActive(world, pos, world.getBlockState(pos), new MBlockState(Blocks.air));
		return true;
	}

}
