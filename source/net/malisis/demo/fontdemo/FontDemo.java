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

import net.malisis.core.MalisisCore;
import net.malisis.demo.IDemo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This demo show how to use a custom font and render a text in the world.<br>
 * There are two ways to render text :<br>
 * - direct rendering in world <br>
 * - create a shape from the text <br>
 * In this example the shape will be animated to demonstrate how it's possible to interact with the shape.
 *
 * @author Ordinastie
 *
 */
public class FontDemo implements IDemo
{
	public static FontPointer fontPointer;
	@SideOnly(Side.CLIENT)
	public static FontDemoRenderer renderer;

	@Override
	public void preInit()
	{
		//create the item and register
		fontPointer = new FontPointer();
		fontPointer.register();
	}

	@Override
	public void init()
	{
		if (MalisisCore.isClient())
		{
			//create the renderer and register it for RenderWorldLastEvent
			//we keep a reference to the renderer for easy direct access from the item
			renderer = new FontDemoRenderer();
			renderer.registerForRenderWorldLast();
		}
	}
}
