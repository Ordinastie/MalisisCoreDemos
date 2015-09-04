package net.malisis.demo.minty;

import java.util.List;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmoryOre extends MalisisBlock
{
	//Create an enum for the different ore types available
	//oreTypes hold their respective infos
	enum OreType implements IStringSerializable
	{
		Lava(0xFFFFFF, 200), Azurite(0x123456, 225), Crimsonite(0xFF0000, 150), Titanium(0xFFFFFF, 0);

		int color;
		int brightness;

		private OreType(int color, int brightness)
		{
			this.color = color;
			this.brightness = brightness;
		}

		@Override
		public String getName()
		{
			return name();
		}
	}

	//create the property from the enum
	public static IProperty oreTypeProperty = PropertyEnum.create("oreType", OreType.class);

	public ArmoryOre()
	{
		//set the usual properties
		super(Material.rock);
		setUnlocalizedName("ArmoryOre");
		setHardness(1f);
		setResistance(3f);
		setCreativeTab(MalisisDemos.tabDemos);
		setStepSound(Block.soundTypeGravel);

		if (MalisisCore.isClient())
			setBlockIconProvider(new ArmoryOreIconProvider());

	}

	@Override
	protected BlockState createBlockState()
	{
		//default blockstate has one property
		return new BlockState(this, oreTypeProperty);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		//meta -> state conversion : we use the enum value ordinal
		return ((OreType) state.getValue(oreTypeProperty)).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		//state -> meta conversion : we use the enum value ordinal
		return getDefaultState().withProperty(oreTypeProperty, OreType.values()[meta]);
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
}