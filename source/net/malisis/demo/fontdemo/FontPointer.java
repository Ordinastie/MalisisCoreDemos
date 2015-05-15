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

import net.malisis.demo.MalisisDemos;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author Ordinastie
 *
 */
public class FontPointer extends Item
{
	public FontPointer()
	{
		setUnlocalizedName("font_pointer");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerIcons(IIconRegister p_94581_1_)
	{}

	@Override
	public IIcon getIconFromDamage(int p_77617_1_)
	{
		return Items.iron_hoe.getIconFromDamage(0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
			return itemStack;

		//FontDemoRenderer.instance.reloadFont(player.isSneaking());
		FontDemoRenderer.instance.animate(player.isSneaking());

		return itemStack;
	}
}
