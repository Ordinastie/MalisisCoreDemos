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

import net.malisis.core.util.syncer.Sync;
import net.malisis.core.util.syncer.Syncable;
import net.malisis.core.util.syncer.Syncer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Ordinastie
 *
 */
@Syncable("TileEntity")
public class SyncTileEntity extends TileEntity
{
	@Sync("counter")
	public int counter = 0;
	@Sync("label")
	public String label = "Counter";
	@Sync("color")
	public int color = 0;

	private int labelIndex = 0;
	private String[] labels = new String[] { "Counter", "Label", "Value", "Amount", "Metadata" };

	public void activate()
	{
		labelIndex++;
		if (labelIndex >= labels.length)
			labelIndex = 0;
		label = labels[labelIndex];

		color = worldObj.rand.nextInt(0xFFFFFF);
		Syncer.sync(this, "label", "color");
	}

	public int getCounter()
	{
		return counter;
	}

	@Override
	public void updateEntity()
	{
		if (worldObj.isRemote)
			return;
		if ((worldObj.getTotalWorldTime() % 20) != 0)
			return;

		counter++;
		Syncer.sync(this, "counter");
	}
}
