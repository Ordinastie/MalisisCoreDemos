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

import net.malisis.core.renderer.icon.Icon;
import net.malisis.core.renderer.icon.VanillaIcon;
import net.malisis.core.renderer.icon.provider.PropertyEnumIconProvider;
import net.malisis.demo.MalisisDemos;
import net.malisis.demo.minty.ArmoryOre.OreType;
import net.minecraft.init.Blocks;

/**
 * We need a custom IconProvider because we need to know whether to get the base icon or the overlay icon. We have to check the current
 * layer to know whether to use the overlay or not.
 *
 * @author Ordinastie
 *
 */
public class ArmoryOreIconProvider extends PropertyEnumIconProvider<ArmoryOre.OreType>
{
	private VanillaIcon lavaIcon = new VanillaIcon(Blocks.lava);
	private boolean isOverlay;

	public ArmoryOreIconProvider()
	{
		super(ArmoryOre.ORE_TYPE, ArmoryOre.OreType.class, MalisisDemos.modid + ":blocks/ArmoryOre_Ore_Glitter");
		for (OreType oreType : OreType.values())
			setIcon(oreType, new Icon(MalisisDemos.modid + ":blocks/ArmoryOre_" + oreType.name() + "_Overlay"));
	}

	public void setOverlay(boolean isOverlay)
	{
		this.isOverlay = isOverlay;
	}

	@Override
	public Icon getIcon(OreType oreType)
	{
		//if base icon : use current default icon (glitter)
		//special case for Lava where it uses regular lava
		if (!isOverlay)
			return oreType == OreType.Lava ? lavaIcon : getIcon();

		return super.getIcon(oreType);
	}

	public static ArmoryOreIconProvider get()
	{
		return new ArmoryOreIconProvider();

	}
}
