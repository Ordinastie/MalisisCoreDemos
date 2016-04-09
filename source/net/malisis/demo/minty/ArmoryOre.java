package net.malisis.demo.minty;

import java.util.List;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.renderer.MalisisRendered;
import net.malisis.core.util.IMSerializable;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@MalisisRendered(MintyOreRenderer.class)
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
	public static PropertyEnum<OreType> ORE_TYPE = PropertyEnum.create("oretype", OreType.class);

	public ArmoryOre()
	{
		//set the usual properties
		super(Material.rock);
		setName("ArmoryOre");
		setHardness(1f);
		setResistance(3f);
		setCreativeTab(MalisisDemos.tabDemos);
		setSoundType(SoundType.GROUND);

		if (MalisisCore.isClient())
			addComponent(ArmoryOreIconProvider.get());
	}

	@Override
	public Item getItem(Block block)
	{
		return new ItemBlockArmoryOre(this);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		//default blockstate has one property
		return new BlockStateContainer(this, ORE_TYPE);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		//meta -> state conversion : we use the enum value ordinal
		return state.getValue(ORE_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		//state -> meta conversion : we use the enum value ordinal
		return getDefaultState().withProperty(ORE_TYPE, OreType.values()[meta]);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer)
	{
		//the block has 2 layers : the overlay has transparent parts to it needs to be drawn on the CUTOUT layer
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
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