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

package net.malisis.demo.multiblock;

import net.malisis.core.MalisisCore;
import net.malisis.core.block.MalisisBlock;
import net.malisis.core.util.multiblock.AABBMultiBlock;
import net.malisis.core.util.multiblock.MultiBlockComponent;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author Ordinastie
 *
 */
public class AABBBlock extends MalisisBlock
{
	public AABBBlock()
	{
		//sets the usual properties
		super(Material.wood);
		setHardness(1.0F);
		setSoundType(SoundType.WOOD);
		setName("aabbMultiBlockDemo");
		setCreativeTab(MalisisDemos.tabDemos);

		//create the multiblock with the AABB
		AABBMultiBlock multiBlock = new AABBMultiBlock(this, new AxisAlignedBB(-1, 0, 0, 3, 4, 1));
		multiBlock.setBulkProcess(true, true);

		//add the component
		addComponent(new MultiBlockComponent(multiBlock));

		if (MalisisCore.isClient())
		{
			//			MegaTextureIconProvider ip = new MegaTextureIconProvider(new VanillaIcon(Blocks.obsidian));
			//			ip.setMegaTexture(EnumFacing.SOUTH, new MalisisIcon(MalisisDemos.modid + ":blocks/mb"), 4);
			//			addComponent(ip);
		}
	}
}
