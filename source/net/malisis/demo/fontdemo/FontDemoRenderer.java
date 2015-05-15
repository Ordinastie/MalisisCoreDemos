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

import java.io.IOException;

import net.malisis.core.MalisisCore;
import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.Face;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.font.FontGeneratorOptions;
import net.malisis.core.renderer.font.FontRenderOptions;
import net.malisis.core.renderer.font.MalisisFont;
import net.malisis.core.renderer.font.MinecraftFont;
import net.malisis.demo.MalisisDemos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

/**
 * @author Ordinastie
 *
 */
public class FontDemoRenderer extends MalisisRenderer
{
	public static FontDemoRenderer instance;
	MalisisFont font;
	Shape shapeString;
	int color = 0;
	AnimationRenderer ar = new AnimationRenderer();
	Animation anim;

	public FontDemoRenderer()
	{
		instance = this;
	}

	@Override
	protected void initialize()
	{
		loadFont(false, false);
		loadAnimations();
	}

	private void loadFont(boolean generate, boolean debug)
	{
		//ResourceLocation rl = new ResourceLocation(MalisisDemos.modid + ":fonts/BrushScriptStd.otf");
		//ResourceLocation rl = new ResourceLocation(MalisisDemos.modid + ":fonts/minecraft.ttf");
		ResourceLocation rl = new ResourceLocation(MalisisDemos.modid + ":fonts/HoboStd.otf");
		//ResourceLocation rl = new ResourceLocation(MalisisDemos.modid + ":fonts/Ubuntu-R.ttf");

		FontGeneratorOptions options = new FontGeneratorOptions();
		options.my = -20;
		options.py = 40;
		options.antialias = true;
		options.fontSize = 256f;
		try
		{
			font = new MalisisFont(rl, options);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//font = new MinecraftFont();

		if (generate && !(font instanceof MinecraftFont))
		{
			font.generateTexture(debug);
			MalisisCore.message("Reloaded font texture (debug=" + debug + ")");
		}
		color = debug ? -1 : 0;
	}

	public void reloadFont(boolean debug)
	{
		loadFont(true, debug);
	}

	private void loadAnimations()
	{
		String text = "Custom font with cool animations!";
		//text = "Custom";
		//font = MalisisFont.minecraftFont;
		shapeString = font.getShape(text, 1f);
		if (shapeString != null)
		{
			shapeString.translate(-20, 4, -19.8F);

			shapeString.storeState();

			int i = 0;
			float a = 0.75F;
			int ticks = 8;
			for (Face f : shapeString.getFaces())
			{
				Translation t = new Translation(0, 0, 0, 0, a, 0).forTicks(ticks).movement(Transformation.SINUSOIDAL);
				Translation t2 = new Translation(0, 0, 0, 0, -a, 0).forTicks(ticks).movement(Transformation.SINUSOIDAL);
				ChainedTransformation chain = new ChainedTransformation(t, t2).delay(i++ * 2);

				ar.addAnimation(new Animation(f, chain));
			}
		}
	}

	public void animate(boolean reload)
	{
		if (reload)
			loadAnimations();

		ar.setStartTime();
	}

	@Override
	public void render()
	{
		//initialize();
		bindTexture(font.getResourceLocation());
		enableBlending();

		rp.colorMultiplier.set(color);

		if (shapeString != null)
			shapeString.resetState();

		ar.animate();
		rp.colorMultiplier.set(0x9999FF);

		drawShape(shapeString, rp);

		//Shape s = font.getShape("" + font.getStringWidth("Custom font with cool animations!"), .5F);
		//		Shape s = font.getShape("à", 1);
		//		s.translate(-2, 3, -20);
		//		s.rotate(-90, 1, 0, 0);
		//
		//		drawShape(s, rp);

		next();

		FontRenderOptions fro = new FontRenderOptions();
		fro.color = 0x339966;
		float fx = -11;
		float fy = 3;
		float fz = 0;
		fro.fontScale = 0.25f;
		fro.shadow = true;
		//fro.italic = true;

		String str = EnumChatFormatting.ITALIC + "Testi" + EnumChatFormatting.GOLD + EnumChatFormatting.ITALIC + "ng another "
				+ EnumChatFormatting.RED + EnumChatFormatting.ITALIC + " color" + EnumChatFormatting.RESET + " and reset";
		//font.render(this, str, fx, fy, fz, fro);

		//MalisisFont.minecraftFont.render(this, str, fx, fy + .5F, fz, fro);

		//		float s = fro.fontScale / 9f;
		//		GL11.glPushMatrix();
		//		GL11.glTranslated(fx, fy, fz);
		//		GL11.glScalef(s, -s, s);
		//		Minecraft.getMinecraft().fontRendererObj.drawString(str, 0, 0, fro.color, true);
		//		GL11.glPopMatrix();
	}
}
