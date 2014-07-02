package net.malisis.demo.minty;

import java.util.List;

import net.malisis.core.renderer.IBaseRendering;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmoryOre extends Block implements IBaseRendering
{
	public static final String ORE_BLOCK_NAME = "ArmoryOre";
	public static final String OBSIDIUM_BLOCK_NAME = "obsidium_block";
	public static final String AZURITE_BLOCK_NAME = "azurite_block";
	public static final String CRIMSONITE_BLOCK_NAME = "crimsonite_block";
	public static final String SMITHING_ANVIL_NAME = "smithing_anvil";
	public static final String SMITHING_FURNACE_NAME = "smithing_furnace";

	public int renderId = -1;
	public IIcon[] overlays = new IIcon[4];
	public String[] iconNames = { "Lava_Overlay", "Azurite_Overlay", "Crimsonite_Overlay", "Titanium_Overlay" };
	public int[] colors = { 0xFFFFFF, 0x123456, 0xFF0000, 0xFFFFFF };
	public int[] brightness = { 200, 225, 150, 0 };

	public ArmoryOre()
	{
		super(Material.rock);
		this.setBlockName(ORE_BLOCK_NAME);
		this.setHardness(1f);
		this.setResistance(3f);
		this.setCreativeTab(MalisisDemos.tabDemos);
		this.setStepSound(Block.soundTypeGravel);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(MalisisDemos.modid + ":" + getUnlocalizedName().substring(5) + "_Ore_Glitter");
		for (int i = 0; i < iconNames.length; i++)
			overlays[i] = iconRegister.registerIcon(MalisisDemos.modid + ":" + getUnlocalizedName().substring(5) + "_" + iconNames[i]);
	}

	public IIcon getOverlayIcon(int side, int meta)
	{
		return overlays[meta];
	}

	public int colorMultiplier(int meta)
	{
		return colors[meta];
	}

	public int getOreBrightness(int meta)
	{
		return brightness[meta];
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return renderId;
	}

	@Override
	public void setRenderId(int id)
	{
		renderId = id;
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