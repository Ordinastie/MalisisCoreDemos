package net.malisis.demo.stargate;

import net.malisis.demo.IDemo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

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
		//create the block
		sgBlock = new StargateBlock();
		//register the block
		GameRegistry.registerBlock(sgBlock, sgBlock.getUnlocalizedName().substring(5));
		//registert the TileEntity
		GameRegistry.registerTileEntity(StargateTileEntity.class, "stargateTileEntity");
	}

	@Override
	public void init()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			//use the renderer for both the block and the TileEntity
			new StargateRenderer().registerFor(StargateBlock.class, StargateTileEntity.class);
		}
	}
}
