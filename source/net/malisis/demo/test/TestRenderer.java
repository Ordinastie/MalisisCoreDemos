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

import net.malisis.core.renderer.BaseRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.Rotation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.shape.Cube;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;

/**
 * @author Ordinastie
 *
 */
public class TestRenderer extends BaseRenderer
{
	public static int renderId;
	private AnimationRenderer ar;
	private RenderParameters rp = new RenderParameters();
	private Shape base;
	private long startTime;

	private Block[] blocks;

	private Transformation transform;

	public TestRenderer()
	{
		setup(0);

	}

	private void setup(long start)
	{
		blocks = new Block[] { Blocks.dirt, Blocks.stone, Blocks.crafting_table };
		startTime = start;

		float f = 0.1875F;
		Shape v = new Cube().setSize(f, 1, f);
		Shape h = new Cube().setSize(1, f, f);

		base = new Shape().addFaces(v.getFaces(), "v").addFaces(h.getFaces(), "h");
		base.interpolateUV();
		base.storeState();

		rp.interpolateUV.set(false);
		rp.icon.set(null);

		ar = new AnimationRenderer(this);

		transform = new Rotation(360).aroundAxis(0, 1, 0).forTicks(120).loop(-1);
	}

	@Override
	public void render()
	{
		if (renderType == TYPE_TESR_WORLD)
		{
			enableBlending();
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			double dist = Vec3.createVectorHelper(x, y, z).squareDistanceTo(player.posX, player.posY, player.posZ) - 10;
			if (dist > 30)
				dist = 30;
			if (dist < 0)
				dist = 0;
			dist /= 30;
			int alpha = (int) (dist * 255);

			Shape s = new Cube();
			rp.icon.set(block.getIcon(0, 0));
			rp.alpha.set(255);
			drawShape(s, rp);

			s.resetState();
			rp.icon.set(block.getIcon(0, 1));
			rp.alpha.set(255 - alpha);
			drawShape(s, rp);
		}

	}
}
