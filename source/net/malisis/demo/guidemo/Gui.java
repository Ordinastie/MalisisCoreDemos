package net.malisis.demo.guidemo;

import java.util.Arrays;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UITabGroup;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.decoration.UIImage;
import net.malisis.core.client.gui.component.decoration.UILabel;
import net.malisis.core.client.gui.component.interaction.UIButton;
import net.malisis.core.client.gui.component.interaction.UICheckBox;
import net.malisis.core.client.gui.component.interaction.UIRadioButton;
import net.malisis.core.client.gui.component.interaction.UISelect;
import net.malisis.core.client.gui.component.interaction.UISlider;
import net.malisis.core.client.gui.component.interaction.UITab;
import net.malisis.core.client.gui.component.interaction.UITextField;
import net.malisis.core.client.gui.event.ComponentEvent.ValueChanged;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.eventbus.Subscribe;

public class Gui extends MalisisGui
{
	private UIPanel panel, panel2;
	private UIButton btn1, btn2, btnOver;
	private UICheckBox cb;

	public Gui(MalisisInventoryContainer inventoryContainer)
	{
		super();
		setInventoryContainer(inventoryContainer);

		UIWindow window = new UIWindow(300, 240).setPosition(0, -40, Anchor.CENTER | Anchor.MIDDLE);
		panel = new UIPanel(290, 140)/*.setVerticalScroll(true)*/;
		panel2 = new UIPanel(290, 140)/*.setVerticalScroll(true)*/;
		panel.setBackgroundColor(0xFFDDEE);
		panel2.setBackgroundColor(0xCCCCFF);

		UITabGroup tabGroup = new UITabGroup();
		//UIImage img = new UIImage(Blocks.bookshelf.getIcon(0, 0), UIImage.BLOCKS_TEXTURE);
		UIImage img = new UIImage(new ItemStack(Items.nether_star));
		UIImage img2 = new UIImage(new ItemStack(Blocks.gold_ore));
		UITab tab1 = new UITab(img);
		UITab tab2 = new UITab(img2);

		tabGroup.addTab(tab1, panel);
		tabGroup.addTab(tab2, panel2);

		tabGroup.setActiveTab(tab1);

		cb = new UICheckBox("CheckBox with label").setTooltip(EnumChatFormatting.AQUA + "with a tooltip!");

		UIRadioButton rb1 = new UIRadioButton("newRb", "Radio value 1").setPosition(0, 14);
		UIRadioButton rb2 = new UIRadioButton("newRb", "Radio value 2").setPosition(rb1.getWidth() + 10, 14);

		UISlider slider = new UISlider(150, 0, 100, "Slider value : %.0f").setPosition(0, 26).register(this);

		UITextField tf = new UITextField(200, "This textfield will only accept numbers.");
		tf.setPosition(0, 52);
		tf.setAutoSelectOnFocus(true);

		UIContainer inv = setInventoryContainer(inventoryContainer.getContainerInventory());

		UISelect select = new UISelect(100, UISelect.Option.fromList(Arrays.asList("Option 1", "Option 2", "Very ultra longer option 3",
				"Shorty", "Moar options", "Even more", "Even Steven", "And a potato too")));
		select.setPosition(0, 70);
		select.maxExpandedWidth(120);
		//select.maxDisplayedOptions(5);
		select.select(2);

		btn1 = new UIButton("Horizontal", 80).setPosition(0, 90, Anchor.CENTER).register(this);
		btn2 = new UIButton("Vertical", 80).setPosition(0, 120, Anchor.CENTER).register(this);
		btnOver = new UIButton("Over the top").setPosition(0, 170, Anchor.CENTER);

		panel.add(cb);
		panel.add(rb1);
		panel.add(rb2);
		panel.add(slider);

		panel.add(tf);
		panel.add(select);

		panel.add(btn1);
		panel.add(btn2);
		panel.add(btnOver);

		panel2.add(inv);
		panel2.add(new UIImage(Items.diamond_axe.getIconFromDamage(0), UIImage.ITEMS_TEXTURE).setPosition(0, 10));
		panel2.add(new UILabel("This is LABEL!").setPosition(20, 15));

		UIPlayerInventory playerInv = new UIPlayerInventory(inventoryContainer.getPlayerInventory());
		playerInv.setPosition(0, 0, Anchor.BOTTOM | Anchor.CENTER);

		window.add(tabGroup);
		window.add(panel);
		window.add(panel2);

		window.add(playerInv);

		addToScreen(window);
	}

	private UIContainer setInventoryContainer(MalisisInventory inventory)
	{
		UIContainer c = new UIContainer(100, 30);
		c.setPosition(0, 50);

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			UISlot slot = new UISlot(inventory.getSlot(i)).setPosition(i * 18, 0);
			c.add(slot);
		}

		return c;
	}

	@Subscribe
	public void onSliderChanged(ValueChanged<UISlider, Float> event)
	{
		int v = (int) (event.getNewValue() / 100 * 255);
		int g = Math.abs(-255 + 2 * v);
		int r = v;
		int b = 255 - g;
		//MalisisCore.message(r + " > " + Integer.toHexString(r << 16 | 0x00FFFF));
		panel.setBackgroundColor(r << 16 | g << 8 | b);
	}
}