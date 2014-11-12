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

import java.util.HashMap;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.inventory.InventoryEvent;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.malisis.demo.multipleinv.BankTileEntity.CardSlot;

import com.google.common.eventbus.Subscribe;

/**
 * @author Ordinastie
 *
 */
public class BankGui extends MalisisGui
{
	private HashMap<CardSlot, UIContainer> slotCont = new HashMap<>();

	public BankGui(MalisisInventoryContainer container)
	{
		setInventoryContainer(container);

		UIWindow window = new UIWindow(this, "Bank", UIPlayerInventory.INVENTORY_WIDTH + 39,
				62 * 3 + UIPlayerInventory.INVENTORY_HEIGHT + 30);

		int i = 0;
		for (MalisisSlot slot : container.getInventory(1).getSlots())
		{
			UIPanel panel = new UIPanel(this, UIPlayerInventory.INVENTORY_WIDTH + 29, 62);
			panel.setPosition(0, 10 + i++ * 65);

			UIContainer uicont = new UIContainer<>(this, UIPlayerInventory.INVENTORY_WIDTH, UIComponent.INHERITED);
			uicont.setPosition(0, 0, Anchor.RIGHT);

			UISlot uislot = new UISlot(this, slot);
			slot.register(this);

			panel.add(uislot);
			panel.add(uicont);

			window.add(panel);

			slotCont.put((CardSlot) slot, uicont);
		}

		UIPlayerInventory playerInv = new UIPlayerInventory(this, container.getPlayerInventory());
		playerInv.setPosition(-3, 0, Anchor.BOTTOM | Anchor.RIGHT);

		window.add(playerInv);

		addToScreen(window);
	}

	@Subscribe
	public void slotChanged(InventoryEvent.SlotChanged event)
	{
		CardSlot slot = (CardSlot) event.getSlot();
		UIContainer uicont = slotCont.get(slot);
		uicont.removeAll();
		MalisisInventory inv = slot.getCardInventory();
		if (inv != null)
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				UISlot uislot = new UISlot(this, inv.getSlot(i));
				uislot.setPosition((i % 9) * 18, (i / 9) * 18);
				uicont.add(uislot);
			}
	}

}
