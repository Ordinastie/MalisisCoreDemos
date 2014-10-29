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

package net.malisis.demo.waves;

import java.util.List;

import net.malisis.core.renderer.BaseRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.MergedVertex;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.face.TopFace;
import net.minecraft.client.Minecraft;

/**
 * @author Ordinastie
 *
 */
public class WaveRenderer extends BaseRenderer
{
	private AnimationRenderer ar;
	private long startTime;

	private Transformation mvt;

	@Override
	protected void initShapes()
	{
		shape = new Shape(new TopFace());
	}

	@Override
	protected void initParameters()
	{
		rp = new RenderParameters();
		rp.interpolateUV.set(true);
		rp.applyTexture.set(false);
	}

	private void setup(long start)
	{
		startTime = start;
		shape.enableMergedVertexes();

		ar = new AnimationRenderer(this);

		//@formatter:off
		mvt = new ChainedTransformation(
					new Translation(0, -0.3F, 0, 0, 0, 0).forTicks(20).movement(Transformation.SINUSOIDAL),
					new Translation(0, 0, 0, 0, -0.3F, 0).forTicks(20).movement(Transformation.SINUSOIDAL)
				).loop(-1);
		//@formatter:on
	}

	@Override
	public void render()
	{
		if (renderType == TYPE_ISBRH_WORLD)
		{
			setup((int) Minecraft.getMinecraft().theWorld.getTotalWorldTime());
		}

		if (renderType == TYPE_TESR_WORLD)
		{
			ar.setStartTime(startTime);
			enableBlending();

			shape.resetState();
			applyTexture(shape);

			List<MergedVertex> mvs = shape.getMergedVertexes(shape.getFace("Top"));
			for (MergedVertex mv : mvs)
			{
				int delay = 0;
				if ((x + mv.getX()) % 2 == 0)
					delay += 10;
				if ((z + mv.getZ()) % 2 != 0)
					delay += 10;
				mvt.delay(delay);
				mvt.transform(mv, ar.getElapsedTime());
			}

			drawShape(shape, rp);
		}
	}
}
