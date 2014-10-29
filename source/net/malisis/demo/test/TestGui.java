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

package net.malisis.demo.test;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.GuiTexture;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.control.UIScrollBar;
import net.malisis.core.client.gui.component.decoration.UIImage;
import net.malisis.core.client.gui.component.decoration.UILabel;
import net.malisis.core.client.gui.component.interaction.UIButton;
import net.malisis.demo.MalisisDemos;
import net.minecraft.util.ResourceLocation;

import com.google.common.eventbus.Subscribe;

/**
 * @author Ordinastie
 *
 */
public class TestGui extends MalisisGui
{
	private UIButton bigButton;
	private UIButton upButton;
	private UIButton downButton;
	private UIButton leftButton;
	private UIButton rightButton;

	private UIButton hsButton;
	private UIButton vsButton;

	private UIScrollBar vertical;
	private UIScrollBar horizontal;

	public TestGui()
	{

		bigButton = new UIButton(this, "BIG BUTTON");
		bigButton.setSize(50, 50);
		bigButton.setPosition(100, 80);
		bigButton.setZIndex(0);

		UIPanel panel = new UIPanel(this).setPosition(0, 15).setSize(UIComponent.INHERITED, 110);
		panel.add(bigButton);

		for (int i = 1; i < 10; i++)
			panel.add(new UILabel(this, "Some label " + i).setPosition(0, 11 * (i - 1)));

		vertical = new UIScrollBar(this, panel, UIScrollBar.Type.VERTICAL).setAutoHide(true);
		horizontal = new UIScrollBar(this, panel, UIScrollBar.Type.HORIZONTAL).setAutoHide(true);

		upButton = new UIButton(this, "Up", 25).setPosition(0, -40, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);
		downButton = new UIButton(this, "Down", 25).setPosition(0, 0, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);
		leftButton = new UIButton(this, "Left", 25).setPosition(-30, -20, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);
		rightButton = new UIButton(this, "Right", 25).setPosition(30, -20, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);

		hsButton = new UIButton(this, "Horizontal").setPosition(0, 0, Anchor.BOTTOM | Anchor.RIGHT).setZIndex(1).register(this);
		vsButton = new UIButton(this, "Vertical").setPosition(0, -30, Anchor.BOTTOM | Anchor.RIGHT).setZIndex(1).register(this);

		UIWindow window = new UIWindow(this, "Test GUI", 300, 200);
		window.add(upButton);
		window.add(downButton);
		window.add(leftButton);
		window.add(rightButton);

		window.add(hsButton);
		window.add(vsButton);

		window.add(panel);

		GuiTexture bgTexture = new GuiTexture(new ResourceLocation(MalisisDemos.modid, "textures/background.png"), 1256, 768);
		UIImage bgImg = new UIImage(this, bgTexture, null);
		bgImg.setSize(0, 0);

		UIContainer bgCont = new UIContainer<>(this);
		bgCont.add(bgImg);

		addToScreen(bgCont);
		addToScreen(window);

	}

	@Subscribe
	public void onClick(UIButton.ClickEvent event)
	{
		UIButton b = event.getComponent();
		if (b == vsButton)
		{
			vertical.setVisible(!vertical.isVisible());
			return;
		}
		else if (b == hsButton)
		{
			horizontal.setVisible(!horizontal.isVisible());
			return;
		}

		int a = isShiftKeyDown() ? 10 : 1;
		int x = 0;
		int y = 0;
		if (b == upButton)
			y = -a;
		else if (b == downButton)
			y = a;
		else if (b == leftButton)
			x = -a;
		else if (b == rightButton)
			x = a;

		bigButton.setPosition(bigButton.getX() + x, bigButton.getY() + y);
	}
}
