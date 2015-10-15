package net.malisis.demo.stargate;

import net.malisis.demo.IDemo;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This demo is designed to show how complex animations can be handled when rendering.
 *
 * @author Ordinastie
 *
 */
public class Stargate implements IDemo
{
	public static StargateBlock sgBlock;

	@Override
	public void preInit()
	{
		//create and register the block
		sgBlock = new StargateBlock();
		sgBlock.register();

		//register the TileEntity
		GameRegistry.registerTileEntity(StargateTileEntity.class, "stargateTileEntity");
	}

	@Override
	public void init()
	{}
}
