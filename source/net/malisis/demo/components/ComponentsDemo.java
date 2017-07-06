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

package net.malisis.demo.components;

import net.malisis.demo.IDemo;

/**
 * This demo show how to make a block that can have different directions.
 *
 * @author Ordinastie
 *
 */
public class ComponentsDemo implements IDemo
{
	private BlockDir blockDir;
	private BlockColor blockColor;
	private BlockSlab blockSlab, blockDoubleSlab;
	private BlockWall blockWall;
	private BlockStairs blockStairs;
	private BlockPower blockPower;
	private BlockPane blockPane;
	private BlockShape slopeShape;
	private BlockShape cornerShape;
	private BlockShape slopedCornerShape;
	private BlockPods pods;

	@Override
	public void preInit()
	{
		//create and register the blocks
		blockDir = new BlockDir();
		blockDir.register();

		blockColor = new BlockColor();
		blockColor.register();

		blockSlab = new BlockSlab();
		blockSlab.register();

		blockWall = new BlockWall();
		blockWall.register();

		blockStairs = new BlockStairs();
		blockStairs.register();

		blockPower = new BlockPower();
		blockPower.register();

		blockPane = new BlockPane();
		blockPane.register();

		slopeShape = new BlockShape("slope");
		slopeShape.register();

		cornerShape = new BlockShape("corner");
		cornerShape.register();

		slopedCornerShape = new BlockShape("slopedCorner");
		slopedCornerShape.register();

		pods = new BlockPods();
		pods.register();
	}

	@Override
	public void init()
	{}

}
