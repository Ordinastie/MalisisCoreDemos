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

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.RenderType;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.ParallelTransformation;
import net.malisis.core.renderer.animation.transformation.Rotation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.icon.MalisisIcon;
import net.malisis.core.renderer.model.MalisisModel;
import net.malisis.demo.MalisisDemos;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * @author Ordinastie
 *
 */
public class ModelDemoRenderer extends MalisisRenderer<ModelDemoTileEntity>
{
	private AnimationRenderer ar = new AnimationRenderer();
	private Shape socle;
	private Shape antenna;
	private MalisisIcon socleIcon;
	private MalisisIcon antennaIcon;
	private RenderParameters rp;

	public ModelDemoRenderer()
	{
		registerFor(ModelDemoTileEntity.class);
	}

	@Override
	protected void initialize()
	{
		//load the model from the ResourceLocation
		ResourceLocation rlModel = new ResourceLocation(MalisisDemos.modid, "models/modeldemo.obj");
		MalisisModel model = new MalisisModel(rlModel);
		//get the shapes used in the model
		//the identifiers are the one used in the model editor for the different groups or objects
		socle = model.getShape("Socle");
		antenna = model.getShape("Antenna");

		//create IIconProviders for the two parts
		socleIcon = MalisisIcon.from(Blocks.coal_block);
		antennaIcon = MalisisIcon.from(Blocks.diamond_block);

		//create the params
		rp = new RenderParameters();

		//load the animations
		loadAnimation();
	}

	private void loadAnimation()
	{
		//the antenna part will be floating mid air up and down, while rotating on itself
		Translation t = new Translation(0, 0.0F, 0, 0, 0.3F, 0).movement(Transformation.SINUSOIDAL).forTicks(20);
		Translation t2 = new Translation(0, 0.0F, 0, 0, -0.3F, 0).movement(Transformation.SINUSOIDAL).forTicks(20);
		ChainedTransformation c = new ChainedTransformation(t, t2);

		//Rotation
		Rotation r = new Rotation(360).aroundAxis(0, 1, 0).forTicks(40);

		//complete transformation
		ParallelTransformation p = new ParallelTransformation(r, c).loop(-1);

		//Add the animation to the animation renderer
		ar.addAnimation(new Animation<>(antenna, p));
	}

	@Override
	public void render()
	{
		if (renderType == RenderType.BLOCK)
		{
			ar.setStartTime();
			return;
		}

		if (renderType == RenderType.TILE_ENTITY)
		{
			//if the model is not made with quads only, draw polygons otherwise,
			//the non animated part can (and should) be draw for BLOCK renderType
			next(GL11.GL_POLYGON);

			//draw the socle with corresponding icon
			socle.resetState();
			rp.icon.set(socleIcon);
			drawShape(socle, rp);

			//draw the antenna with corresponding icon
			antenna.resetState();
			ar.animate();
			rp.icon.set(antennaIcon);
			drawShape(antenna, rp);
		}

	}
}
