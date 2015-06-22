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

package net.malisis.demo.multiblock2;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.BlockPos;
import net.malisis.core.util.BlockState;
import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.chunklistener.IBlockListener;
import net.malisis.core.util.multiblock.PatternMultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class PatternBlock extends MalisisBlock implements IBlockListener
{
	public PatternMultiBlock multiBlock = new PatternMultiBlock();

	public PatternBlock()
	{
		//sets the usual properties
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setUnlocalizedName("multiBlockDemo2");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerIcons(IIconRegister register)
	{
		//no icon, the multiblock uses different already registered blocks
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return Blocks.gold_block.getIcon(side, metadata);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		int rotation = EntityUtils.getEntityFacingInt(player, false);

		multiBlock = new PatternMultiBlock();
		multiBlock.addLayer(" A ", "A A", " A ", " B ", " C ")/*.addLayer(" D", "D D")*/;
		multiBlock.withRef('A', Blocks.emerald_block).withRef('B', Blocks.planks, 2).withRef('C', Blocks.birch_stairs, 3)
				.withRef('D', Blocks.torch).withRef('E', Blocks.chest, 2);
		multiBlock.setOffset(new BlockPos(-1, 0, -1));
		multiBlock.setRotation(rotation);
		multiBlock.placeBlocks(world, new BlockPos(x, y, z));

		world.setBlockMetadataWithNotify(x, y, z, rotation, 3);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta)
	{
		multiBlock.setRotation(meta);
		multiBlock.breakBlocks(world, new BlockPos(x, y, z));
	}

	@Override
	public int blockRange()
	{
		return 3;
	}

	@Override
	public boolean onBlockSet(World world, BlockPos pos, BlockState newState)
	{
		return true;
	}

	@Override
	public boolean onBlockRemoved(World world, BlockPos pos, BlockPos newPos)
	{
		multiBlock.setRotation(pos.getMetadata(world));
		if (multiBlock.isFromMultiblock(newPos.substract(pos)))
		{
			world.setBlockToAir(pos.getX(), pos.getY(), pos.getZ());
			return false;
		}
		return true;
	}

}
