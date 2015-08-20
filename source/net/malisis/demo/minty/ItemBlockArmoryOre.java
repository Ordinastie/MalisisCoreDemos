package net.malisis.demo.minty;

import net.malisis.demo.minty.ArmoryOre.OreType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockArmoryOre extends ItemBlock
{
	public static final String[] oreTypes = { "Obsidium", "Azurite", "Crimsonite", "Titanium" };

	public ItemBlockArmoryOre(Block block)
	{
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return oreTypes[getMetadata(itemStack)] + " Ore";
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}

	public OreType getOreType(ItemStack itemStack)
	{
		return OreType.values()[getMetadata(itemStack)];
	}
}
