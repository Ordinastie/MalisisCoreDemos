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

package net.malisis.demo.rwldemo;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.malisis.core.MalisisCore;
import net.malisis.core.item.MalisisItem;
import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.Point;
import net.malisis.core.util.Ray;
import net.malisis.core.util.Utils;
import net.malisis.core.util.Vector;
import net.malisis.core.util.floodfill.FloodFill;
import net.malisis.core.util.modmessage.ModMessageManager;
import net.malisis.core.util.raytrace.RaytraceWorld;
import net.malisis.demo.MalisisDemos;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Ordinastie
 *
 */
public class RWLPointer extends MalisisItem
{
	public enum Mode
	{
		BLOCKHIGHLIGHT,
		RAYTRACE,
		FLOODFILL,
		SHAPETEST;

		public Mode next()
		{
			return Mode.values()[(ordinal() + 1) % Mode.values().length];
		}
	}

	private Mode currentMode = Mode.BLOCKHIGHLIGHT;
	private Triple<Point, Point, Point> raytraceInfos;
	private FloodFill floodFill;
	private Set<BlockPos> markedBlocks = Sets.newHashSet();
	private Map<Block, Integer> blockColors = Maps.newHashMap();

	public RWLPointer()
	{
		setName("rwl_pointer");
		setCreativeTab(MalisisDemos.tabDemos);
		//use the wooden sword icon
		setTexture(Items.WOODEN_SWORD);

		if (MalisisCore.isClient())
			MinecraftForge.EVENT_BUS.register(this);

		blockColors.put(Blocks.COAL_ORE, 0x333333);
		blockColors.put(Blocks.IRON_ORE, 0x999999);
		blockColors.put(Blocks.GOLD_ORE, 0xAA9933);
		blockColors.put(Blocks.DIAMOND_ORE, 0x3399DD);
		blockColors.put(Blocks.EMERALD_ORE, 0x3399DD);
	}

	public Mode getCurrentMode()
	{
		return currentMode;
	}

	public Triple<Point, Point, Point> getRaytraceInfos()
	{
		return raytraceInfos;
	}

	private void rayTrace()
	{
		//set necessary data for rayTracing
		EntityPlayer player = Utils.getClientPlayer();
		Vec3d look = player.getLook(0);
		Vec3d pos = player.getPositionEyes(0);
		Point start = new Point(pos.xCoord, pos.yCoord, pos.zCoord);

		Ray ray = new Ray(start, new Vector(look));
		RaytraceWorld rayTrace = new RaytraceWorld(Utils.getClientWorld(), ray);
		//limit distance to 10 blocks
		rayTrace.setLength(10);
		Point end = rayTrace.getDestination();

		//do the actual rayTracing
		RayTraceResult result = rayTrace.trace();

		Point hit = null;
		//set the hit point if necessary
		if (result.typeOfHit == RayTraceResult.Type.BLOCK)
			hit = new Point(result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord);

		raytraceInfos = Triple.of(start, end, hit);
	}

	private void floodFill()
	{
		World world = Utils.getClientWorld();
		RayTraceResult result = rayTrace(world, Utils.getClientPlayer(), true);
		if (result == null || result.typeOfHit != Type.BLOCK)
			return;

		IBlockState state = world.getBlockState(result.getBlockPos());
		if (state == Blocks.AIR.getDefaultState())
			return;

		floodFill = FloodFill	.builder(world)
								.from(result.getBlockPos())
								.processIf(this::shouldParse)
								.onProcess(this::process)
								.limitDistance(20)
								.build();

		markedBlocks.clear();
		ModMessageManager.message("mdt", "renderFloodfill", floodFill, 0x660000, 0x66DD66, 0);
	}

	private boolean shouldParse(World world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() != Blocks.AIR;
	}

	private void process(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (blockColors.containsKey(state.getBlock()))
		{
			blockColors.put(Blocks.COAL_ORE, 0x090909);
			markedBlocks.add(pos);
			ModMessageManager.message(	"mdt",
										"markBlocks",
										ImmutableSet.copyOf(markedBlocks),
										(Function<IBlockState, Integer>) this::colorGetter);
		}
	}

	private int colorGetter(IBlockState state)
	{
		return blockColors.getOrDefault(state.getBlock(), -1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		//renderer not accessible server-side
		if (!world.isRemote)
			return new ActionResult<>(EnumActionResult.FAIL, itemStack);

		currentMode = currentMode.next();
		Minecraft.getMinecraft().ingameGUI.setOverlayMessage(currentMode.toString(), false);

		if (currentMode != Mode.FLOODFILL)
		{
			floodFill = null;
			markedBlocks.clear();
		}

		ModMessageManager.message("mdt", "renderFloodfill", floodFill, 0x660000, 0x66DD66, 0);
		ModMessageManager.message("mdt", "markBlocks", markedBlocks, (Function<IBlockState, Integer>) this::colorGetter);

		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
	{
		if (!(entity instanceof EntityPlayer))
			return true;

		switch (currentMode)
		{
			case RAYTRACE:
				rayTrace();
				break;
			case FLOODFILL:
				floodFill();
			case SHAPETEST:
			{
				Set<BlockPos> blocks = ShapeTest.makeEllipse(30, 15);
				ModMessageManager.message("mdt", "markBlocks", blocks, 0xCC3333);
			}
			default:
				break;
		}

		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
	{
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (!world.isRemote || !isSelected || !(entity instanceof EntityPlayer))
			return;

		if (currentMode != Mode.FLOODFILL || floodFill == null)
			return;

		floodFill.process(1000);
		Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Parsed : " + floodFill.getProcessed().size() + " / To parse : "
				+ floodFill.getToProcess().size(), false);
	}

	@SubscribeEvent
	public void blockBreak(BlockEvent.BreakEvent event)
	{
		if (EntityUtils.isEquipped(event.getPlayer(), this, EnumHand.MAIN_HAND))
			event.setCanceled(true);
	}

}
