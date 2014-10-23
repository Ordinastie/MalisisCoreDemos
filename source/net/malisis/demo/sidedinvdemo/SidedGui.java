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

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.GuiRenderer;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.container.UIInventory;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.inventory.MalisisInventoryContainer;

import org.lwjgl.opengl.GL11;

/**
 * @author Ordinastie
 *
 */
public class SidedGui extends MalisisGui
{
	private ProgressBar progressBar;
	private SidedTileEntity tileEntity;

	public SidedGui(SidedTileEntity te, MalisisInventoryContainer container)
	{
		this.tileEntity = te;
		setInventoryContainer(container);

		UIWindow window = new UIWindow(this, 190, 230);

		UIInventory contTriage = new UIInventory(this, te.triageInventory, 10).setPosition(0, 0, Anchor.CENTER);
		UIInventory contIngots = new UIInventory(this, te.ingotsInventory, 4).setPosition(0, 40, Anchor.LEFT);
		UIInventory contStone = new UIInventory(this, te.stoneInventory, 4).setPosition(0, 40, Anchor.RIGHT);

		progressBar = new ProgressBar(this).setPosition(0, 30, Anchor.CENTER).setSize(contTriage.getWidth(), 5);

		UIPlayerInventory playerInv = new UIPlayerInventory(this, container.getPlayerInventory());

		window.add(contTriage);
		window.add(contIngots);
		window.add(contStone);
		window.add(playerInv);

		window.add(progressBar);

		addToScreen(window);
	}

	@Override
	public void update(int mouseX, int mouseY, float partialTick)
	{
		progressBar.setProgress(tileEntity.getTimer(partialTick));
	}

	public static class ProgressBar extends UIComponent<ProgressBar>
	{
		public ProgressBar(MalisisGui gui)
		{
			super(gui);
		}

		private float progress = 0;

		public void setProgress(float progress)
		{
			this.progress = progress;
		}

		@Override
		public void drawBackground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick)
		{
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			renderer.next(GL11.GL_LINE_STRIP);
			rp.colorMultiplier.set(0x444444);
			renderer.drawShape(shape, rp);
			renderer.next(GL11.GL_QUADS);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

		}

		@Override
		public void drawForeground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick)
		{
			shape.resetState();
			shape.setSize((int) (width * progress), height - 2).translate(0, 1);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			rp.colorMultiplier.set(0x008800);
			renderer.drawShape(shape, rp);
			renderer.next();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}
}
