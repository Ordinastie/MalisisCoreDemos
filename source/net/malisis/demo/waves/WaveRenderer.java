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
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.BrightnessTransform;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.MergedVertex;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.face.TopFace;
import net.malisis.core.renderer.element.shape.Cube;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

/**
 * @author Ordinastie
 *
 */
public class WaveRenderer extends BaseRenderer
{
	//Create the animation renderer
	private AnimationRenderer ar = new AnimationRenderer(this);
	//start time (reset each time the block is redrawn (for debug purpose)
	private long startTime;

	//the transform that handles the movement of the vertexes
	private Transformation mvt;
	//the transform that handle the brightness of the vertexes
	private Transformation br;

	//shape used for the TESR
	private Shape wave;
	//shape used for inventory
	private Shape cube;

	@Override
	protected void initialize()
	{
		//creates the shapes
		wave = new Shape(new TopFace());
		cube = new Cube();
		//enable the mergedVertexes so we can easily manipulate them with the animations
		wave.enableMergedVertexes();
	}

	//should be called only once from initialize(). For debug purpose, we recall it every time the block asks to be
	//redrawn so we can change the transforms on the fly
	private void setup(long start)
	{
		startTime = start;

		//@formatter:off
		mvt = new ChainedTransformation(
					new Translation(0, -0.2F, 0, 0, 0, 0).forTicks(50).movement(Transformation.SINUSOIDAL),
					new Translation(0, 0, 0, 0, -0.2F, 0).forTicks(50).movement(Transformation.SINUSOIDAL)
				).loop(-1);
		br = new ChainedTransformation(
					new BrightnessTransform(0, 240).forTicks(100).movement(Transformation.SINUSOIDAL),
					new BrightnessTransform(240, 0).forTicks(100).movement(Transformation.SINUSOIDAL)
			).loop(-1);

		//@formatter:on
	}

	@Override
	public void render()
	{
		if (renderType == TYPE_ISBRH_INVENTORY)
		{
			drawShape(cube);
			return;
		}

		if (renderType == TYPE_ISBRH_WORLD)
		{
			setup((int) Minecraft.getMinecraft().theWorld.getTotalWorldTime());
			return;
		}

		if (renderType == TYPE_TESR_WORLD)
		{
			ar.setStartTime(startTime);
			enableBlending();
			shape.resetState();
			rp.usePerVertexBrightness.set(true);

			List<MergedVertex> mvs = shape.getMergedVertexes(shape.getFace("Top"));
			for (MergedVertex mv : mvs)
			{
				int delay = 0;
				int x = (int) ((this.x + mv.getX()) % 10);
				delay += 10 * Math.abs(x - 5);
				int z = (int) ((this.z + mv.getZ()) % 10);
				delay += 10 * Math.abs(z - 5);;
				mvt.delay(delay);
				mvt.transform(mv, ar.getElapsedTime());
				br.delay(delay * 2);
				br.transform(mv, ar.getElapsedTime());
			}
			rp.icon.set(Blocks.water.getIcon(1, 0));
			drawShape(wave, rp);
		}
	}
}
