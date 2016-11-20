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

package net.malisis.demo.almura;

import net.malisis.core.client.gui.GuiRenderer;
import net.malisis.core.client.gui.GuiTexture;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.container.UIListContainer;
import net.malisis.core.client.gui.element.SimpleGuiShape;
import net.malisis.core.renderer.RenderParameters;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Ordinastie
 *
 */
public class AboutList extends UIListContainer<AboutList, Pair<String, GuiTexture>>
{
	public AboutList(MalisisGui gui, int width, int height)
	{
		super(gui, width, height);
		shape = new SimpleGuiShape();
		rp = new RenderParameters();
	}

	@Override
	public int getElementHeight(Pair<String, GuiTexture> element)
	{
		return 20;
	}

	@Override
	public void drawElementBackground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick, Pair<String, GuiTexture> element, boolean isHovered)
	{
		renderer.disableTextures();
		rp.colorMultiplier.set(isHovered ? 0x333399 : 0x999999);
		shape.resetState();
		shape.setSize(getWidth(), getElementHeight(element));
		renderer.drawShape(shape, rp);
		renderer.next();
		renderer.enableTextures();
	}

	@Override
	public void drawElementForeground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick, Pair<String, GuiTexture> element, boolean isHovered)
	{
		//render icon
		renderer.bindTexture(element.getRight());

		rp.reset();
		shape.resetState();
		shape.setSize(16, 16);
		shape.translate(2, 2);

		renderer.drawShape(shape, rp);

		//render text
		renderer.drawText(element.getLeft(), 20, 4, 0);
	}
}
