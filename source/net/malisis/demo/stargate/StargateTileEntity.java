package net.malisis.demo.stargate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class StargateTileEntity extends TileEntity
{
	//start time for animations
	public long placedTimer;
	public AxisAlignedBB aabb = new AxisAlignedBB(-2, 0, -2, 3, 2, 3);

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		//extends the rendering bound so the TE is rendered even when not looking directly at the base block
		return aabb.offset(pos.getX(), pos.getY(), pos.getZ());
	}

}
