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

package net.malisis.demo.fontdemo;

import net.malisis.core.MalisisCore;
import net.malisis.core.item.MalisisItem;
import net.malisis.core.renderer.icon.VanillaIcon;
import net.malisis.core.renderer.icon.provider.DefaultIconProvider;
import net.malisis.demo.MalisisDemos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class FontPointer extends MalisisItem
{
	public FontPointer()
	{
		//set basic infos
		setUnlocalizedName("font_pointer");
		setCreativeTab(MalisisDemos.tabDemos);
		//make the pointer use the Iron hoe item icon and model
		if (MalisisCore.isClient())
			setItemIconProvider(new DefaultIconProvider(new VanillaIcon(Items.iron_hoe, 0)));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		//renderer not accessible server-side
		if (!world.isRemote)
			return itemStack;

		//tell the renderer to start the animation for the shape rendered text
		FontDemo.renderer.animate();

		return itemStack;
	}
}
