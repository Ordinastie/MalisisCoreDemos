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

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.CornerComponent;
import net.malisis.core.block.component.SlopeComponent;
import net.malisis.core.block.component.SlopedCornerComponent;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

/**
 * @author Ordinastie
 *
 */
public class BlockShape extends MalisisBlock
{
	public BlockShape(String type)
	{
		//set usual properties
		super(Material.GROUND);
		setCreativeTab(MalisisDemos.tabDemos);
		setName(type + "ShapeDemo");
		setUnlocalizedName("blockShapeDemo");
		setTexture(Blocks.LAPIS_BLOCK);

		switch (type)
		{
			case "slope":
				addComponent(new SlopeComponent());
				break;
			case "corner":
				addComponent(new CornerComponent());
				break;
			case "slopedCorner":
				addComponent(new SlopedCornerComponent());
				break;
		}

	}
}
