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
import net.malisis.core.util.BlockPos;
import net.malisis.core.util.BlockState;
import net.malisis.core.util.chunklistener.IBlockListener;
import net.malisis.core.util.multiblock.PatternMultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class LavaPoolBlock extends MalisisBlock implements ITileEntityProvider, IBlockListener
{
	PatternMultiBlock multiBlock;

	public LavaPoolBlock()
	{
		super(Material.anvil);

		setUnlocalizedName(MalisisDemos.modid + ":lavapool");
		setHardness(2F);
		setStepSound(soundTypeGlass);
		setCreativeTab(MalisisDemos.tabDemos);

		setMultiblock();
	}

	private void setMultiblock()
	{
		multiBlock = new PatternMultiBlock();
		multiBlock.addLayer("ABBBA", "B   B", "B   B", "B   B", "ABBBA");
		multiBlock.addLayer("ACCCA", "C   C", "C   C", "C   C", "ADDDA");
		multiBlock.addLayer("ACCCA", "C   C", "C   C", "C   C", "ADDDA");
		multiBlock.addLayer("ABBBA", "B   B", "B   B", "B   B", "ABBBA");
		multiBlock.withRef('A', Blocks.obsidian).withRef('B', Blocks.stone).withRef('C', Blocks.iron_bars).withRef('D', Blocks.glass);
		multiBlock.setOffset(new BlockPos(-2, 0, -2));

	}

	@Override
	public void registerIcons(IIconRegister reg)
	{
		//use gold_block icons
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.gold_block.getIcon(side, meta);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		setMultiblock();

		boolean b = multiBlock.isComplete(world, new BlockPos(x, y, z));
		LavaPoolTileEntity te = (LavaPoolTileEntity) world.getTileEntity(x, y, z);
		te.setActive(b);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ)
	{
		LavaPoolTileEntity te = (LavaPoolTileEntity) world.getTileEntity(x, y, z);
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
	public boolean onBlockSet(World world, BlockPos pos, BlockState newState)
	{
		if (multiBlock == null)
			return true;

		boolean b = multiBlock.isComplete(world, pos, newState);
		LavaPoolTileEntity te = (LavaPoolTileEntity) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		te.setActive(b);

		return true;
	}

	@Override
	public boolean onBlockRemoved(World world, BlockPos pos, BlockPos blockPos)
	{
		if (multiBlock == null)
			return true;

		boolean b = !multiBlock.isFromMultiblock(blockPos.substract(pos)) && multiBlock.isComplete(world, pos);
		LavaPoolTileEntity te = (LavaPoolTileEntity) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		te.setActive(b);

		return true;
	}

}
