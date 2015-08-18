package net.malisis.demo.guidemo;

import net.malisis.core.block.MalisisBlock;
import net.malisis.core.inventory.IInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.util.TileEntityUtils;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class GuiBlock extends MalisisBlock implements ITileEntityProvider
{
	public GuiBlock()
	{
		super(Material.ground);
		setCreativeTab(MalisisDemos.tabDemos);
		setLightLevel(0.9375F);
		setUnlocalizedName("guiDemo");
		setTextureName(MalisisDemos.modid + ":blocks/guidemo");
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, net.minecraft.util.EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//no action done on the client
		//because the TE has an inventory, it needs to be opened on the server, which then automatically opens the GUI given by the TE on the client
		if (world.isRemote)
			return true;

		IInventoryProvider te = TileEntityUtils.getTileEntity(IInventoryProvider.class, world, pos);
		MalisisInventory.open((EntityPlayerMP) player, te);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new GuiTileEntity();
	}
}
