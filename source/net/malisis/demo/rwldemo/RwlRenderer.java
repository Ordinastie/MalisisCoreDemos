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

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.Point;
import net.malisis.core.util.Ray;
import net.malisis.core.util.RaytraceWorld;
import net.malisis.core.util.Vector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

/**
 * @author Ordinastie
 *
 */
public class RwlRenderer extends MalisisRenderer
{
	private int mode = 0;
	private Point start;
	private Point end;
	private Point hit;
	private RaytraceWorld rayTrace;

	public void changeMode()
	{
		mode = (mode + 1) % 2;
		if (mode == 1)
			setLine();
	}

	public void setLine()
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;

		Vec3 look = player.getLook(partialTick);
		Vec3 pos = player.getPosition(partialTick);
		start = new Point(pos.xCoord, pos.yCoord, pos.zCoord);
		Ray ray = new Ray(start, new Vector(look));
		rayTrace = new RaytraceWorld(ray);
		rayTrace.setLength(10);
		end = rayTrace.getDestination();

		mop = rayTrace.trace();
		if (mop.typeOfHit == MovingObjectType.BLOCK)
			hit = new Point(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		else
			hit = null;

	}

	@Override
	public boolean shouldRender(RenderWorldLastEvent event, IBlockAccess world)
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		return player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == RWLDemo.pointer;
	}

	@Override
	public void render()
	{
		if (mode == 0)
			renderBlockHighlight();
		else if (mode == 1)
			renderRayTrace();
	}

	private void renderBlockHighlight()
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);

		set(mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ);
		set(mop.blockX, mop.blockY, mop.blockZ);
		//set(-1, 3, 6);

		int color = 0x000066;
		GL11.glTranslatef(x, y, z);

		next(GL11.GL_LINE_LOOP);
		disableTextures();

		rp.colorMultiplier.set(color);

		super.render();

		next(GL11.GL_QUADS);
		enableTextures();
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		ForgeDirection facing = EntityUtils.getEntityFacing(player);
		float a = 0;
		String[] texts = { x + "," + y + "," + z, facing + " / " + a, "" + player.rotationYaw, "Chunk " + (x >> 4) + ", " + (z >> 4) };
		int ox = 0;
		int oy = 1;
		int oz = 0;
		//	color = 0xAAAAFF;
		if (facing == ForgeDirection.NORTH)
		{
			oz = 1;
		}
		else if (facing == ForgeDirection.EAST)
		{
			a = 90;
		}
		else if (facing == ForgeDirection.SOUTH)
		{
			a = 180;
			ox = -1;
		}
		else if (facing == ForgeDirection.WEST)
		{
			a = 270;
			ox = -1;
			oz = 1;
		}

		GL11.glRotatef(-a, 0, 1, 0);

		if (dir == ForgeDirection.UP)
		{
			GL11.glRotatef(-90, 1, 0, 0);
			oz = 1;
			if (facing == ForgeDirection.NORTH)
			{
				ox = 0;
				oy = 0;
			}
			if (facing == ForgeDirection.WEST)
			{
				oy = 0;
			}
		}

		int i = 0;
		for (String str : texts)
		{
			int offset = getStringWidth(str);
			float x = ox - offset * 0.01F;
			drawString(str, x, oy - i * 0.1F, oz, color, false);
			i++;
		}

		next();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void renderRayTrace()
	{

		next(GL11.GL_LINES);
		disableTextures();
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		t.setColorRGBA_I(0x339933, 0xFF);
		t.addVertex(start.x, start.y, start.z);
		if (hit != null)
		{
			t.addVertex(hit.x, hit.y, hit.z);
			t.setColorRGBA_I(0xAA3333, 0xFF);
			t.addVertex(hit.x, hit.y, hit.z);
		}
		t.addVertex(end.x, end.y, end.z);

		next(GL11.GL_QUADS);
		enableTextures();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
