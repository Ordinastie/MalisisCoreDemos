package net.malisis.demo.guidemo;

import net.malisis.demo.IDemo;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GuiDemo implements IDemo
{
	public static GuiBlock guiBlock;

	@Override
	public void preInit()
	{
		guiBlock = new GuiBlock();
		guiBlock.register();

		GameRegistry.registerTileEntity(GuiTileEntity.class, "guiTileEntity");
	}

	@Override
	public void init()
	{}

}
