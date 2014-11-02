package net.malisis.demo.guidemo;

import net.malisis.core.inventory.IInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiBlock extends Block implements ITileEntityProvider
{
	public GuiBlock()
	{
		super(Material.ground);
		setCreativeTab(MalisisDemos.tabDemos);
		setLightLevel(0.9375F);
		setBlockName("guiDemo");
		setBlockTextureName(MalisisDemos.modid + ":guidemo");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return true;

		IInventoryProvider te = TileEntityUtils.getTileEntity(IInventoryProvider.class, world, x, y, z);
		MalisisInventory.open((EntityPlayerMP) player, te);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new GuiTileEntity();
	}
}
