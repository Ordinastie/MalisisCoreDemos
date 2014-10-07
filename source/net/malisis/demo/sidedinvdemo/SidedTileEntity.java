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

package net.malisis.demo.sidedinvdemo;

import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.inventory.InventoryEvent;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.malisis.core.tileentity.TileEntitySidedInventory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.eventbus.Subscribe;

/**
 * @author Ordinastie
 *
 */
public class SidedTileEntity extends TileEntitySidedInventory
{
	public MalisisInventory triageInventory;
	public MalisisInventory ingotsInventory;
	public MalisisInventory stoneInventory;
	public int timer;
	public int totalTime = 20;

	public SidedTileEntity()
	{
		triageInventory = new MalisisInventory(this, 10);
		ingotsInventory = new MalisisInventory(this, 16)
		{
			@Override
			public boolean isItemValidForSlot(int slotNumber, ItemStack itemStack)
			{
				return itemStack != null && (itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot);
			};
		};
		stoneInventory = new MalisisInventory(this, 16)
		{
			@Override
			public boolean isItemValidForSlot(int slotNumber, ItemStack itemStack)
			{
				if (itemStack == null)
					return false;

				Block block = Block.getBlockFromItem(itemStack.getItem());
				return block != null && (block == Blocks.stone || block == Blocks.stonebrick);
			}
		};

		triageInventory.setName("Triage");
		ingotsInventory.setName("Ingots");
		stoneInventory.setName("Stones");
		triageInventory.register(this);

		addSidedInventory(triageInventory, ForgeDirection.UP);
		addSidedInventory(ingotsInventory, ForgeDirection.EAST);
		addSidedInventory(stoneInventory, ForgeDirection.WEST);
	}

	public float getTimer(float partialTick)
	{
		if (!triageInventory.isEmpty())
			return (timer + partialTick) / totalTime;
		else
			return 0;
	}

	@Override
	public void updateEntity()
	{
		timer++;
		if (timer >= totalTime)
		{
			timer = 0;
			if (worldObj.isRemote)
				return;

			MalisisSlot slot = triageInventory.getFirstOccupiedSlot();
			if (slot == null)
				return;
			ItemStack itemStack = slot.getItemStack();
			if (itemStack != null && (itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot))
				itemStack = ingotsInventory.transferInto(itemStack);
			else
			{
				Block block = Block.getBlockFromItem(itemStack.getItem());
				if (block != null && (block == Blocks.stone || block == Blocks.stonebrick))
					itemStack = stoneInventory.transferInto(itemStack);
			}
			slot.setItemStack(itemStack);
		}

	}

	public void getOpenInventory(EntityPlayerMP player)
	{

	}

	@Override
	public MalisisInventory getInventory(Object... data)
	{
		return triageInventory;
	}

	@Override
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		return new SidedGui(this, container);
	}

	@Subscribe
	public void onOpenInventory(InventoryEvent.Open event)
	{
		event.getContainer().addInventory(ingotsInventory);
		event.getContainer().addInventory(stoneInventory);
	}
}
