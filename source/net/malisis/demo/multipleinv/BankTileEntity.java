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
import net.malisis.core.inventory.MalisisSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Ordinastie
 *
 */
public class BankTileEntity extends TileEntity implements IInventoryProvider
{
	MalisisInventory inventory;

	public BankTileEntity()
	{
		inventory = new MalisisInventory(this, new CardSlot[] { new CardSlot(0), new CardSlot(1), new CardSlot(2) });

	}

	@Override
	public MalisisInventory getInventory(Object... data)
	{
		return inventory;
	}

	@Override
	public MalisisInventory getInventory(ForgeDirection side, Object... data)
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
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		inventory.writeToNBT(tagCompound);
	}

	@Override
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		return new BankGui(container);
	}

	public class CardSlot extends MalisisSlot
	{
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
			if (cardIventory != null)
			{
				for (MalisisInventoryContainer container : BankTileEntity.this.inventory.getOpenedContainers())
					container.removeInventory(cardIventory);
				cardIventory = null;
			}

			if (getItemStack() != null)
			{
				if (getItemStack().stackTagCompound != null && getItemStack().stackTagCompound.hasKey("inventoryId"))
					return;

				Card item = (Card) getItemStack().getItem();
				cardIventory = item.getInventory(getItemStack());

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
