package net.malisis.demo.minty;

import net.malisis.demo.IDemo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Demo based on Minty's Armory mod (https://github.com/MintyMint/Armory).<br>
 * This demo details how to render blocks with overlays and custom lighting.
 *
 * @author Ordinastie
 *
 */
public class Minty implements IDemo
{
	//Block
	ArmoryOre ore;

	@Override
	public void preInit()
	{
		//instanciate new block
		ore = new ArmoryOre();
		//register the block with an item with subtypes
		GameRegistry.registerBlock(ore, ItemBlockArmoryOre.class, ore.getUnlocalizedName().substring(5));
	}

	@Override
	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			//create a new renderer and register the block class to be used for it.
			//hide behind a side check because we're not using a proxy
			new MintyOreRenderer().registerFor(ArmoryOre.class);
		}
	}
}
