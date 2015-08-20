/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Ordinastie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.malisis.demo.minty;

import java.util.EnumMap;

import net.malisis.core.renderer.icon.MalisisIcon;
import net.malisis.core.renderer.icon.provider.DefaultIconProvider;
import net.malisis.demo.MalisisDemos;
import net.malisis.demo.minty.ArmoryOre.OreType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

/**
 * We need a custom IconProvider because we need to know whether to get the base icon or the overlay icon. We have to manually define when
 * to use the overlay or not. It's done in the renderer, by testing the current layer.
 *
 * @author Ordinastie
 *
 */
public class ArmoryOreIconProvider extends DefaultIconProvider
{
	//whether we're currently drawing the overlay
	private boolean isOverlay = false;

	private EnumMap<OreType, MalisisIcon> icons = new EnumMap<>(OreType.class);

	public ArmoryOreIconProvider()
	{
		super(MalisisDemos.modid + ":blocks/ArmoryOre_Ore_Glitter");
	}

	public void setOverlay(boolean isOverlay)
	{
		//sets when to use the base icon or the overlay
		this.isOverlay = isOverlay;
	}

	@Override
	public MalisisIcon getIcon(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing facing)
	{
		//get the oreType
		OreType oreType = (OreType) state.getValue(ArmoryOre.oreTypeProperty);
		//get the right icon
		return getIcon(oreType);
	}

	@Override
	public MalisisIcon getIcon(ItemStack itemStack)
	{
		//get the oreType
		OreType oreType = ((ItemBlockArmoryOre) itemStack.getItem()).getOreType(itemStack);
		//get the right icon
		return getIcon(oreType);
	}

	private MalisisIcon getIcon(OreType oreType)
	{
		//if base icon : use current default icon (glitter)
		//special case for Lava where it uses regular lava
		if (!isOverlay)
			return oreType == OreType.Lava ? MalisisIcon.get("minecraft:blocks/lava_still") : icon;

		//fetch from the map
		return icons.get(oreType);
	}

	@Override
	public void registerIcons(TextureMap map)
	{
		//call super to register the default icon (glitter)
		super.registerIcons(map);

		for (OreType oreType : OreType.values())
		{
			//create the icon from the oreType
			MalisisIcon icon = new MalisisIcon(MalisisDemos.modid + ":blocks/ArmoryOre_" + oreType.name() + "_Overlay");
			//register the icon
			icon.register(map);
			//store in the map
			icons.put(oreType, icon);
		}
	}

}
