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

package net.malisis.demo.model;

import net.malisis.demo.IDemo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Ordinastie
 * 
 */
public class ModelDemo implements IDemo
{
	ModelDemoBlock modelBlock;

	@Override
	public void preInit()
	{
		modelBlock = new ModelDemoBlock();
		GameRegistry.registerBlock(modelBlock, modelBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerTileEntity(ModelDemoTileEntity.class, "modelDemoTileEntity");
	}

	@Override
	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			new ModelDemoRenderer().registerFor(ModelDemoBlock.class, ModelDemoTileEntity.class);
		}
	}
}
