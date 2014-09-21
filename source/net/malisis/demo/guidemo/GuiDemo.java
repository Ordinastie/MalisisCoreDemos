package net.malisis.demo.guidemo;

import net.malisis.demo.IDemo;
import cpw.mods.fml.common.registry.GameRegistry;

public class GuiDemo implements IDemo
{
	GuiBlock guiBlock;

	@Override
	public void preInit()
	{
		(guiBlock = new GuiBlock()).setBlockName("guiDemo");
		GameRegistry.registerBlock(guiBlock, guiBlock.getUnlocalizedName().substring(5));

		GameRegistry.registerTileEntity(GuiTileEntity.class, "guiTileEntity");
	}

	@Override
	public void init()
	{}

}
