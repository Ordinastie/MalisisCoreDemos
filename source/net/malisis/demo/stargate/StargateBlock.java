/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 PaleoCrafter, Ordinastie
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

package net.malisis.demo.stargate;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.DefaultRenderer;
import net.malisis.core.renderer.MalisisRendered;
import net.malisis.core.renderer.icon.Icon;
import net.malisis.core.renderer.icon.provider.IBlockIconProvider;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@MalisisRendered(block = StargateRenderer.class, item = DefaultRenderer.Block.class)
public class StargateBlock extends MalisisBlock implements ITileEntityProvider
{

	protected StargateBlock()
	{
		super(Material.IRON);
		setName("sgBlock");
		setCreativeTab(MalisisDemos.tabDemos);

		if (MalisisCore.isClient())
			addComponent(SgIconProvider.get());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		//sets the starting time for the animations
		StargateTileEntity te = TileEntityUtils.getTileEntity(StargateTileEntity.class, world, pos);
		if (te != null)
			te.placedTimer = world.getTotalWorldTime();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		//return the TE
		return new StargateTileEntity();
	}

	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	//the icon provider is custom one that holds the 3 icons that will be needed
	//the default one will be use for the inventory block
	@SideOnly(Side.CLIENT)
	public static class SgIconProvider implements IBlockIconProvider
	{
		private Icon defaultIcon = Icon.from(MalisisDemos.modid + ":blocks/stargate");
		private Icon platform = Icon.from(MalisisDemos.modid + ":blocks/sgplatform");
		private Icon platformSide = Icon.from(MalisisDemos.modid + ":blocks/sgplatformside");

		@Override
		public Icon getIcon(IBlockState state, EnumFacing side)
		{
			return defaultIcon;
		}

		@Override
		public Icon getIcon()
		{
			return defaultIcon;
		}

		public Icon getPlatformIcon()
		{
			return platform;
		}

		public Icon getPlatformSideIcon()
		{
			return platformSide;
		}

		public static SgIconProvider get()
		{
			return new SgIconProvider();
		}
	}

}
