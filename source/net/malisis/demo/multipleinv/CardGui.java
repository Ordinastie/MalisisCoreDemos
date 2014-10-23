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

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;

/**
 * @author Ordinastie
 *
 */
public class CardGui extends MalisisGui
{
	public CardGui(MalisisInventoryContainer container)
	{
		setInventoryContainer(container);

		//add padding to width
		UIWindow window = new UIWindow(this, UIPlayerInventory.INVENTORY_WIDTH + 10, UIPlayerInventory.INVENTORY_HEIGHT * 2);

		UIContainer invCont = new UIContainer<>(this, "Card", UIPlayerInventory.INVENTORY_WIDTH, UIPlayerInventory.INVENTORY_HEIGHT);
		invCont.setPosition(0, 0, Anchor.CENTER);
		MalisisInventory inv = container.getInventory(1);
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			UISlot uislot = new UISlot(this, inv.getSlot(i));
			uislot.setPosition((i % 9) * 18, (i / 9) * 18 + 11);
			invCont.add(uislot);
		}

		//by default, player inventory is anchored to bottom center of its container
		UIPlayerInventory playerInv = new UIPlayerInventory(this, container.getPlayerInventory());

		window.add(playerInv);
		window.add(invCont);

		addToScreen(window);
	}
}
