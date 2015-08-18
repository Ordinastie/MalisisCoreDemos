package net.malisis.demo.guidemo;

import net.malisis.demo.IDemo;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This demo shows how to use a GUI to display the block inventory as well as how to use most components available in MalisisCore.
 */
public class GuiDemo implements IDemo
{
	public static GuiBlock guiBlock;

	@Override
	public void preInit()
	{
		//create the block
		guiBlock = new GuiBlock();
		//register the block
		guiBlock.register();

		//register the tile entity associated
		GameRegistry.registerTileEntity(GuiTileEntity.class, "guiTileEntity");
	}

	@Override
	public void init()
	{}

}
