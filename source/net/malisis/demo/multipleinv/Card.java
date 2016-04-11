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
import net.malisis.core.inventory.IInventoryProvider.IDeferredInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.item.MalisisItem;
import net.malisis.demo.MalisisDemos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class Card extends MalisisItem implements IDeferredInventoryProvider<ItemStack>
{
	public Card()
	{
		setName("demoCard");
		setCreativeTab(MalisisDemos.tabDemos);
		setTexture(Items.MAP);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		//don't do anything on the client
		if (world.isRemote)
			return new ActionResult<>(EnumActionResult.FAIL, itemStack);

		//open the inventory for current itemStack
		MalisisInventory.open((EntityPlayerMP) player, this, itemStack);

		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		//MalisisCore.message("[%s] %s", world.isRemote ? "Client" : "Server", itemStack.getTagCompound());
		//itemStack.stackTagCompound = null;
	}

	@Override
	public MalisisInventory getInventory(ItemStack itemStack)
	{
		//create the inventory
		MalisisInventory inventory = new MalisisInventory(this, 27);
		inventory.setInventoryStackLimit(2);
		inventory.setItemStackProvider(itemStack);

		return inventory;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MalisisGui getGui(ItemStack itemStack, MalisisInventoryContainer container)
	{
		return new CardGui(container);
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

}
