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

import net.malisis.core.client.gui.GuiRenderer;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.element.SimpleGuiShape;
import net.malisis.core.renderer.element.Bezier;
import net.malisis.core.renderer.element.Face;
import net.malisis.core.renderer.element.Vertex;

import org.lwjgl.opengl.GL11;

/**
 * @author Ordinastie
 *
 */
public class TestGui extends MalisisGui
{

	@Override
	public void construct()
	{
		UIWindow window = new UIWindow(this, "Test GUI", 300, 200);
		UIBezier bezier = new UIBezier(this);

		window.add(bezier);
		addToScreen(window);
	}

	public class UIBezier extends UIComponent<UIBezier>
	{
		public UIBezier(MalisisGui gui)
		{
			super(gui);
			//@formatter:off
			Vertex[] controlPoints = new Vertex[] {
					new Vertex(0.5F, 0, 0),
					new Vertex(0.0F, 0, 0),
					new Vertex(0, 0.5F, 0),
					new Vertex(0.5F, 1, 0),
					new Vertex(0, 0.5F, 0),
					new Vertex(1, 0.5F, 0),
					new Vertex(0.5F, 0, 0),
			};
			//@formatter:on

			Bezier bezier = new Bezier(controlPoints, 60);
			Face face = new Face(bezier.getVertexes());
			shape = new SimpleGuiShape(face);
		}

		@Override
		public void drawBackground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick)
		{}

		@Override
		public void drawForeground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick)
		{
			//renderer.next(GL11.GL_LINE_LOOP);
			//renderer.next(GL11.GL_TRIANGLE_FAN);
			renderer.next(GL11.GL_POLYGON);
			renderer.disableTextures();
			rp.colorMultiplier.set(0x333399);
			renderer.drawShape(shape, rp);

			renderer.next(GL11.GL_QUADS);
			renderer.enableTextures();

		}

	}

}
