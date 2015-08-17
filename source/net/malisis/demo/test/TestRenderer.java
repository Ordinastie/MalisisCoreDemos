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

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.RenderType;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.AlphaTransform;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.ColorTransform;
import net.malisis.core.renderer.animation.transformation.Rotation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.element.shape.Cube;
import net.malisis.core.renderer.model.MalisisModel;
import net.malisis.demo.MalisisDemos;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

/**
 * @author Ordinastie
 *
 */
public class TestRenderer extends MalisisRenderer
{
	private AnimationRenderer ar;
	private RenderParameters rp = new RenderParameters();
	private long startTime;

	private Transformation mvt;
	private Transformation color;
	private Transformation alpha;

	private MalisisModel model;

	private void setup(long start)
	{
		model = new MalisisModel(new ResourceLocation(MalisisDemos.modid, "models/sphere.obj"));

		startTime = start;
		shape.enableMergedVertexes();
		shape = new Cube();

		ar = new AnimationRenderer();

		//@formatter:off
		mvt = new Rotation(360).aroundAxis(0, 1, 0).forTicks(160).loop(-1);

		color = new ChainedTransformation(
					new ColorTransform(0xFF0000, 0x0000FF).forTicks(30),
					new ColorTransform(0x0000FF, 0x00FF00).forTicks(30),
					new ColorTransform(0x00FF00, 0xFF0000).forTicks(30)
				).loop(-1);
		alpha = new ChainedTransformation(
					new AlphaTransform(100, 255).forTicks(30),
					new AlphaTransform(255, 100).forTicks(30)
				).loop(-1);
		//@formatter:on
	}

	@Override
	public void render()
	{
		if (renderType == RenderType.ISBRH_INVENTORY)
		{
			shape.resetState();
			rp.icon.set(Blocks.glowstone.getIcon(0, 0));
			drawShape(shape, rp);
			return;
		}

		if (renderType == RenderType.ISBRH_WORLD)
		{
			shape.resetState();
			shape.setBounds(0.25F, 0, 0.25F, 0.75F, 0.5F, 0.75F);
			rp.icon.set(Blocks.glowstone.getIcon(0, 0));
			rp.renderAllFaces.set(true);
			drawShape(shape, rp);
			return;
		}

		if (renderType == RenderType.TESR_WORLD)
		{

		}
	}
}
