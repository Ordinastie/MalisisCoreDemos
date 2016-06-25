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

import java.util.ArrayList;

import net.malisis.core.IMalisisMod;
import net.malisis.core.MalisisCore;
import net.malisis.core.configuration.Settings;
import net.malisis.core.network.MalisisNetwork;
import net.malisis.demo.blocklistener.BlockListenerDemo;
import net.malisis.demo.collision.Collision;
import net.malisis.demo.components.ComponentsDemo;
import net.malisis.demo.connected.Connected;
import net.malisis.demo.fontdemo.FontDemo;
import net.malisis.demo.guidemo.GuiDemo;
import net.malisis.demo.lavapool.LavaPoolDemo;
import net.malisis.demo.minty.Minty;
import net.malisis.demo.model.ModelDemo;
import net.malisis.demo.multiblock.MultiBlockDemo;
import net.malisis.demo.multipleinv.MultipleInv;
import net.malisis.demo.rwldemo.RWLDemo;
import net.malisis.demo.sidedinvdemo.SidedInvDemo;
import net.malisis.demo.stargate.Stargate;
import net.malisis.demo.syncdemo.SyncDemo;
import net.malisis.demo.tabinv.TabInv;
import net.malisis.demo.waves.Waves;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
@Mod(modid = MalisisDemos.modid, name = MalisisDemos.modname, version = MalisisDemos.version, dependencies = "required-after:malisiscore")
public class MalisisDemos implements IMalisisMod
{
	public static final String modid = "malisisdemos";
	public static final String modname = "MalisisCore Demos";
	public static final String version = "1.7.2-0.1";

	public static MalisisDemos instance;
	public static MalisisNetwork network;
	public static ArrayList<IDemo> demos = new ArrayList<>();

	public static CreativeTabs tabDemos = new CreativeTabs(modid)
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(GuiDemo.guiBlock);
		}
	};

	public MalisisDemos()
	{
		instance = this;
		network = new MalisisNetwork(instance);
		MalisisCore.registerMod(this);

		demos.add(new Stargate());
		demos.add(new ComponentsDemo());
		demos.add(new GuiDemo());
		demos.add(new Minty());
		demos.add(new ModelDemo());
		demos.add(new Connected());
		demos.add(new MultipleInv());
		demos.add(new SidedInvDemo());
		demos.add(new MultiBlockDemo());
		demos.add(new TabInv());
		demos.add(new Waves());
		demos.add(new RWLDemo());
		demos.add(new Collision());
		demos.add(new BlockListenerDemo());
		demos.add(new FontDemo());
		demos.add(new LavaPoolDemo());
		demos.add(new SyncDemo());

		//demos.add(new TestDemo());
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
	public void preInit(FMLPreInitializationEvent event)
	{
		for (IDemo demo : demos)
			demo.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		for (IDemo demo : demos)
			demo.init();
	}
}
