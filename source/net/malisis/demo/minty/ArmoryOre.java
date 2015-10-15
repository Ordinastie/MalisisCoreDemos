package net.malisis.demo.minty;

import java.util.List;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.util.IMSerializable;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmoryOre extends MalisisBlock
{
	//Create an enum for the different ore types available
	//oreTypes hold their respective infos
	public static enum OreType implements IMSerializable
	{
		Lava(0xFFFFFF, 200), Azurite(0x123456, 225), Crimsonite(0xFF0000, 150), Titanium(0xFFFFFF, 0);

		int color;
		int brightness;

		private OreType(int color, int brightness)
		{
			this.color = color;
			this.brightness = brightness;
		}
	}

	//create the property from the enum
	public static PropertyEnum ORE_TYPE = PropertyEnum.create("oreType", OreType.class);

	public ArmoryOre()
	{
		//set the usual properties
		super(Material.rock);
		setName("ArmoryOre");
		setHardness(1f);
		setResistance(3f);
		setCreativeTab(MalisisDemos.tabDemos);
		setStepSound(Block.soundTypeGravel);

		if (MalisisCore.isClient())
			setIconProvider(new ArmoryOreIconProvider());
	}

	@Override
	public Class<? extends ItemBlock> getItemClass()
	{
		return ItemBlockArmoryOre.class;
	}

	@Override
	protected BlockState createBlockState()
	{
		//default blockstate has one property
		return new BlockState(this, ORE_TYPE);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		//meta -> state conversion : we use the enum value ordinal
		return ((OreType) state.getValue(ORE_TYPE)).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		//state -> meta conversion : we use the enum value ordinal
		return getDefaultState().withProperty(ORE_TYPE, OreType.values()[meta]);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean canRenderInLayer(EnumWorldBlockLayer layer)
	{
		//the block has 2 layers : the overlay has transparent parts to it needs to be drawn on the CUTOUT layer
		return layer == EnumWorldBlockLayer.SOLID || layer == EnumWorldBlockLayer.CUTOUT;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 4; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MalisisRenderer getRenderer()
	{
		return new MintyOreRenderer();
	}

}