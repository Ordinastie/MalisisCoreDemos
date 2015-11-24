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
import net.malisis.core.inventory.ISidedInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

/**
 * This tile entity will handle 3 different inventories :<br>
 * - triage : can accept everything<br>
 * - ingots : can only accept gold or iron ingots<br>
 * - stone : can only accept stone or stone bricks<br>
 * Every second the inventory will move a stack from the triage to either ingots or stone inventory. The top face of the block can insert
 * into triage inventory, the east face can insert into ingots inventory and west face can insert into stone inventory
 *
 * @author Ordinastie
 *
 */
public class SidedTileEntity extends TileEntity implements ISidedInventoryProvider, IUpdatePlayerListBox
{
	//the 3 different inventories
	public MalisisInventory triageInventory;
	public MalisisInventory ingotsInventory;
	public MalisisInventory stoneInventory;
	//current processing timer
	public int timer;
	//total time before processing again
	public int totalTime = 60;

	public SidedTileEntity()
	{
		//create the inventories : 10 slots for triage
		triageInventory = new MalisisInventory(this, 10);
		//16 slots for ingots
		ingotsInventory = new MalisisInventory(this, 16)
		{
			@Override
			public boolean itemValidForSlot(MalisisSlot slot, ItemStack itemStack)
			{
				//accept only gold or iron ingots
				return itemStack != null && (itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot);
			};
		};
		//16 slots for stone
		stoneInventory = new MalisisInventory(this, 16)
		{
			@Override
			public boolean itemValidForSlot(MalisisSlot slot, ItemStack itemStack)
			{
				if (itemStack == null)
					return false;

				//accept only stone or stone bricks
				Block block = Block.getBlockFromItem(itemStack.getItem());
				return block != null && (block == Blocks.stone || block == Blocks.stonebrick);
			}
		};

		//give inventories a name (for display in gui)
		triageInventory.setName("Triage");
		ingotsInventory.setName("Ingots");
		stoneInventory.setName("Stones");
	}

	@Override
	public MalisisInventory getInventory()
	{
		return triageInventory;
	}

	@Override
	public MalisisInventory getInventory(EnumFacing side)
	{
		switch (side)
		{
			case UP:
				return triageInventory;
			case EAST:
				return stoneInventory;
			case WEST:
				return ingotsInventory;
			default:
				return null;
		}
	}

	public float getTimer(float partialTick)
	{
		//gets the timer for display in GUI
		if (!triageInventory.isEmpty())
			return (timer + partialTick) / totalTime;
		else
			return 0;
	}

	@Override
	public void update()
	{
		timer++;
		//time to process
		if (timer >= totalTime)
		{
			timer = 0;
			//only process on server
			//			if (worldObj.isRemote)
			//				return;

			MalisisSlot slot = triageInventory.getFirstOccupiedSlot();
			//if inventory is empty, no process to do
			if (slot == null)
				return;

			ItemStack itemStack = slot.getItemStack();
			//transfer into triage if gold or iron ingot
			if (itemStack != null && (itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot))
				itemStack = ingotsInventory.transferInto(itemStack);
			else
			{
				Block block = Block.getBlockFromItem(itemStack.getItem());
				if (block != null && (block == Blocks.stone || block == Blocks.stonebrick))
					itemStack = stoneInventory.transferInto(itemStack);
			}
			//itemStack hold the result of the transfer : if the target inventory was full and could not
			//accept all of the itemStack, itemStack hold what's left, so put it back in the slot
			slot.setItemStack(itemStack);
		}

	}

	@Override
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		//get the GUI for this TE
		return new SidedGui(this, container);
	}

}
