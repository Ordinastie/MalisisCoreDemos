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
import net.malisis.core.renderer.preset.ShapePreset;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

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
		Shape v = ShapePreset.Cube().setSize(f, 1, f);
		Shape h = ShapePreset.Cube().setSize(1, f, f);

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
		//		TestTileEntity te = (TestTileEntity) tileEntity;
		//		if (te.startTime != startTime)
		//			setup(te.startTime);
		//
		//		ar.setStartTime(startTime);
		//
		//		base.resetState();
		//		ar.animate(base, transform);
		//
		//		rp.icon.set(Blocks.brick_block.getIcon(2, 0));
		//		base.setParameters("v", rp, true);
		//		rp.icon.set(null);
		//
		//		set(blocks[te.num]);
		//		drawShape(base, rp);
		t.addVertexWithUV(0, 0, 0, 0, 0);//bottom left texture
		t.addVertexWithUV(0, 1, 0, 0, 1);//bottom left texture
		t.addVertexWithUV(1, 1, 0, 1, 1);//bottom left texture
		t.addVertexWithUV(1, 0, 0, 1, 0);//bottom left texture

		//model.render(this);
	}
}
