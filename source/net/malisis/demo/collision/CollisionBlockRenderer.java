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

package net.malisis.demo.collision;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.RenderType;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.shape.Cube;
import net.malisis.core.renderer.icon.VanillaIcon;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Ordinastie
 *
 */
public class CollisionBlockRenderer extends MalisisRenderer
{
	private Shape shape = new Cube();
	private RenderParameters rp = new RenderParameters();

	@Override
	public void render()
	{
		//reset the shape
		shape.resetState();

		//set params
		rp.renderAllFaces.set(true);
		rp.interpolateUV.set(true);
		rp.alpha.set(255);

		//make sure icon is not set for the center cube (or item)
		rp.icon.reset();

		//draw regular cube
		drawShape(shape, rp);

		//we don't want the stairs for the item
		if (renderType == RenderType.ITEM)
			return;

		//get the AABBS for rendering
		AxisAlignedBB[] aabbs = ((MalisisBlock) block).getRenderBoundingBox(world, pos, blockState);

		//make the stairs translucent
		rp.alpha.set(150);
		rp.interpolateUV.set(false);
		//use the planks icon
		rp.icon.set(new VanillaIcon(Blocks.planks));

		//draw each aabb making the stairs except the first one
		for (int i = 1; i < aabbs.length; i++)
		{
			shape.resetState();
			shape.setBounds(aabbs[i]);
			drawShape(shape, rp);
		}
	}
}
