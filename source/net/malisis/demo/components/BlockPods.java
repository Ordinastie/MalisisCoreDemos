/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Ordinastie
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

package net.malisis.demo.components;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.ITickableComponent.PeriodicTickableComponent;
import net.malisis.core.block.component.PowerComponent;
import net.malisis.core.block.component.PowerComponent.ComponentType;
import net.malisis.core.block.component.PowerComponent.InteractionType;
import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderType;
import net.malisis.core.renderer.component.ModelComponent;
import net.malisis.core.renderer.icon.provider.IIconProvider;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class BlockPods extends MalisisBlock
{
	private static PropertyInteger PODS = PropertyInteger.create("pods", 0, 4);

	public BlockPods()
	{
		super(Material.GROUND);
		setCreativeTab(MalisisDemos.tabDemos);
		setName("block_pods");
		setUnlocalizedName("blockShapeDemo");

		addComponent(new PowerComponent(InteractionType.RIGHT_CLICK, ComponentType.NONE));
		addComponent(new PeriodicTickableComponent(this::update));

		if (MalisisCore.isClient())
		{
			addComponent(IIconProvider.create(MalisisDemos.modid + ":blocks/", "pods").forShape("foot", "pod_base").build());
			addComponent(new ModelComponent(MalisisDemos.modid + ":models/pods.obj", this::isPodVisible));
		}
	}

	@Override
	protected List<IProperty<?>> getProperties()
	{
		return Lists.newArrayList(PODS);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
		world.scheduleBlockUpdate(pos, this, 20, 0);
		return true;
	}

	private int update(Block block, World world, BlockPos pos, IBlockState state, Random rand)
	{
		boolean powered = PowerComponent.isPowered(state);
		boolean shouldTick = false;
		int pods = state.getValue(PODS);

		if (powered && pods < 4)
			shouldTick = pods++ < 4;
		if (!powered && pods > 0)
			shouldTick = pods-- > 0;

		world.setBlockState(pos, state.withProperty(PODS, pods));
		return shouldTick ? 20 : 0;
	}

	@SideOnly(Side.CLIENT)
	private boolean isPodVisible(MalisisRenderer<TileEntity> renderer, String name)
	{
		if (renderer.getRenderType() != RenderType.BLOCK)
			return true;

		if (!name.startsWith("pod"))
			return true;

		int numpods = renderer.getBlockState().getValue(PODS);
		int shapeNum = Integer.parseInt(name.substring(3));
		return shapeNum <= numpods;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}
}
