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

package net.malisis.demo.multipleinv;

import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.inventory.IInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.demo.MalisisDemos;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class Card extends Item implements IInventoryProvider
{
	public Card()
	{
		setUnlocalizedName("demoCard");
		setCreativeTab(MalisisDemos.tabDemos);
	}

	@Override
	public void registerIcons(IIconRegister p_94581_1_)
	{}

	@Override
	public IIcon getIconFromDamage(int damage)
	{
		return Items.map.getIconFromDamage(damage);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (world.isRemote)
			return itemStack;

		getInventory(itemStack).open((EntityPlayerMP) player);

		return itemStack;
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		//MalisisCore.message("[%s] %s", world.isRemote ? "Client" : "Server", itemStack.getTagCompound());
		//itemStack.stackTagCompound = null;
	}

	@Override
	public MalisisInventory getInventory(Object... data)
	{
		if (!(data[0] instanceof ItemStack))
			return null;

		MalisisInventory inventory = new MalisisInventory(this, 27);
		inventory.setInventoryStackLimit(2);
		inventory.setItemStackProvider((ItemStack) data[0]);
		return inventory;
	}

	@Override
	public MalisisInventory getInventory(ForgeDirection side, Object... data)
	{
		return getInventory(data);
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack)
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		return new CardGui(container);
	}

}
