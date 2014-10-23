package net.malisis.demo.minty;

import java.util.List;

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

public class ArmoryOre extends Block
{
	//this static int will be set to the right value when regsiter the class for the renderer
	public static int renderId = -1;
	//the different overlays available
	public IIcon[] overlays = new IIcon[4];
	//icon names for the overlays
	public String[] iconNames = { "Lava_Overlay", "Azurite_Overlay", "Crimsonite_Overlay", "Titanium_Overlay" };
	//colors to be used in rendering based on metadata
	public int[] colors = { 0xFFFFFF, 0x123456, 0xFF0000, 0xFFFFFF };
	//brightness to be used in rendering based on metadata
	public int[] brightness = { 200, 225, 150, 0 };

	public ArmoryOre()
	{
		//set the usual properties
		super(Material.rock);
		this.setBlockName("ArmoryOre");
		this.setHardness(1f);
		this.setResistance(3f);
		this.setCreativeTab(MalisisDemos.tabDemos);
		this.setStepSound(Block.soundTypeGravel);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		//register a base icon for the block. It will be used under the overlay and will be used with the right color and brightness
		blockIcon = iconRegister.registerIcon(MalisisDemos.modid + ":ArmoryOre_Ore_Glitter");
		//register the overlays
		for (int i = 0; i < iconNames.length; i++)
			overlays[i] = iconRegister.registerIcon(MalisisDemos.modid + ":ArmoryOre_" + iconNames[i]);
	}

	public IIcon getOverlayIcon(int side, int meta)
	{
		//get the overlay based on metadata
		return overlays[meta];
	}

	public int colorMultiplier(int meta)
	{
		//get the color based on metadata
		return colors[meta];
	}

	public int getOreBrightness(int meta)
	{
		//get the brightness based on metadata
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