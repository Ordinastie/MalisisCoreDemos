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

package net.malisis.demo.fontdemo;

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.Face;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.font.FontRenderOptions;
import net.malisis.core.renderer.font.MalisisFont;
import net.malisis.core.util.EntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * @author Ordinastie
 *
 */
public class FontDemoRenderer extends MalisisRenderer
{
	//The font that will be used
	MalisisFont font;
	//The shape holding the text : "Custom font with cool animations!"
	Shape shapeString;
	//Parameters
	RenderParameters rp;
	//The Animation renderer that handles the animations for the shape
	AnimationRenderer ar = new AnimationRenderer();

	@Override
	protected void initialize()
	{
		rp = new RenderParameters();
		rp.colorMultiplier.set(0x9999FF);
		loadFont();
		loadShapeAndAnimations();
	}

	@Override
	public boolean shouldRender(RenderWorldLastEvent event, IBlockAccess world)
	{
		//render only if not further away than 64 blocks away from origin of the world
		return Minecraft.getMinecraft().thePlayer.getPosition().distanceSq(0, 0, 0) < 1024;
	}

	private void loadFont()
	{
		//font is loaded from a regular ResourceLocation
		//keep in mind that the process takes time, especially the first time when the font sheet is generated
		ResourceLocation rl = new ResourceLocation(MalisisDemos.modid + ":fonts/HoboStd.otf");
		font = new MalisisFont(rl);
	}

	private void loadShapeAndAnimations()
	{
		String text = "Custom font with cool animations!";
		//create the Shape from the text with the font where each letter is a Face
		//(the position is hard coded to be placed against a wall in my test world)
		shapeString = font.getShape(text, 1f);
		shapeString.translate(-20, 4, -19.8F);
		shapeString.storeState();

		//create the animations : letters will move up and down in succession (increased delay for each letter)
		int delay = 0;
		float amount = 0.75F;
		int ticks = 8;
		for (Face f : shapeString.getFaces())
		{
			//up
			Translation t = new Translation(0, 0, 0, 0, amount, 0).forTicks(ticks).movement(Transformation.SINUSOIDAL);
			//down
			Translation t2 = new Translation(0, 0, 0, 0, -amount, 0).forTicks(ticks).movement(Transformation.SINUSOIDAL);
			//chained animation with up and down with greater delay than previous letter
			ChainedTransformation chain = new ChainedTransformation(t, t2).delay(delay++ * 2);

			//make the animation for the face (letter) and add it to the AnimationRenderer
			ar.addAnimation(new Animation(f, chain));
		}

	}

	public void animate()
	{
		//by setting the starting time for the AnimationRenderer, the animations start again
		ar.setStartTime();
	}

	@Override
	public void render()
	{
		//bind the font texture
		bindTexture(font.getResourceLocation());

		//reset the shape state
		shapeString.resetState();
		//animate
		ar.animate();
		//draw the shape
		drawShape(shapeString, rp);
		next();

		//for direct rendering, you can specify options
		FontRenderOptions fro = new FontRenderOptions();
		fro.color = 0x339966;
		fro.fontScale = 0.25f;
		fro.shadow = true;
		fro.underline = true;

		//hard coded position
		float fx = -11;
		float fy = 3.5F;
		float fz = 0;

		//some text with EnumChatFormatting inside
		String str = "Just an " + EnumChatFormatting.GOLD + "example with " + EnumChatFormatting.RED + "custom color"
				+ EnumChatFormatting.RESET + " and reset after";
		//render the text at the position with the options
		font.render(this, str, fx, fy, fz, fro);

		//change options
		fro.color = 0x666666;
		fro.underline = false;
		//default need to be saved again if the same object is to be reused with different options
		fro.saveDefault();
		//set the position lower
		fy -= 0.4F;
		//some "dynamic text"
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		BlockPos p = player.getPosition();
		str = "Player position : " + EnumChatFormatting.DARK_AQUA + p.getX() + ", " + p.getY() + ", " + p.getZ();
		//render the text at the position with the options
		font.render(this, str, fx, fy, fz, fro);
		fy -= 0.3F;
		str = "Facing : " + EnumChatFormatting.DARK_AQUA + EntityUtils.getEntityFacing(player) + " ("
				+ EntityUtils.getEntityRotation(player) + ")";
		font.render(this, str, fx, fy, fz, fro);

	}
}
