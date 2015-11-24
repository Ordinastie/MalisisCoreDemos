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

package net.malisis.demo.tabinv;

import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.inventory.IInventoryProvider.IDirectInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
public class TabInvTileEntity extends TileEntity implements IDirectInventoryProvider, IUpdatePlayerListBox
{
	//inventory in first tab
	public MalisisInventory inventory;
	//inventory in second tab
	public MalisisInventory converter;
	//manually instantiate converter slots because they have specificities
	public MalisisSlot slotIron;
	public MalisisSlot slotGold;
	public MalisisSlot slotDiamond;

	//tab currently active
	public int currentTab = 1;

	//make one conversion every second
	private int maxTimeConvert = 20; //1s
	//extract 2 items every 3 seconds
	private int maxTimeExtract = 60; //3s
	//current convert timer
	private int timerConvert;
	//current extract timer
	private int timerExtract;

	public TabInvTileEntity()
	{
		//base inventory holds 16 slots with max 4 items in each slot
		inventory = new MalisisInventory(this, 16);
		inventory.setInventoryStackLimit(4);

		//manually create converter slots
		slotIron = new FilterSlot(0, Items.iron_ingot);
		slotGold = new FilterSlot(1, Items.gold_ingot);
		//output slot means player can't interact with it, so no need to make a special slot
		slotDiamond = new MalisisSlot(2);
		slotDiamond.setOutputSlot();

		//converter inventory with stack limit 32 (FilterSlot overrides that value)
		converter = new MalisisInventory(this, new MalisisSlot[] { slotIron, slotGold, slotDiamond });
		converter.setInventoryStackLimit(32);

		setTab(1);
	}

	public void setTab(int tab)
	{
		//Set the tab (called both sides)
		currentTab = tab;
		if (currentTab == 1)
		{
			//allow player interaction
			inventory.state.allowPlayer();
			//deny player interaction
			converter.state.denyPlayer();
		}
		else
		{
			//deny player interaction
			inventory.state.denyPlayer();
			//allow player interaction
			converter.state.allowPlayer();
		}
	}

	public int getTab()
	{
		//we need a getter for the gui
		return currentTab;
	}

	@Override
	public void update()
	{
		if (worldObj.isRemote)
			return;

		timerConvert++;
		timerExtract++;

		//time to process the conversion
		if (timerConvert > maxTimeConvert)
		{
			timerConvert = 0;
			//no iron or gold, or no room for output, abort
			if (slotIron.isEmpty() || slotGold.isEmpty() || slotDiamond.isFull())
				return;
			//not enough iron and gold, abort
			if (slotIron.getItemStack().stackSize < 2 || slotGold.getItemStack().stackSize < 2)
				return;

			//remove 2 iron from slot
			slotIron.extract(2);
			//remove 2 gold from slot
			slotGold.extract(2);
			//put 1 diamond in slot
			slotDiamond.insert(new ItemStack(Items.diamond));
		}

		//time to extract
		if (timerExtract > maxTimeExtract)
		{
			timerExtract = 0;
			//no diamond or inventory is full
			if (slotDiamond.isEmpty() || inventory.isFull())
				return;

			//remove 2 diamonds
			ItemStack itemStack = slotDiamond.extract(2);
			//transfer into inventory
			itemStack = inventory.transferInto(itemStack);
			//put what's left and couldn't fit into inventory back into the slot
			slotDiamond.insert(itemStack);
		}
	}

	@Override
	public MalisisInventory getInventory()
	{
		//make the default inventory
		return inventory;
	}

	@Override
	public MalisisInventory[] getInventories()
	{
		//return both inventories, because they will be both added to the container
		return new MalisisInventory[] { inventory, converter };
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		//get the gui to open (do not forget @SideOnly)
		return new TabInvGui(this, container);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		inventory.readFromNBT(tag.getCompoundTag("inventory"));
		converter.readFromNBT(tag.getCompoundTag("converter"));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		//write both inventories in their own tag
		NBTTagCompound inventoryTag = new NBTTagCompound();
		inventory.writeToNBT(inventoryTag);
		tag.setTag("inventory", inventoryTag);

		NBTTagCompound converterTag = new NBTTagCompound();
		converter.writeToNBT(converterTag);
		tag.setTag("converter", converterTag);
	}

	//special slot just to store what kind of filter we need for the slot
	//Java 8 will allow more efficient in-lined methods
	private static class FilterSlot extends MalisisSlot
	{
		private Item filter;

		public FilterSlot(int index, Item filter)
		{
			super(index);
			this.filter = filter;
		}

		@Override
		public boolean isItemValid(ItemStack itemStack)
		{
			return itemStack != null && itemStack.getItem() == filter;
		}

		@Override
		public int getSlotStackLimit()
		{
			return 40;
		}
	}
}
