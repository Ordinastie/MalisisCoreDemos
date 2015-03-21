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

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.ComponentPosition;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIInventory;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UITabGroup;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.interaction.UITab;
import net.malisis.core.inventory.MalisisInventoryContainer;

import com.google.common.eventbus.Subscribe;

/**
 * @author Ordinastie
 *
 */
public class TabInvGui extends MalisisGui
{
	//UITabs for the GUI. We need to keep track of the instances for the TabChangeEvent
	private UITab tabInventory;
	private UITab tabConverter;
	//we need to keep track of the TE to set the current tab on the client too
	private TabInvTileEntity te;

	public TabInvGui(TabInvTileEntity te, MalisisInventoryContainer container)
	{
		//link this Gui with a container
		//activates some behavior input behavior and render (like the picked itemStack on the cursor)
		setInventoryContainer(container);
		this.te = te;
	}

	@Override
	public void construct()
	{

		//create the first window, with a title, size and position
		UIWindow inventoryWindow = new UIWindow(this, "TE Inventory", UIPlayerInventory.INVENTORY_WIDTH + 10, 100).setPosition(0, -60);
		//create the inventory container and with the first inventory
		UIInventory inv = new UIInventory(this, inventoryContainer.getInventory(1), 4).setPosition(0, 0, Anchor.CENTER | Anchor.MIDDLE);
		//add the inventory to the window
		inventoryWindow.add(inv);

		//create the second window (needs to be same size and position as first
		UIWindow converterWindow = new UIWindow(this, "Converter", UIPlayerInventory.INVENTORY_WIDTH + 10, 100).setPosition(0, -60);;
		//this time, we add the slots manually to the window because we want them to have custom position
		UISlot uislot = new UISlot(this, te.slotIron).setPosition(-30, 20, Anchor.CENTER);
		converterWindow.add(uislot);
		uislot = new UISlot(this, te.slotGold).setPosition(30, 20, Anchor.CENTER);
		converterWindow.add(uislot);
		uislot = new UISlot(this, te.slotDiamond).setPosition(0, 60, Anchor.CENTER);
		converterWindow.add(uislot);

		//create the tabGroup that will hold the tabs
		//the position is relative to the container it will be attached to
		UITabGroup tabGroup = new UITabGroup(this, ComponentPosition.BOTTOM).setPosition(10, 0);
		//create the tabs with a title. Can accept UIImage too (and UIImage can be Icon+Texture or directly ItemStack)
		//register this tells that this gui will receive events fired by the tab (TabChangeEvent and input events but we don't catch them)
		tabInventory = new UITab(this, "TE Inventory").register(this);
		tabConverter = new UITab(this, "Converter").register(this);
		//add tab to group with the container it's linked to
		tabGroup.addTab(tabInventory, inventoryWindow);
		tabGroup.addTab(tabConverter, converterWindow);
		//set the active tab otherwise, no window will be visible as addTab() automatically hides the container
		tabGroup.setActiveTab(te.getTab() == 1 ? tabInventory : tabConverter);
		//attach the group to a container so it will use the same graphic and renders itself relative to that container
		tabGroup.attachTo(inventoryWindow, false);

		//create a separate window to display the player's inventory
		UIWindow playerWindow = new UIWindow(this, UIPlayerInventory.INVENTORY_WIDTH + 10, UIPlayerInventory.INVENTORY_HEIGHT + 10);
		//windows are centered by default, so 0, 60 mean x-centered, and y-centered + 60 px (to leave place for the tabs)
		playerWindow.setPosition(0, 60);
		//create the player inventory container
		UIPlayerInventory playerInv = new UIPlayerInventory(this, inventoryContainer.getPlayerInventory());
		//add player inv
		playerWindow.add(playerInv);

		//add all the windows on the screen
		addToScreen(tabGroup);
		addToScreen(inventoryWindow);
		addToScreen(converterWindow);
		addToScreen(playerWindow);
	}

	@Subscribe
	public void onTabChange(UITabGroup.TabChangeEvent event)
	{
		int idx = event.getNewTab() == tabInventory ? 1 : 2;
		//set the tab in the TE for the client
		te.setTab(idx);
		//send info to server
		TabInvMessage.sendTab(idx);

	}
}
