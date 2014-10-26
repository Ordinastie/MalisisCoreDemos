package net.malisis.demo.stargate;

import net.malisis.core.renderer.BaseRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.ParallelTransformation;
import net.malisis.core.renderer.animation.transformation.Rotation;
import net.malisis.core.renderer.animation.transformation.Scale;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.Vertex;
import net.malisis.core.renderer.element.face.TopFace;
import net.malisis.core.renderer.element.shape.Cube;
import net.malisis.demo.MalisisDemos;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class StargateRenderer extends BaseRenderer
{
	public static int renderId;
	public static ResourceLocation rlPlatform = new ResourceLocation(MalisisDemos.modid, "textures/blocks/sgplatform.png");
	int slices = 5;
	float sliceHeight = 1F / slices;

	int deployTimer = StargateBlock.deployTimer;
	int openTimer = (int) (deployTimer * 0.30F);
	int rotationTimer = (int) (deployTimer * 0.30F);
	int archTimer = (int) (deployTimer * 0.50F);
	int fadeTimer = (int) (deployTimer * 0.20F);

	AnimationRenderer ar = new AnimationRenderer(this);

	public StargateRenderer()
	{
		// make sure the renderer is registered after pre init, otherwise you may
		// not be able to retrieve block icons yet
		createOpeningAnimation();
		createUnrollingAnimation();
		createArchAnimation();
		//	createFloatingAnimation();
	}

	// #region Animations
	private void createOpeningAnimation()
	{
		int ot = openTimer / slices;
		Shape shape;
		Transformation trans;
		Animation anim;
		for (int i = 1; i < slices; i++)
		{
			int t = ot * i;
			// opening towards west
			shape = new Cube().setBounds(0, 0, 0, 0.5F, sliceHeight, 1F);
			shape.translate(0, sliceHeight * i, 0F);
			//@formatter:off
			trans = new ParallelTransformation(
					new Translation(-0.5F * i, 0, 0).forTicks(t),
					new Translation(0F, -sliceHeight * i, 0F).forTicks(t, ot)
			);
			//@formatter:on0
			anim = new Animation(shape, trans);
			anim.setRender(true, true);
			ar.addAnimation(anim);

			// opening toward east
			shape = new Shape(shape).translate(0.5F, 0, 0);
			// move east first, then down
			//@formatter:off
			trans = new ParallelTransformation(
					new Translation(0.5F * i, 0, 0).forTicks(t),
					new Translation(0F, -sliceHeight * i, 0F).forTicks(t, ot)
			);
			//@formatter:on
			anim = new Animation(shape, trans);
			anim.setRender(true, true);
			ar.addAnimation(anim);
		}
	}

	private void createUnrollingAnimation()
	{
		RenderParameters rp = new RenderParameters();
		rp.useBlockBrightness.set(false);
		rp.calculateAOColor.set(false);

		int rt = rotationTimer * 2 / 5;
		int delay = rt / 2;
		float y = -0.5F + sliceHeight / 2;

		// create the shapes
		Shape[] shapesNorth = new Shape[slices];
		Shape[] shapesSouth = new Shape[slices];
		for (int i = 0; i < slices; i++)
		{
			Shape sN = new Cube().setBounds(0, 0, 0, 1, sliceHeight, 0.5F);
			sN.translate(-2 + i, 0, 0);
			shapesNorth[i] = sN;

			Shape sS = new Shape(sN);
			sS.translate(0, 0, 0.5F);
			shapesSouth[i] = sS;
		}

		Shape shapeNorth = Shape.fromShapes(shapesNorth);
		Shape shapeSouth = Shape.fromShapes(shapesSouth);

		//make the animations
		Animation anim;
		ParallelTransformation north;
		ParallelTransformation south;
		for (int row = 0; row < 4; row++)
		{
			north = new ParallelTransformation();
			south = new ParallelTransformation();

			// create the animations
			north.addTransformations(new Rotation(-180, 1F, 0, 0, 0, y, -0.5F).forTicks(rt, 0));
			south.addTransformations(new Rotation(180, 1F, 0, 0, 0, y, 0.5F).forTicks(rt, 0));
			if (row > 0)
			{
				north.addTransformations(new Rotation(-180, 1F, 0, 0, 0, y, 0).forTicks(rt, delay));
				south.addTransformations(new Rotation(180, 1F, 0, 0, 0, y, 0).forTicks(rt, delay));
			}
			if (row > 1)
			{
				north.addTransformations(new Rotation(-180, 1F, 0, 0, 0, y, -0.5F).forTicks(rt, delay * 2));
				south.addTransformations(new Rotation(180, 1F, 0, 0, 0, y, 0.5F).forTicks(rt, delay * 2));
			}
			if (row > 2)
			{
				north.addTransformations(new Rotation(-180, 1F, 0, 0, 0, y, 0).forTicks(rt, delay * 3));
				south.addTransformations(new Rotation(180, 1F, 0, 0, 0, y, 0).forTicks(rt, delay * 3));
			}

			// link the shapes the the animations
			anim = new Animation(shapeNorth, north, rp, openTimer);
			anim.setRender(false, true);
			ar.addAnimation(anim);

			anim = new Animation(shapeSouth, south, rp, openTimer);
			anim.setRender(false, true);
			ar.addAnimation(anim);
		}
	}

	private void createArchAnimation()
	{
		// override rendering parameters for bottom face
		RenderParameters rpFace = new RenderParameters();
		rpFace.calculateAOColor.set(false);
		rpFace.calculateBrightness.set(false);
		rpFace.useBlockBrightness.set(false);
		rpFace.brightness.set(32);
		rpFace.icon.set(Blocks.diamond_block.getIcon(0, 0));

		// create the shape
		Shape base = new Cube();
		base.setParameters("bottom", rpFace, true);
		base.translate(0, 3, 0);
		base.shrink(ForgeDirection.DOWN, 0.69F);
		base.shrink(ForgeDirection.UP, 0.87F);

		int totalArch = 13;
		float angle = 10;
		int at = archTimer / totalArch;
		// at = 10;

		RenderParameters rp = new RenderParameters();
		rp.renderAllFaces.set(true);

		Shape shape;
		Transformation trans;
		Animation anim;
		for (int i = 0; i < totalArch; i++)
		{
			float archAngle = 130 - (angle * i + angle / 2);
			int delay = (totalArch - i) * at;

			shape = new Shape(base).rotate(130, 0, 0, 1, 0, -2.2F, 0);
			// rotation then scaling of the western blocks of the arch
			//@formatter:off
			trans = new ParallelTransformation(
					new Rotation(-archAngle).aroundAxis(0, 0, 1).offset(0, -2.2F, 0).forTicks(at, delay),
					new Scale(0.5F, 0.3F, 0.2F, 0.5F, 0.5F, 0.3F).forTicks(at / 2, delay + at)
					);
			//@formatter:on
			anim = new Animation(shape, trans, rp, openTimer);
			anim.setRender(false, true);
			ar.addAnimation(anim);

			// rotation then scaling of the eastern blocks of the arch
			shape = new Shape(base).rotate(-130, 0, 0, 1, 0, -2.2F, 0);
			//@formatter:off
			trans = new ParallelTransformation(
					new Rotation(archAngle).aroundAxis(0, 0, 1).offset(0, -2.2F, 0).forTicks(at, delay),
					new Scale(0.5F, 0.3F, 0.2F, 0.5F, 0.5F, 0.3F).forTicks(at / 2, delay + at)
					);
			//@formatter:on
			anim = new Animation(shape, trans, rp, openTimer);
			anim.setRender(false, true);
			ar.addAnimation(anim);
		}
	}

	@SuppressWarnings("unused")
	private void createFloatingAnimation()
	{
		RenderParameters rp = new RenderParameters();
		rp.icon.set(Blocks.gold_block.getIcon(0, 0));
		rp.useBlockBrightness.set(false);
		rp.brightness.set(Vertex.BRIGHTNESS_MAX);
		rp.alpha.set(175);

		Shape s = new Cube();
		s.scale(0.2F);
		s.applyMatrix();
		s.translate(-1.0F, 1.5F, 0);

		//		ar.rotate(360, 0, 1, 0, 1.0F, 0, 0).forTicks(50).loop(-1).translate(0, 1, 0).forTicks(20).loop(-1, 0, 20).sinusoidal()
		//				.translate(0, -1, 0).forTicks(20).loop(-1, 20, 0).sinusoidal().rotate(360, 1, 0, 0).forTicks(50).loop(-1)
		//				.animate("floating", s, rp);
	}

	// #end Animations

	@Override
	public void render()
	{
		if (renderType == TYPE_TESR_WORLD)
			renderStargateTileEntity();
		else if (renderType == TYPE_ISBRH_WORLD)
			renderStargateBlock();
		else if (renderType == TYPE_ISBRH_INVENTORY)
		{
			RenderParameters rp = new RenderParameters();
			rp.colorMultiplier.set(0x6666AA);
			drawShape(new Cube());
		}
	}

	private void renderStargateTileEntity()
	{
		StargateTileEntity te = (StargateTileEntity) tileEntity;

		int alpha = 255;
		boolean drawTopFace = false;
		ar.setStartTime(te.placedTimer);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);

		if (blockMetadata == 0)
		{
			// 1st non moving cube at the bottom
			drawShape(new Cube().setBounds(0, 0, 0, 1F, sliceHeight, 1F));
			// render animations
			ar.animate();
			ar.render(this);

			// manually calculate the alpha of the top texture
			float comp = Math.min((ar.getElapsedTime() - deployTimer + fadeTimer) / fadeTimer, 1);
			if (comp > 0)
			{
				alpha = (int) (255 * comp);
				drawTopFace = true;
			}
		}

		if (blockMetadata == 1 || drawTopFace)
		{
			// next() needs to be called to trigger a draw before we bind another texture
			// otherwise, all the blocks would use that new texture
			next();

			Shape topFace = new Shape(new TopFace());
			// move the platform a bit higher than the block to avoid z-fighting
			topFace.translate(0, -0.499F + sliceHeight / 2, 0);
			topFace.scale(5F, sliceHeight, 5F);

			bindTexture(rlPlatform);
			RenderParameters rp = new RenderParameters();
			rp.useCustomTexture.set(true);
			rp.alpha.set(alpha);
			drawShape(topFace, rp);
		}

	}

	private void renderStargateBlock()
	{
		if (blockMetadata != 1)
			return;

		// override rendering parameters for top face of the platform
		// change the texture to lava and set the brightness to max
		RenderParameters rpFace = new RenderParameters();
		rpFace.calculateAOColor.set(false);
		rpFace.calculateBrightness.set(false);
		rpFace.brightness.set(Vertex.BRIGHTNESS_MAX);
		rpFace.useBlockBrightness.set(false);
		rpFace.icon.set(Blocks.lava.getIcon(0, 0));
		rpFace.colorFactor.set(1F);
		rpFace.interpolateUV.set(false);

		//Draw the platform
		Shape platform = new Cube();
		platform.translate(0, -0.5F + sliceHeight / 2, 0);
		platform.scale(5F, sliceHeight, 5F);
		//set the paramaters for the top face
		platform.setParameters("top", rpFace, true);
		drawShape(platform);

		//Create the base of each arch block
		Shape base = new Cube();
		//reuse the parameters for the top face platform, just change the icon
		rpFace.icon.set(Blocks.diamond_block.getIcon(0, 0));
		base.setParameters("bottom", rpFace, true);
		base.translate(0, 3, 0);
		base.shrink(ForgeDirection.DOWN, 0.69F);
		base.shrink(ForgeDirection.UP, 0.87F);

		int totalArch = 13;
		float angle = 10;

		RenderParameters rp = new RenderParameters();
		rp.renderAllFaces.set(true);

		for (int i = 0; i < totalArch; i++)
		{
			float archAngle = angle * i + angle / 2;

			Shape s1 = new Shape(base);
			s1.rotate(-archAngle, 0, 0, 1, 0, -2.2F, 0);
			s1.scale(0.5F, 0.5F, 0.3F);

			Shape s2 = new Shape(base);
			s2.rotate(archAngle, 0, 0, 1, 0, -2.2F, 0);
			s2.scale(0.5F, 0.5F, 0.3F);

			drawShape(s1, rp);
			drawShape(s2, rp);
		}

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

}
