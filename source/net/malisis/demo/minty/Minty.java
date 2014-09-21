package net.malisis.demo.minty;

import net.malisis.demo.IDemo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class Minty implements IDemo
{
	ArmoryOre ore;

	@Override
	public void preInit()
	{
		ore = new ArmoryOre();
		GameRegistry.registerBlock(ore, ItemBlockArmoryOre.class, ore.getUnlocalizedName().substring(5));
	}

	@Override
	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			new MintyOreRenderer().registerFor(ArmoryOre.class);
		}
	}
}
