package net.malisis.demo.guidemo;

import net.malisis.core.inventory.IInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GuiBlock extends Block implements ITileEntityProvider
{
	public static int renderId = 0;

	public GuiBlock()
	{
		super(Material.ground);
		setCreativeTab(MalisisDemos.tabDemos);
		setLightLevel(0.9375F);
	}

	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return Blocks.lit_pumpkin.getIcon(side, metadata);
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
	public boolean isNormalCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return renderId;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new GuiTileEntity();
	}
}
