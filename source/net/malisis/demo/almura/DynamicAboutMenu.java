/**
 * This file is part of Almura, All Rights Reserved.
 *
 * Copyright (c) AlmuraDev <http://github.com/AlmuraDev/>
 */
package net.malisis.demo.almura;

import java.util.List;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.GuiRenderer;
import net.malisis.core.client.gui.GuiTexture;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.container.UIBackgroundContainer;
import net.malisis.core.client.gui.component.container.UIListContainer;
import net.malisis.core.client.gui.component.decoration.UIImage;
import net.malisis.core.client.gui.component.decoration.UILabel;
import net.malisis.core.client.gui.component.interaction.UIButton;
import net.malisis.core.client.gui.element.SimpleGuiShape;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

@SideOnly(Side.CLIENT)
public class DynamicAboutMenu extends SimpleGui
{

	private UIBackgroundContainer contentContainer;
	private UIBackgroundContainer bottomBorderContainer;

	public DynamicAboutMenu(SimpleGui parent)
	{
		super(parent);
		guiscreenBackground = false;
	}

	@Override
	public void construct()
	{
		// Create the form
		final UIBackgroundContainer container = new UIBackgroundContainer(this);
		container.setBackgroundAlpha(0);

		final UILabel titleLabel = new UILabel(this, "About");
		//titleLabel.setFontRenderOptions(FontRenderOptionsConstants.FRO_COLOR_WHITE);
		titleLabel.setPosition(0, 20, Anchor.TOP | Anchor.CENTER);

		final UIBackgroundContainer topBorderContainer = new UIBackgroundContainer(this, UIComponent.INHERITED, 4);
		topBorderContainer.setPosition(0, SimpleGui.getPaddedY(titleLabel, 3));
		topBorderContainer.setColor(0);
		topBorderContainer.setTopAlpha(255);
		topBorderContainer.setBottomAlpha(120);

		contentContainer = new UIBackgroundContainer(this, UIComponent.INHERITED, container.getHeight() - 104);
		contentContainer.setPosition(0, SimpleGui.getPaddedY(topBorderContainer, 0));
		contentContainer.setColor(0);
		contentContainer.setBackgroundAlpha(100);
		contentContainer.setPadding(4, 10);
		contentContainer.setClipContent(false);

		bottomBorderContainer = new UIBackgroundContainer(this, UIComponent.INHERITED, 4);
		bottomBorderContainer.setPosition(0, container.getHeight() - 86);
		bottomBorderContainer.setColor(0);
		bottomBorderContainer.setTopAlpha(120);
		bottomBorderContainer.setBottomAlpha(255);

		final AboutList list = new AboutList(this, 100, 200);
		list.setElementSpacing(4);
		list.setPosition(0, 0, Anchor.CENTER | Anchor.TOP);
		List<Pair<String, GuiTexture>> elements = Lists.newArrayList();
		for (int i = 0; i < 50; i++)
			elements.add(Pair.of("Zidane " + i, new GuiTexture(new ResourceLocation("malisisdemos", "textures/zidane_head.png"))));
		list.setElements(elements);

		contentContainer.add(list);
		container.add(titleLabel, topBorderContainer, contentContainer, bottomBorderContainer);

		addToScreen(contentContainer);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		if (this.contentContainer != null && this.bottomBorderContainer != null)
		{
			bottomBorderContainer.setPosition(0, SimpleGui.getPaddedY(this.contentContainer, 0));
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Subscribe
	public void onButtonClick(UIButton.ClickEvent event)
	{}

	protected static final class AboutList2 extends UIListContainer<AboutList2, AboutListElement>
	{

		private static final int padding = 4;
		private static final int textureHeight = 32;
		private static final int textureWidth = 32;
		private static final int elementHeight = textureHeight + (padding * 2);

		public AboutList2(MalisisGui gui, int width, int height)
		{
			super(gui, width, height);

			shape = new SimpleGuiShape();
			shape.setSize(textureWidth, textureHeight);
			shape.setPosition(padding, padding);
			shape.storeState();
		}

		public void addElement(AboutListElement element)
		{
			if (elements == null)
			{
				elements = Lists.newArrayList();
			}
			element.setParent(this);
			this.elements.add(element);
		}

		@Override
		public int getElementHeight(AboutListElement element)
		{
			return elementHeight;
		}

		@Override
		public void drawElementBackground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick, AboutListElement element, boolean isHovered)
		{}

		@Override
		public void drawElementForeground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick, AboutListElement element, boolean isHovered)
		{
			element.draw(renderer, mouseX, mouseY, partialTick);
		}
	}

	protected static final class AboutListElement extends UIBackgroundContainer
	{

		private final UIImage image;
		private final UILabel label;

		private AboutListElement(MalisisGui gui, ResourceLocation location, String text)
		{
			super(gui);
			this.image = new UIImage(gui, new GuiTexture(location), null);
			this.label = new UILabel(gui, text);
			this.label.setPosition(SimpleGui.getPaddedX(image, 4), 0);
			this.add(image, label);

			setBottomColor(0x333366);
			setSize(45, 16);
		}
	}
}