package net.malisis.demo.minty;

import net.malisis.demo.IDemo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Demo based on Minty's Armory mod (https://github.com/MintyMint/Armory).<br>
 * This demo details how to render blocks with overlays and custom brightness.
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
		//instantiate new block
		ore = new ArmoryOre();
		//register the block with an item with sub types
		ore.register(ItemBlockArmoryOre.class);
	}

	@Override
	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			//create a new renderer and register the block class to be used for it.
			//hide behind a side check because we're not using a proxy
			new MintyOreRenderer().registerFor(ore);
		}
	}
}
