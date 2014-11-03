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

package net.malisis.demo.test;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.control.UIScrollBar;
import net.malisis.core.client.gui.component.control.UISlimScrollbar;
import net.malisis.core.client.gui.component.decoration.UILabel;
import net.malisis.core.client.gui.component.decoration.UIMultiLineLabel;
import net.malisis.core.client.gui.component.interaction.UIButton;
import net.malisis.core.renderer.animation.Animation;
import net.malisis.core.renderer.animation.transformation.AlphaTransform;
import net.malisis.core.renderer.animation.transformation.ChainedTransformation;
import net.malisis.core.renderer.animation.transformation.ColorTransform;
import net.malisis.core.renderer.animation.transformation.PositionTransform;

import com.google.common.eventbus.Subscribe;

/**
 * @author Ordinastie
 *
 */
public class TestGui extends MalisisGui
{
	private UIPanel panel;
	private UIButton bigButton;
	private UIButton upButton;
	private UIButton downButton;
	private UIButton leftButton;
	private UIButton rightButton;

	private UIButton hsButton;
	private UIButton vsButton;

	private UIButton blueButton;
	private UIButton redButton;
	private UIButton alphaButton;

	private UIScrollBar vertical;
	private UIScrollBar horizontal;

	public TestGui()
	{

		bigButton = new UIButton(this, "BIG BUTTON");
		bigButton.setSize(50, 50);
		bigButton.setPosition(100, 80);
		bigButton.setZIndex(0);

		panel = new UIPanel(this).setPosition(0, 15).setSize(-153, 110);
		panel.add(bigButton);

		for (int i = 1; i < 10; i++)
			panel.add(new UILabel(this, "Some label " + i).setPosition(0, 11 * (i - 1)));

		vertical = new UIScrollBar(this, panel, UIScrollBar.Type.VERTICAL).setAutoHide(true);
		horizontal = new UIScrollBar(this, panel, UIScrollBar.Type.HORIZONTAL).setAutoHide(true);

		UIPanel lorem = new UIPanel(this).setPosition(0, 15, Anchor.RIGHT).setSize(150, 110);
		UIMultiLineLabel ipsum = new UIMultiLineLabel(this);
		ipsum.setText("Contrairement à une opinion répandue, le Lorem Ipsum n'est pas simplement du texte aléatoire. Il trouve ses racines dans une oeuvre de la littérature latine classique datant de 45 av. J.-C., le rendant vieux de 2000 ans. Un professeur du Hampden-Sydney College, en Virginie, s'est intéressé à un des mots latins les plus obscurs, consectetur, extrait d'un passage du Lorem Ipsum, et en étudiant tous les usages de ce mot dans la littérature classique, découvrit la source incontestable du Lorem Ipsum. Il provient en fait des sections 1.10.32 et 1.10.33 du \"De Finibus Bonorum et Malorum\" (Des Suprêmes Biens et des Suprêmes Maux) de Cicéron. Cet ouvrage, très populaire pendant la Renaissance, est un traité sur la théorie de l'éthique. Les premières lignes du Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", proviennent de la section 1.10.32");

		lorem.add(ipsum);

		new UISlimScrollbar(this, ipsum, UIScrollBar.Type.VERTICAL);

		upButton = new UIButton(this, "Up", 25).setPosition(0, -40, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);
		downButton = new UIButton(this, "Down", 25).setPosition(0, 0, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);
		leftButton = new UIButton(this, "Left", 25).setPosition(-30, -20, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);
		rightButton = new UIButton(this, "Right", 25).setPosition(30, -20, Anchor.BOTTOM | Anchor.CENTER).setZIndex(1).register(this);

		hsButton = new UIButton(this, "Horizontal").setPosition(0, 0, Anchor.BOTTOM | Anchor.RIGHT).setZIndex(1).register(this);
		vsButton = new UIButton(this, "Vertical").setPosition(0, -30, Anchor.BOTTOM | Anchor.RIGHT).setZIndex(1).register(this);

		blueButton = new UIButton(this, "Blue").setPosition(0, 0, Anchor.BOTTOM).setZIndex(1).register(this);
		redButton = new UIButton(this, "Red").setPosition(0, -20, Anchor.BOTTOM).setZIndex(1).register(this);
		alphaButton = new UIButton(this, "Alpha").setPosition(0, -40, Anchor.BOTTOM).setZIndex(1).register(this);

		UIWindow window = new UIWindow(this, "Test GUI", 300, 200);
		window.add(upButton);
		window.add(downButton);
		window.add(leftButton);
		window.add(rightButton);

		window.add(hsButton);
		window.add(vsButton);

		window.add(blueButton);
		window.add(redButton);
		window.add(alphaButton);

		window.add(panel);
		window.add(lorem);

		addToScreen(window);
	}

	@Subscribe
	public void onClick(UIButton.ClickEvent event)
	{
		UIButton b = event.getComponent();
		if (b == vsButton)
		{
			vertical.setVisible(!vertical.isVisible());
			return;
		}
		else if (b == hsButton)
		{
			horizontal.setVisible(!horizontal.isVisible());
			return;
		}
		else if (b == blueButton || b == redButton)
		{
			int color = b == blueButton ? 0x3333FF : 0x993333;
			//@formatter:off
			ChainedTransformation ct = new ChainedTransformation(
						new ColorTransform(panel.getBackgroundColor(), color).forTicks(5),
						new ColorTransform(color, panel.getBackgroundColor()).forTicks(5)
						);
			//@formatter:on
			Animation anim = new Animation(panel, ct);
			animate(anim);
		}
		else if (b == alphaButton)
		{
			//@formatter:off
			ChainedTransformation ct = new ChainedTransformation(
						new AlphaTransform(255, 0).forTicks(10),
						new AlphaTransform(0, 255).forTicks(10).delay(20)
						);
			//@formatter:on
			Animation anim = new Animation(panel, ct);
			animate(anim);
		}

		int a = isShiftKeyDown() ? 50 : 1;
		int x = 0;
		int y = 0;
		if (b == upButton)
			y = -a;
		else if (b == downButton)
			y = a;
		else if (b == leftButton)
			x = -a;
		else if (b == rightButton)
			x = a;

		moveTo(bigButton, bigButton.getX() + x, bigButton.getY() + y);
	}

	private void moveTo(UIComponent component, int x, int y)
	{
		PositionTransform pt = new PositionTransform<>(component.getX(), component.getY(), x, y).forTicks(20);

		Animation anim = new Animation(component, pt);
		animate(anim);

	}
}
