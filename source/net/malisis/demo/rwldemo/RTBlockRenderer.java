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

package net.malisis.demo.rwldemo;

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.shape.Cube;

/**
 * @author Ordinastie
 *
 */
public class RTBlockRenderer extends MalisisRenderer
{
	private Shape s;

	@Override
	protected void initialize()
	{
		int n = 4;
		float f = (float) 1 / n;

		Shape t = new Cube();
		t.setSize(1, f, f);

		Shape[] shapes = new Shape[n];
		for (int i = 0; i < n; i++)
		{
			shapes[i] = new Shape(t);
			shapes[i].translate(0, i * f, i * f);
		}

		s = Shape.fromShapes(shapes);
		s.storeState();

		rp = new RenderParameters();
		rp.renderAllFaces.set(true);
	}

	@Override
	public void render()
	{
		initialize();

		drawShape(s, rp);
	}

}
