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

package net.malisis.demo;

import net.malisis.core.IMalisisMod;
import net.malisis.core.configuration.Settings;
import net.malisis.demo.connected.Connected;
import net.malisis.demo.guidemo.GuiDemo;
import net.malisis.demo.minty.Minty;
import net.malisis.demo.model.ModelDemo;
import net.malisis.demo.stargate.Stargate;
import net.malisis.demo.test.Test;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 * 
 */
@Mod(modid = MalisisDemos.modid, name = MalisisDemos.modname, version = MalisisDemos.version)
public class MalisisDemos implements IMalisisMod
{
	public static final String modid = "malisisdemos";
	public static final String modname = "MalisisCore Demos";
	public static final String version = "1.7.2-0.1";

	public static MalisisDemos instance;

	public static GuiDemo guiDemo;
	public static Minty minty;
	public static Stargate stargate;
	public static ModelDemo modelDemo;
	public static Test test;
	public static Connected connected;

	public static CreativeTabs tabDemos = new CreativeTabs("MalisisCore Demos")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(stargate.sgBlock);
		}
	};

	public MalisisDemos()
	{
		instance = this;
	}

	//#region IMalisisMod
	@Override
	public String getModId()
	{
		return modid;
	}

	@Override
	public String getName()
	{
		return modname;
	}

	@Override
	public String getVersion()
	{
		return version;
	}

	@Override
	public Settings getSettings()
	{
		return null;
	}

	//#end IMalisisMod

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		guiDemo = new GuiDemo();
		minty = new Minty();
		stargate = new Stargate();
		modelDemo = new ModelDemo();
		test = new Test();
		connected = new Connected();

		guiDemo.preInit();
		minty.preInit();
		stargate.preInit();
		modelDemo.preInit();
		test.preInit();
		connected.preInit();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		guiDemo.init();
		minty.init();
		stargate.init();
		modelDemo.init();
		test.init();
		connected.init();
	}

}
