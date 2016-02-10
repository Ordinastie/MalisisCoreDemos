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

package net.malisis.demo.components;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.DirectionalComponent;
import net.malisis.core.block.component.PowerComponent;
import net.malisis.core.block.component.PowerComponent.Type;
import net.malisis.core.renderer.icon.MalisisIcon;
import net.malisis.core.renderer.icon.provider.FlexibleBlockIconProvider;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class BlockPower extends MalisisBlock
{
	public BlockPower()
	{
		//set usual properties
		super(Material.clay);
		setCreativeTab(MalisisDemos.tabDemos);
		setName("blockPower");

		addComponent(new DirectionalComponent());
		addComponent(new PowerComponent(Type.RIGHT_CLICK));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createIconProvider(Object object)
	{
		MalisisIcon icon = new MalisisIcon(MalisisDemos.modid + ":blocks/blockPower");
		MalisisIcon iconOn = new MalisisIcon(MalisisDemos.modid + ":blocks/blockPower_on");
		MalisisIcon iconOff = new MalisisIcon(MalisisDemos.modid + ":blocks/blockPower_off");

		iconProvider = new FlexibleBlockIconProvider(this, icon,
				(state, side) -> side == EnumFacing.SOUTH ? (PowerComponent.isPowered(state) ? iconOn : iconOff) : icon);
	}
}
