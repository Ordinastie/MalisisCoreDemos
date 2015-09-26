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

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.icon.MalisisIcon;
import net.malisis.core.renderer.icon.VanillaIcon;
import net.malisis.core.renderer.icon.provider.MegaTextureIconProvider;
import net.malisis.core.util.multiblock.AABBMultiBlock;
import net.malisis.core.util.multiblock.IMultiBlock;
import net.malisis.core.util.multiblock.MultiBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class AABBBlock extends MalisisBlock implements IMultiBlock
{
	AABBMultiBlock multiBlock;

	public AABBBlock()
	{
		//sets the usual properties
		super(Material.wood);
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setUnlocalizedName("aabbMultiBlockDemo");
		setCreativeTab(MalisisDemos.tabDemos);

		if (MalisisCore.isClient())
		{
			MegaTextureIconProvider iconProvider = new MegaTextureIconProvider(new VanillaIcon(Blocks.obsidian));
			iconProvider.setMegaTexture(EnumFacing.SOUTH, new MalisisIcon(MalisisDemos.modid + ":blocks/mb"), 4);
			setBlockIconProvider(iconProvider);
		}

		//create the multiblock with the AABB
		multiBlock = new AABBMultiBlock(this, new AxisAlignedBB(-1, 0, 0, 3, 4, 1));
		multiBlock.setBulkProcess(true, true);
	}

	@Override
	public MultiBlock getMultiBlock(World world, BlockPos pos, IBlockState state)
	{
		return multiBlock;
	}
}
