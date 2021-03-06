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
import net.malisis.core.renderer.font.FontOptions;
import net.malisis.core.renderer.font.MalisisFont;

/**
 * @author Ordinastie
 *
 */
public class SyncTeRenderer extends MalisisRenderer<SyncTileEntity>
{
	@Override
	public void render()
	{
		//make it so the text always face the player
		setBillboard(0.5F, 1.2F, 0.5F);

		//get the values from the TE
		String label = tileEntity.label;
		int counter = tileEntity.counter;
		int color = tileEntity.color;

		//display the text with updated values on the client
		String str = label + " : " + counter + " (#" + Integer.toHexString(color) + ")";

		//reduce the size of the text and set the color
		FontOptions fontOptions = FontOptions.builder().scale(0.25F).color(color).build();

		//make it centered on the block
		float width = MalisisFont.minecraftFont.getStringWidth(str, fontOptions);
		MalisisFont.minecraftFont.render(this, str, -width / 18F, 0, 0, fontOptions);

		//clear billboarding
		endBillboard();
	}
}
