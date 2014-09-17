package net.malisis.demo.stargate;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class Stargate
{
	public StargateBlock sgBlock;

	public void preInit()
	{
		sgBlock = new StargateBlock();
		GameRegistry.registerBlock(sgBlock, sgBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerTileEntity(StargateTileEntity.class, "stargateTileEntity");
	}

	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			new StargateRenderer().registerFor(StargateBlock.class, StargateTileEntity.class);
		}
	}
}
