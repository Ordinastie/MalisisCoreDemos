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

package net.malisis.demo.model;

import net.malisis.core.renderer.BaseRenderer;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.model.MalisisModel;
import net.malisis.demo.MalisisDemos;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * @author Ordinastie
 *
 */
public class ModelDemoRenderer extends BaseRenderer
{
	public static int renderId;
	private ResourceLocation rlModel = new ResourceLocation(MalisisDemos.modid, "models/hopper.obj");
	private MalisisModel model;
	private AnimationRenderer ar;
	private Shape socle;
	private Shape antenna;
	private int start;

	@Override
	protected void initShapes()
	{
		boolean b = false;
		if (b)
		{
			rlModel = new ResourceLocation(MalisisDemos.modid, "models/modeldemo.obj");
			model = MalisisModel.load(rlModel);
			socle = model.getShape("Socle");
			socle.storeState();
			antenna = model.getShape("Antenna");
			antenna.storeState();

			loadAnimation();
		}
		else
		{
			rlModel = new ResourceLocation(MalisisDemos.modid, "models/slab.obj");
			model = MalisisModel.load(rlModel);
		}
	}

	private void loadAnimation()
	{
		ar = new AnimationRenderer(this);

		//Rotation r = new Rotation(360).aroundAxis(0, 1, 0).forTicks(40).loop(-1, 0, 0);
		Translation t = new Translation(0, -0.3F, 0, 0, 0.3F, 0).forTicks(20);
		//Translation t2 = new Translation(0, 0.1F, 0, 0, -0.1F, 0).forTicks(20).movement(Transformation.SINUSOIDAL);
		ChainedTransformation c = new ChainedTransformation(t, t);

		//ParallelTransformation p = new ParallelTransformation(r, c).loop(-1, 0, 0);

		Animation anim = new Animation(antenna, c, rp, 0);
		ar.addAnimation(anim);
	}

	@Override
	public void render()
	{
		boolean b = false;

		if (renderType == TYPE_ISBRH_WORLD)
		{
			initShapes();
			start = (int) Minecraft.getMinecraft().theWorld.getTotalWorldTime();
		}
		else if (renderType == TYPE_TESR_WORLD)
		{
			if (b)
			{

				ar.setStartTime(start);
				next(GL11.GL_POLYGON);

				rp.icon.set(Blocks.anvil.getIcon(0, 0));
				drawShape(socle, rp);

				rp.icon.set(Blocks.diamond_block.getIcon(0, 0));
				ar.animate();
				drawShape(antenna, rp);

			}
			else
			{
				rp.icon.set(Blocks.planks.getIcon(0, 0));
				shape = model.getShape("Default");
				if (shape != null)
				{
					shape.resetState();
					drawShape(shape, rp);
				}
			}

		}

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}
}
