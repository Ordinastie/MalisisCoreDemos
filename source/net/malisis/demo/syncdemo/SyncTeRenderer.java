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

package net.malisis.demo.syncdemo;

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.shape.Cube;
import net.malisis.core.renderer.font.FontRenderOptions;
import net.malisis.core.renderer.font.MalisisFont;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Ordinastie
 *
 */
public class SyncTeRenderer extends MalisisRenderer
{
	private Shape cube;

	@Override
	protected void initialize()
	{
		cube = new Cube();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick)
	{
		super.renderTileEntityAt(tileEntity, x, y, z, partialTick);
	}

	@Override
	public void render()
	{
		drawShape(cube);

		FontRenderOptions fro = new FontRenderOptions();
		fro.fontScale = 0.25F;

		String label = ((SyncTileEntity) tileEntity).label;
		int counter = ((SyncTileEntity) tileEntity).getCounter();
		int color = ((SyncTileEntity) tileEntity).color;

		String str = label + " : " + counter + " (" + Integer.toHexString(color) + ")";
		fro.color = color;

		MalisisFont.minecraftFont.render(this, str, 0, 1.2F, 0, fro);
	}
}
