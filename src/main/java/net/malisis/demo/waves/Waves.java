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

package net.malisis.demo.waves;

import net.malisis.demo.IDemo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * This demo is an example for animations.
 *
 * @author Ordinastie
 *
 */
public class Waves implements IDemo
{
	WaveBlock waveBlock;

	@Override
	public void preInit()
	{
		//create the block
		waveBlock = new WaveBlock();

		//register the block
		GameRegistry.registerBlock(waveBlock, waveBlock.getUnlocalizedName().substring(5));
		//register the TileEntity
		GameRegistry.registerTileEntity(WaveTileEntity.class, "waveTE");
	}

	@Override
	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			//use the renderer for both the block and the TE
			new WaveRenderer().registerFor(WaveBlock.class, WaveTileEntity.class);
		}
	}

}
