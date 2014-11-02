package net.malisis.demo.stargate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class StargateTileEntity extends TileEntity
{
	//start time for animations
	public long placedTimer;

	@Override
	public boolean canUpdate()
	{
		//no need to for update
		return false;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		//extends the rendering bound so the TE is rendered even when not looking directly at the base block
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 2, zCoord + 3);
	}

}
