package net.malisis.demo.guidemo;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.decoration.UIImage;
import net.malisis.core.client.gui.component.decoration.UILabel;
import net.malisis.core.client.gui.component.interaction.UIButton;
import net.malisis.core.client.gui.component.interaction.UICheckBox;
import net.malisis.core.client.gui.component.interaction.UISelect;
import net.malisis.core.client.gui.component.interaction.UITextField;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumChatFormatting;

public class Gui extends MalisisGui
{
	private UIPanel cbPanel, bulletsPanel;
	private UIButton btn1, btn2, btnOver;
	private UICheckBox cb;

	public Gui(MalisisInventoryContainer inventoryContainer)
	{
		super();
		setInventoryContainer(inventoryContainer);

		UIWindow window = new UIWindow(300, 220);
		window.setPosition(0, -40, Anchor.CENTER | Anchor.MIDDLE);

		cbPanel = new UIPanel(290, 120);
		cbPanel.setVerticalScroll(true);

		cb = new UICheckBox("CheckBox with label").setTooltip(EnumChatFormatting.AQUA + "with a tooltip!");

		UITextField tf = new UITextField(200, "This textfield will only accept numbers.");
		tf.setFilter("\\d+");
		tf.setPosition(0, 35);
		tf.setAutoSelectOnFocus(true);

		UIContainer inv = setInventoryContainer(inventoryContainer.getContainerInventory());

		UISelect select = new UISelect(100, new String[] { "Option 1", "Option 2", "Very ultra longer option 3", "Shorty", "Moar options",
				"Even more", "Even Steven", "And a potato too" });
		select.setPosition(0, 55);
		select.maxExpandedWidth(120);
		// select.maxDisplayedOptions(5);
		select.select(2);

		btn1 = new UIButton("Horizontal", 80).setPosition(0, 90, Anchor.CENTER).register(this);
		btn2 = new UIButton("Vertical", 80).setPosition(0, 120, Anchor.CENTER).register(this);
		btnOver = new UIButton("Over the top").setPosition(0, 170, Anchor.CENTER);

		cbPanel.add(cb);
		cbPanel.add(new UIImage(Items.diamond_axe.getIconFromDamage(0), UIImage.ITEMS_TEXTURE).setPosition(0, 10));
		cbPanel.add(new UILabel("This is LABEL!").setPosition(20, 15));
		cbPanel.add(tf);
		cbPanel.add(inv);
		cbPanel.add(select);

		cbPanel.add(btn1);
		cbPanel.add(btn2);
		cbPanel.add(btnOver);

		bulletsPanel = new UIPanel(100, 250).setPosition(0, 0, Anchor.RIGHT);
		bulletsPanel.register(this);
		bulletsPanel.setName("bulletsPanel");

		// cbPanel.add(bulletsPanel);
		UIPlayerInventory playerInv = new UIPlayerInventory(inventoryContainer.getPlayerInventory());
		playerInv.setPosition(0, 0, Anchor.BOTTOM | Anchor.CENTER);

		window.add(cbPanel);
		window.add(playerInv);

		addToScreen(window);
	}

	private UIContainer setInventoryContainer(MalisisInventory inventory)
	{
		UIContainer c = new UIContainer(100, 30);
		c.setPosition(0, 70);

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			UISlot slot = new UISlot(inventory.getSlot(i)).setPosition(i * 18, 0);
			c.add(slot);
		}

		return c;
	}
}