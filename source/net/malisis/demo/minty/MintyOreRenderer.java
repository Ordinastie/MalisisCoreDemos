package net.malisis.demo.minty;

import net.malisis.core.renderer.BaseRenderer;
import net.minecraft.init.Blocks;

public class MintyOreRenderer extends BaseRenderer
{
	@Override
	public void render()
	{
		//when reaching this method, everything is already set up for rendering :
		//ISBRH already has the tessellator translated, TESR has the openGl states set up etc..
		//this.block holds the block instance, this.x/y/z the coordinates, this.titeEntity

		// {"Obsidium/Lava", "Azurite", "Crimsonite", "Titanium"}
		ArmoryOre block = (ArmoryOre) this.block;

		//Note : rp is RenderParameters and hold all the parameters available to tweak the rendering

		//special case, if metadata == 0, use the lava texture
		if (blockMetadata == 0)
			rp.icon.set(Blocks.lava.getIcon(0, 0));

		//titanium doesn't have its own brightness
		if (blockMetadata != 3)
		{
			//do not automatically calculate brightness for the block
			rp.calculateBrightness.set(false);
			//use this brightness instead
			rp.brightness.set(block.getOreBrightness(blockMetadata));
		}

		//apply a general color for the block
		rp.colorMultiplier.set(block.colorMultiplier(blockMetadata));
		//draw first layer : shape is preset to be a simple cube.
		drawShape(shape, rp);

		//reset the state to its original state because rendering may alter the shape
		shape.resetState();
		//reset parameters to their defaults so the brightness of this layer will be calculated
		rp.reset();
		//set the overlay icon
		rp.icon.set(block.getOverlayIcon(0, blockMetadata));
		//draw second layer
		drawShape(shape, rp);

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		//render() will be called for inventory drawing too
		return true;
	}
}
