package net.malisis.demo.guidemo;

import cpw.mods.fml.common.registry.GameRegistry;

public class GuiDemo
{
	GuiBlock guiBlock;

	public void preInit()
	{
		(guiBlock = new GuiBlock()).setBlockName("guiDemo");
		GameRegistry.registerBlock(guiBlock, guiBlock.getUnlocalizedName().substring(5));

		GameRegistry.registerTileEntity(GuiTileEntity.class, "guiTileEntity");
	}

	public void init()
	{}

}
