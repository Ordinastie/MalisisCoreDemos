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

package net.malisis.demo.model;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.BooleanComponent;
import net.malisis.core.renderer.component.AnimatedModelComponent;
import net.malisis.core.util.Timer;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class Chest extends MalisisBlock
{
	private BooleanComponent opened = new BooleanComponent("opened");

	public Chest()
	{
		super(Material.WOOD);
		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setName("demoChest");
		setTexture(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH));
		setCreativeTab(MalisisDemos.tabDemos);

		addComponent(opened);

		if (MalisisCore.isClient())
		{
			AnimatedModelComponent amc = new AnimatedModelComponent(MalisisDemos.modid + ":models/chest.obj");
			amc.onFirstRender(this::stateCheck);
			addComponent(amc);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		boolean isOpened = opened.invert(world, pos);
		if (world.isRemote)
			AnimatedModelComponent.get(this).link(pos, isOpened ? "close" : "open", isOpened ? "open" : "close");
		return true;
	}

	public void stateCheck(IBlockAccess world, BlockPos pos, IBlockState state, AnimatedModelComponent amc)
	{
		if (opened.get(state) && !amc.isAnimating(pos, "open"))
			amc.start(pos, "open", new Timer(Integer.MIN_VALUE));
	}

}
