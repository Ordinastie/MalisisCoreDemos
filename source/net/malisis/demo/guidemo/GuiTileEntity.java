package net.malisis.demo.guidemo;

import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.inventory.IInventoryProvider.IDirectInventoryProvider;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiTileEntity extends TileEntity implements IDirectInventoryProvider
{
	private MalisisInventory inventory;

	public GuiTileEntity()
	{
		//create the inventory : 5 slots holding a maximum of 5 items
		inventory = new MalisisInventory(this, 5);
		inventory.setInventoryStackLimit(5);
	}

	@Override
	public MalisisInventory getInventory()
	{
		return inventory;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public MalisisGui getGui(MalisisInventoryContainer container)
	{
		//opens the GUI for this TE.
		return new Gui(container);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		inventory.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		inventory.writeToNBT(tagCompound);
	}

	@Override
	public Packet<INetHandlerPlayClient> getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}
}
