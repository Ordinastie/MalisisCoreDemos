package net.malisis.demo.stargate;

import net.malisis.core.renderer.BaseRenderer;
import net.malisis.core.renderer.IBaseRendering;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
			StargateRenderer r = BaseRenderer.create(StargateRenderer.class, (IBaseRendering) sgBlock);
			RenderingRegistry.registerBlockHandler(r);
			ClientRegistry.bindTileEntitySpecialRenderer(StargateTileEntity.class, r);
		}
	}
}
