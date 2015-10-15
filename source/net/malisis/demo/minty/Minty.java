package net.malisis.demo.minty;

import net.malisis.demo.IDemo;

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
		ore.register();
	}

	@Override
	public void init()
	{}
}
