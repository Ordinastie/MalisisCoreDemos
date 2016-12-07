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
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.element.Face;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.Vertex;
import net.malisis.core.renderer.element.shape.Cube;
import net.malisis.core.renderer.font.FontOptions;
import net.malisis.core.renderer.font.MalisisFont;
import net.malisis.core.util.EntityUtils;
import net.malisis.core.util.Point;
import net.malisis.core.util.Utils;
import net.malisis.demo.rwldemo.RWLPointer.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.apache.commons.lang3.tuple.Triple;
import org.lwjgl.opengl.GL11;

/**
 * @author Ordinastie
 *
 */
public class RwlRenderer extends MalisisRenderer<TileEntity>
{

	@Override
	public boolean shouldRender(RenderWorldLastEvent event, IBlockAccess world)
	{
		//only render if the pointer is currently equipped with correct mode
		if (!EntityUtils.isEquipped(Utils.getClientPlayer(), RWLDemo.rwlPointer, EnumHand.MAIN_HAND))
			return false;

		Mode mode = RWLDemo.rwlPointer.getCurrentMode();
		//if mode is BLOCKHIGHLIGHT, always render
		if (mode == Mode.BLOCKHIGHLIGHT)
			return true;

		//if mode is RAYTRACE only render if raytrace has been done first
		if (mode == Mode.RAYTRACE)
			return RWLDemo.rwlPointer.getRaytraceInfos() != null;

		//mode is FLOODFILL
		return false;
	}

	@Override
	public void render()
	{
		//render based on current mode
		switch (RWLDemo.rwlPointer.getCurrentMode())
		{
			case BLOCKHIGHLIGHT:
				renderBlockHighlight();
				break;
			case RAYTRACE:
				renderRayTrace();
				break;
			default:
		}
	}

	private void renderBlockHighlight()
	{
		EntityPlayer player = Utils.getClientPlayer();
		RayTraceResult mop = Minecraft.getMinecraft().objectMouseOver;
		pos = mop.getBlockPos();
		//pos = new BlockPos(14, 6, 4);
		blockState = world.getBlockState(pos);
		set(world, blockState.getBlock(), pos, blockState);

		//draw a blue block highlight
		next(GL11.GL_LINE_LOOP);
		GlStateManager.pushMatrix();
		GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());
		//disable texture when drawing lines
		disableTextures();

		//draw the cube with a color
		RenderParameters rp = new RenderParameters();
		rp.colorMultiplier.set(0x000066);

		drawShape(new Cube(), rp);
		GlStateManager.popMatrix();

		//draw the block informations
		next(GL11.GL_QUADS);
		GlStateManager.disableDepth();
		GlStateManager.pushMatrix();
		//prevent z-fighting for the text
		GL11.glPolygonOffset(-3.0F, -3.0F);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);

		//reenable the textures
		enableTextures();

		float ox = 0;
		float oy = 1;
		float oz = 0;
		float angle = 0;

		//do some math for text placement based on the direction the player is facing
		EnumFacing facing = EntityUtils.getEntityFacing(player);
		if (facing == EnumFacing.NORTH)
			oz = 1;
		else if (facing == EnumFacing.EAST)
			angle = 270;
		else if (facing == EnumFacing.SOUTH)
		{
			angle = 180;
			ox = -1;
		}
		else if (facing == EnumFacing.WEST)
		{
			angle = 90;
			oz = 1;
			ox = -1;
		}

		GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());
		GlStateManager.rotate(angle, 0, 1, 0);

		//special case when player is looking down (the top face of a block)
		if (mop.sideHit == EnumFacing.UP)
		{
			oz = 1;
			if (facing == EnumFacing.NORTH || facing == EnumFacing.WEST)
				oy = 0;

			GL11.glRotatef(-90, 1, 0, 0);
		}

		//set the font and options to use for the text
		MalisisFont font = MalisisFont.minecraftFont;
		FontOptions fro = FontOptions.builder().scale(0.1F).color(0x9999CC).shadow().build();

		//set the texts to display
		String[] texts = { pos.getX() + ", " + pos.getY() + ", " + pos.getZ(), blockState.toString(), "Side " + mop.sideHit,
				"Chunk " + (pos.getX() >> 4) + ", " + (pos.getZ() >> 4) };

		//draw the lines of text
		int i = 1;
		for (String str : texts)
			drawText(font, str, ox, oy - i++ * fro.getFontScale(), oz, fro);

		//reset OGL states
		next();
		GlStateManager.popMatrix();
		GlStateManager.enableDepth();
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
	}

	public void renderRayTrace()
	{
		next(GL11.GL_LINES);
		disableTextures();
		//disable depth to see the line go through blocks
		GlStateManager.disableDepth();

		//create the vertexes for start and end of the line
		int color = 0x339933;
		Triple<Point, Point, Point> infos = RWLDemo.rwlPointer.getRaytraceInfos();
		Point start = infos.getLeft();
		Point end = infos.getMiddle();
		Point hit = infos.getRight();

		Vertex vstart = new Vertex(start.x, start.y, start.z);
		vstart.setColor(color);
		Vertex vend = new Vertex(end.x, end.y, end.z);
		vend.setColor(color);

		Face f;
		if (hit == null)
			f = new Face(vstart, vend);
		else
		{
			//if a block was hit, add vertexes at the hit point to split the line
			Vertex vhitstart = new Vertex(hit.x, hit.y, hit.z);
			vhitstart.setColor(color);
			color = 0xAA3333;
			Vertex vhitend = new Vertex(hit.x, hit.y, hit.z);
			vhitend.setColor(color);
			//change the end color
			vend.setColor(color);

			f = new Face(vstart, vhitstart, vhitend, vend);
		}

		//draw the line
		RenderParameters rp = new RenderParameters();
		rp.usePerVertexColor.set(true);
		drawShape(new Shape(f), rp);

		//reset OGL states
		next(GL11.GL_QUADS);
		enableTextures();
		GlStateManager.enableDepth();
	}
}
