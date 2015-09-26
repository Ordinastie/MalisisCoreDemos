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

package net.malisis.demo.rwldemo;

import net.malisis.core.MalisisCore;
import net.malisis.demo.IDemo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This demo shows how to use a renderer for the RenderWorldLastEvent.<br>
 * When holding the pointer item, two modes are available :<br>
 * - informations about the block the player is currently looking at can be displayed.<br>
 * - RayTracing is performed when entering that mode and then displayed in world, showing where it intersects with a block.<br>
 * To demonstrate RayTracing, a stair shaped block is provided too.
 *
 * @author Ordinastie
 *
 */
public class RWLDemo implements IDemo
{
	public static RTBlock rtBlock;
	public static RWLPointer rwlPointer;
	@SideOnly(Side.CLIENT)
	public static RwlRenderer renderer;

	@Override
	public void preInit()
	{
		//create the block and register it
		rtBlock = new RTBlock();
		rtBlock.register();

		//create the item and register it
		rwlPointer = new RWLPointer();
		rwlPointer.register();
	}

	@Override
	public void init()
	{
		if (MalisisCore.isClient())
		{
			//create a new renderer and register it to be used for RenderWorldLastEvent
			renderer = new RwlRenderer();
			renderer.registerForRenderWorldLast();
		}
	}
}
