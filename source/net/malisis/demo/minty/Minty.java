package net.malisis.demo.minty;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class Minty
{
	ArmoryOre ore;

	public void preInit()
	{
		ore = new ArmoryOre();
		GameRegistry.registerBlock(ore, ItemBlockArmoryOre.class, ore.getUnlocalizedName().substring(5));
	}

	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			new MintyOreRenderer().registerFor(ArmoryOre.class);
		}
	}
}
