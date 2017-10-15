/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Ordinastie
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

package net.malisis.demo.door;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import net.malisis.core.util.modmessage.ModMessageManager;
import net.malisis.demo.IDemo;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemDoor;

/**
 * @author Ordinastie
 *
 */
public class DoorDemo implements IDemo
{
	public BlockDoor door;
	public ItemDoor itemDoor;

	@Override
	public void preInit()
	{
		Map<String, Object> map = Maps.newHashMap();
		map.put("name", "doorCompatTest");
		map.put("modid", MalisisDemos.modid);
		map.put("textureName", "laboratory_door");
		map.put("hardness", 1.0F);
		map.put("soundType", SoundType.CLOTH);
		map.put("openingTime", 10);
		map.put("movement", "rotate_place");
		map.put("sound", "iron_door");
		map.put("tab", MalisisDemos.tabDemos);

		Pair<BlockDoor, ItemDoor> pair = ModMessageManager.message("malisisdoors", "createDoor", map);
		door = pair.getLeft();
		itemDoor = pair.getRight();
	}

	@Override
	public void init()
	{}
}
