package net.malisis.demo.stargate;

import java.util.List;

import net.malisis.core.renderer.MalisisRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.RenderType;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.AnimationRenderer;
import net.malisis.core.renderer.animation.transformation.AlphaTransform;
import net.malisis.core.renderer.animation.transformation.BrightnessTransform;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.ITransformable;
import net.malisis.core.renderer.animation.transformation.ParallelTransformation;
import net.malisis.core.renderer.animation.transformation.Rotation;
import net.malisis.core.renderer.animation.transformation.Scale;
import net.malisis.core.renderer.animation.transformation.Transformation;
import net.malisis.core.renderer.animation.transformation.Translation;
import net.malisis.core.renderer.element.Shape;
import net.malisis.core.renderer.element.Vertex;
import net.malisis.core.renderer.element.face.TopFace;
import net.malisis.core.renderer.element.shape.Cube;
import net.malisis.core.renderer.model.MalisisModel;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class StargateRenderer extends MalisisRenderer
{
	//the animation renderer handle the timer and elapsed time for animations
	//it's also a container for animations
	private AnimationRenderer ar = new AnimationRenderer();
	//basic cube for inventory rendering
	private Shape cube;
	//shape for top face
	private Shape topFace;
	//model that will hold all the shapes
	private MalisisModel model;

	//number of slices the opening animation will use
	private int slices = 5;
	//height for each slice
	private float sliceHeight = 1F / slices;

	//total deployment time
	private int deployTimer = 100;
	//animations timers
	private int openTimer = (int) (deployTimer * 0.30F);
	private int rotationTimer = (int) (deployTimer * 0.30F);
	private int archTimer = (int) (deployTimer * 0.50F);
	private int fadeTimer = (int) (deployTimer * 0.20F);
	//wee need to keep a ref for those parameters because they're not store in animation renderer nor the models
	//we define an animation for them in a create*Animation() method and actually use them in render()
	private RenderParameters rpFaceArch;
	private RenderParameters rpTopFace;
	private RenderParameters rpLavaFace;

	@Override
	protected void initialize()
	{
		//build the basic cube for inventory rendering
		cube = new Cube();
		//build the topFace shape and place it accordingly
		topFace = new Shape(new TopFace());
		//move the face a bit higher than the platform to avoid z-fighting
		topFace.translate(0, -0.999F + sliceHeight, 0);
		topFace.scale(5F, 1F, 5F);

		//create the model that will hold all the shapes
		model = new MalisisModel();
		//make sure the remove all the animations (necessary only if you allow initialize() to be call multiple times)
		ar.clearAnimations();
		//load all the animations
		loadAnimations();
		//store the model current state
		model.storeState();
	}

	// #region Animations
	private void loadAnimations()
	{
		createOpeningAnimation();
		createUnrollingAnimation();
		createArchAnimation();
		createFadeAnimation();
		//createFloatingAnimation();
	}

	private void createOpeningAnimation()
	{
		RenderParameters rp = new RenderParameters();
		rp.icon.set(Stargate.sgBlock.getPlateformSideIcon());

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
			model.addShape(shape.setParameters(rp, true));
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
			shape = new Shape(shape);
			shape.translate(0.5F, 0, 0);
			model.addShape(shape.setParameters(rp, true));
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
		rp.icon.set(Stargate.sgBlock.getPlateformSideIcon());
		rp.interpolateUV.set(false);
		rp.useEnvironmentBrightness.set(false);
		rp.calculateBrightness.set(false);
		rp.calculateAOColor.set(false);
		rp.colorFactor.set(1F);

		int rt = rotationTimer * 2 / 5 * 2;
		int delay = rt / 4;
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

		Shape shapeNorth = Shape.fromShapes(shapesNorth).setParameters(rp, true);
		Shape shapeSouth = Shape.fromShapes(shapesSouth).setParameters(rp, true);
		//model.addShapes(shapeNorth, shapeSouth);

		//make the animations
		Animation anim;
		ParallelTransformation north;
		ParallelTransformation south;
		for (int row = 0; row < 4; row++)
		{
			north = new ParallelTransformation().delay(openTimer);
			south = new ParallelTransformation().delay(openTimer);

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

			Shape sn = new Shape(shapeNorth);
			Shape ss = new Shape(shapeSouth);
			model.addShapes(sn, ss);

			anim = new Animation(sn, north);
			anim.setRender(false, true);
			ar.addAnimation(anim);

			anim = new Animation(ss, south);
			anim.setRender(false, true);
			ar.addAnimation(anim);
		}
	}

	private void createArchAnimation()
	{
		RenderParameters rp = new RenderParameters();
		rp.renderAllFaces.set(true);
		rp.icon.set(Stargate.sgBlock.getPlateformSideIcon());
		rp.interpolateUV.set(false);

		// override rendering parameters for bottom face
		rpFaceArch = new RenderParameters();
		rpFaceArch.calculateAOColor.set(false);
		rpFaceArch.calculateBrightness.set(false);
		rpFaceArch.useEnvironmentBrightness.set(false);
		rpFaceArch.brightness.set(32);
		rpFaceArch.icon.set(Blocks.diamond_block.getIcon(0, 0));

		// create the shape
		Shape base = new Cube();
		base.setParameters(rp, true);
		base.shrink(ForgeDirection.UP, 0.62F);
		base.shrink(ForgeDirection.DOWN, 0.79F);
		base.storeState();

		int totalAngle = 140;
		float angle = 10;
		int totalArch = (int) (totalAngle / angle);
		int at = archTimer / totalArch;
		//at = 20;

		Shape shape;
		Transformation trans;
		Animation anim;
		for (int i = 0; i < totalArch; i++)
		{
			float archAngle = angle * (totalArch - i) - angle / 2;
			int rt = i * at + 1;
			int rd = i * at / 2;
			int st = at / 2;
			int sd = rt + rd;

			shape = new Shape(base);
			shape.setParameters("top", rpFaceArch, true);
			model.addShape(shape);
			// rotation then scaling of the western blocks of the arch
			//@formatter:off
			trans = new ParallelTransformation(
					new Translation(0, -1, 0).forTicks(1),
					new Rotation(180 - totalAngle, 180 - archAngle).aroundAxis(0, 0, 1).offset(0, 2, 0).forTicks(rt, rd),
					new Scale(0.5F, 0.3F, 0.2F, 0.5F, 0.5F, 0.3F).forTicks(st, sd)
					).delay(openTimer + rotationTimer / 2);
			//@formatter:on
			anim = new Animation(shape, trans);
			anim.setRender(false, true);
			ar.addAnimation(anim);

			//@formatter:off
			ChainedTransformation ct = new ChainedTransformation(
					new BrightnessTransform(32, 240).forTicks(fadeTimer).movement(Transformation.SINUSOIDAL).delay(fadeTimer),
					new BrightnessTransform(240, 32).forTicks(fadeTimer).movement(Transformation.SINUSOIDAL)
					).delay(deployTimer  + rt).loop(-1);
			//@formatter:on
			anim = new Animation(shape.getFace("top").getParameters(), ct);
			ar.addAnimation(anim);

			// rotation then scaling of the eastern blocks of the arch
			shape = new Shape(base);
			shape.setParameters("top", rpFaceArch, true);
			//shape.rotate(-130, 0, 0, 1, 0, -2.2F, 0);
			model.addShape(shape.storeState());
			//@formatter:off
			trans = new ParallelTransformation(
					new Translation(0, -1, 0).forTicks(1),
					new Rotation(180 + totalAngle, 180 + archAngle).aroundAxis(0, 0, 1).offset(0, 2, 0).forTicks(rt, rd),
					new Scale(0.5F, 0.3F, 0.2F, 0.5F, 0.5F, 0.3F).forTicks(st, sd)
					).delay(openTimer + rotationTimer / 2);
			//@formatter:on
			anim = new Animation(shape, trans);
			anim.setRender(false, true);
			ar.addAnimation(anim);

			//@formatter:off
			ct = new ChainedTransformation(
					new BrightnessTransform(32, 240).forTicks(fadeTimer).movement(Transformation.SINUSOIDAL).delay(fadeTimer),
					new BrightnessTransform(240, 32).forTicks(fadeTimer).movement(Transformation.SINUSOIDAL)
					).delay(deployTimer + fadeTimer- rt).loop(-1);
			//@formatter:on
			anim = new Animation(shape.getFace("top").getParameters(), ct);
			ar.addAnimation(anim);
		}
	}

	private void createFadeAnimation()
	{
		AlphaTransform at = new AlphaTransform(0, 255).forTicks(fadeTimer, deployTimer);
		rpTopFace = new RenderParameters();
		rpTopFace.alpha.set(0);
		rpTopFace.icon.set(Stargate.sgBlock.getPlateformIcon());
		Animation anim = new Animation(rpTopFace, at);
		ar.addAnimation(anim);

		//@formatter:off
		ParallelTransformation pt = new ParallelTransformation(
										new BrightnessTransform(0, 240).forTicks(fadeTimer),
										new AlphaTransform(0, 255).forTicks(fadeTimer)
									).delay(deployTimer + fadeTimer);
		//@formatter:on
		rpLavaFace = new RenderParameters();
		rpLavaFace.calculateBrightness.set(false);
		rpLavaFace.useEnvironmentBrightness.set(false);
		rpLavaFace.alpha.set(0);
		rpLavaFace.icon.set(Blocks.lava.getIcon(1, 0));
		anim = new Animation(rpLavaFace, pt);
		ar.addAnimation(anim);
	}

	@SuppressWarnings("unused")
	private void createFloatingAnimation()
	{
		RenderParameters rp = new RenderParameters();
		rp.icon.set(Blocks.gold_block.getIcon(0, 0));
		rp.useEnvironmentBrightness.set(false);
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
		if (renderType == RenderType.TESR_WORLD)
			renderStargateTileEntity();
		else if (renderType == RenderType.ISBRH_WORLD)
		{
			//recall initialize() (for debug purpose)
			initialize();
		}
		else if (renderType == RenderType.ISBRH_INVENTORY)
		{
			//draw standard cube with default renderparameters
			cube.resetState();
			drawShape(new Cube());
		}
	}

	private void renderStargateTileEntity()
	{
		//set the timer for the animimation renderer. It calculates the elpasedTime
		ar.setStartTime(((StargateTileEntity) tileEntity).placedTimer);

		//enable blending because we have fading for the TopFace
		enableBlending();
		//reset the model to its orinigal state. That means reset the vertex positions that have been moved around in the previous render loop
		model.resetState();

		// 1st non moving cube at the bottom
		cube.resetState();
		cube.setBounds(0, 0, 0, 1F, sliceHeight, 1F);
		drawShape(cube);

		//animate all the animations, this will return the transformable for the animations
		//if an animation is set to not render before or after its transform, then it won't be in the list
		List<ITransformable> shapes = ar.animate();
		for (ITransformable s : shapes)
		{
			//make sure we draw shapes only because we added animations for RenderParameters too
			if (s instanceof Shape)
				drawShape((Shape) s, rp);
		}

		//first draw the lava face
		topFace.resetState();
		drawShape(topFace, rpLavaFace);

		//draw the platform face, a bit higher to prevent z-fighting
		topFace.translate(0, 0.001F, 0);
		drawShape(topFace, rpTopFace);

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		//we want to render a basic 3D cube
		return true;
	}

}
