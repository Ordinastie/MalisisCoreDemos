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

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.DirectionalComponent;
import net.malisis.core.renderer.icon.Icon;
import net.malisis.core.renderer.icon.provider.IIconProvider;
import net.malisis.core.renderer.icon.provider.IconProviderBuilder;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

/**
 * Only thing needed is for the block to add a {@link DirectionalComponent}.<br>
 * The {@code MalisisBlock} will forward the required {@link Block} calls to the component.
 *
 * @author Ordinastie
 *
 */
public class BlockDir extends MalisisBlock
{
	public BlockDir()
	{
		//set usual properties
		super(Material.CLAY);
		setCreativeTab(MalisisDemos.tabDemos);
		setName("blockDirDemo");

		addComponent(new DirectionalComponent());

		if (MalisisCore.isClient())
		{

			String prefix = MalisisDemos.modid + ":blocks/dirblock_";
			String[] names = new String[] { prefix + "bottom", prefix + "top", prefix + "back", prefix + "front", prefix + "left",
					prefix + "right", };
			IconProviderBuilder builder = IIconProvider.create(Icon.from(names[1]));
			for (int i = 0; i < 6; i++)
				builder.withSide(EnumFacing.getFront(i), Icon.from(names[i]));
			//create the iconProvider
			addComponent(builder.build());
		}
	}
}
