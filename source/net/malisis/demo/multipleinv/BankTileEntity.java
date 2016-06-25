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
import net.malisis.core.inventory.IInventoryProvider.IDirectInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Ordinastie
 *
 */
public class BankTileEntity extends TileEntity implements IDirectInventoryProvider
{
	MalisisInventory inventory;

	public BankTileEntity()
	{
		//create the inventory
		inventory = new MalisisInventory(this, new CardSlot[] { new CardSlot(0), new CardSlot(1), new CardSlot(2) });
	}

	@Override
	public MalisisInventory getInventory()
	{
		return inventory;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		inventory.readFromNBT(tagCompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		inventory.writeToNBT(tagCompound);
		return tagCompound;
	}

	@Override
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		return new BankGui(container);
	}

	public class CardSlot extends MalisisSlot
	{
		//the inventory stored in the card
		//we want to keep track of it so it can be removed for the container for this slot's inventory
		private MalisisInventory cardIventory;

		public CardSlot(int index)
		{
			super(index);
		}

		@Override
		public boolean isItemValid(ItemStack itemStack)
		{
			return itemStack.getItem() == MultipleInv.card;
		}

		@Override
		public void onSlotChanged()
		{
			//if there was an inventory before
			if (cardIventory != null)
			{
				//remove the card inventory from all opened containers
				for (MalisisInventoryContainer container : BankTileEntity.this.inventory.getOpenedContainers())
					container.removeInventory(cardIventory);
				cardIventory = null;
			}

			//stack is not null, new card is placed in the slot
			if (getItemStack() != null)
			{
				if (getItemStack().getTagCompound() != null && getItemStack().getTagCompound().hasKey("inventoryId"))
					return;

				//get the inventory from the card
				Card item = (Card) getItemStack().getItem();
				cardIventory = item.getInventory(getItemStack());

				//add the inventory to all opened containers
				for (MalisisInventoryContainer container : BankTileEntity.this.inventory.getOpenedContainers())
					container.addInventory(cardIventory);
			}

			BankTileEntity.this.inventory.onSlotChanged(this);
		}

		public MalisisInventory getCardInventory()
		{
			return cardIventory;
		}
	}

}
