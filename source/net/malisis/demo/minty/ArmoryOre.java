package net.malisis.demo.minty;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.block.component.SubtypeComponent;
import net.malisis.core.renderer.MalisisRendered;
import net.malisis.core.util.IMSerializable;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

@MalisisRendered(MintyOreRenderer.class)
public class ArmoryOre extends MalisisBlock
{
	//Create an enum for the different ore types available
	//oreTypes hold their respective infos
	public static enum OreType implements IMSerializable
	{
		Obsidium(0xFFFFFF, 200),
		Azurite(0x123456, 225),
		Crimsonite(0xFF0000, 150),
		Titanium(0xFFFFFF, 0);

		int color;
		int brightness;

		private OreType(int color, int brightness)
		{
			this.color = color;
			this.brightness = brightness;
		}
	}

	//create the property from the enum
	public static PropertyEnum<OreType> ORE_TYPE = PropertyEnum.create("oretype", OreType.class);

	public ArmoryOre()
	{
		//set the usual properties
		super(Material.ROCK);
		setName("ArmoryOre");
		setHardness(1f);
		setResistance(3f);
		setCreativeTab(MalisisDemos.tabDemos);
		setSoundType(SoundType.GROUND);

		addComponent(new SubtypeComponent<>(OreType.class));

		if (MalisisCore.isClient())
			addComponent(ArmoryOreIconProvider.get());
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		//the block has 2 layers : the overlay has transparent parts to it needs to be drawn on the CUTOUT layer
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
	}

}