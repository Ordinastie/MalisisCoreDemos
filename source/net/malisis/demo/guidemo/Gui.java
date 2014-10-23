package net.malisis.demo.guidemo;

import java.util.Arrays;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UITabGroup;
import net.malisis.core.client.gui.component.container.UITabGroup.Position;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.control.UICloseHandle;
import net.malisis.core.client.gui.component.control.UIMoveHandle;
import net.malisis.core.client.gui.component.control.UIResizeHandle;
import net.malisis.core.client.gui.component.decoration.UIImage;
import net.malisis.core.client.gui.component.decoration.UILabel;
import net.malisis.core.client.gui.component.decoration.UITooltip;
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
	private UIPanel panel;
	private UITab tab1;
	private UIButton btn1, btn2, btnOver;
	private UICheckBox cb;

	public Gui(MalisisInventoryContainer inventoryContainer)
	{
		super();
		setInventoryContainer(inventoryContainer);

		UIWindow window = new UIWindow(this, 300, 240).setPosition(0, -40, Anchor.CENTER | Anchor.MIDDLE);
		window.clipContent = false;
		panel = new UIPanel(this, UIComponent.INHERITED, 140);
		UIContainer tabCont1 = new UIContainer(this)/*.setVerticalScroll(true)*/;
		UIContainer tabCont2 = new UIContainer(this)/*.setVerticalScroll(true)*/;
		//		panel.setBackgroundColor(0xFFDDEE);
		//		panel2.setBackgroundColor(0xCCCCFF);

		UITabGroup tabGroup = new UITabGroup(this, Position.TOP);
		UIImage img = new UIImage(this, new ItemStack(Items.nether_star));
		UIImage img2 = new UIImage(this, new ItemStack(Blocks.gold_ore));

		tab1 = new UITab(this, "Tab 1");
		UITab tab2 = new UITab(this, "Tab 2");

		tab1.setColor(0xFFDDEE);
		tab2.setColor(0xCCCCFF);;

		tabGroup.addTab(tab1, tabCont1);
		tabGroup.addTab(tab2, tabCont2);

		tabGroup.setActiveTab(tab1);

		/**
		 * WIDOW TAB EXAMPLE
		 */
		//			tabGroup.attachTo(window, false);
		//			addToScreen(tabGroup);

		/**
		 * PANEL TAB EXAMPLE
		 */
		tabGroup.attachTo(panel, true);
		window.add(tabGroup);

		cb = new UICheckBox(this, "CheckBox with label").setTooltip(new UITooltip(this, EnumChatFormatting.AQUA + "with a tooltip!"));

		UIRadioButton rb1 = new UIRadioButton(this, "newRb", "Radio value 1").setPosition(0, 14);
		UIRadioButton rb2 = new UIRadioButton(this, "newRb", "Radio value 2").setPosition(rb1.getWidth() + 10, 14);

		UISlider slider = new UISlider(this, 150, 0, 100, "Slider value : %.0f").setPosition(0, 26).register(this);

		UITextField tf = new UITextField(this, 200, "This textfield will only accept numbers.");
		tf.setPosition(0, 52);
		tf.setAutoSelectOnFocus(true);

		UIContainer inv = setInventoryContainer(inventoryContainer.getInventory(1));

		UISelect select = new UISelect(this, 100, UISelect.Option.fromList(Arrays.asList("Option 1", "Option 2",
				"Very ultra longer option 3", "Shorty", "Moar options", "Even more", "Even Steven", "And a potato too")));
		select.setPosition(0, 70);
		select.maxExpandedWidth(120);
		//select.maxDisplayedOptions(5);
		select.select(2);

		btn1 = new UIButton(this, "Horizontal", 80).setPosition(0, 80, Anchor.CENTER).register(this);
		btn2 = new UIButton(this, "Vertical", 80).setPosition(0, 120, Anchor.CENTER).register(this);
		btnOver = new UIButton(this, "Over the top").setPosition(0, 170, Anchor.CENTER);

		new UIResizeHandle(this, window);
		new UIMoveHandle(this, window);
		new UICloseHandle(this, window)
		{
			@Override
			public void onClose()
			{
				MalisisGui.currentGui().close();
			}
		};

		tabCont1.add(cb);
		tabCont1.add(rb1);
		tabCont1.add(rb2);
		tabCont1.add(slider);

		tabCont1.add(tf);
		tabCont1.add(select);

		tabCont1.add(btn1);
		tabCont1.add(btn2);
		tabCont1.add(btnOver);

		tabCont2.add(inv);
		//tabCont2.add(new UIImage(this, Items.diamond_axe.getIconFromDamage(0), UIUIImage.ITEMS_TEXTURE).setPosition(0, 10));
		tabCont2.add(new UILabel(this, "This is LABEL!").setPosition(20, 15));

		panel.add(tabCont1);
		panel.add(tabCont2);

		UIPlayerInventory playerInv = new UIPlayerInventory(this, inventoryContainer.getPlayerInventory());

		new UIResizeHandle(this, playerInv);
		new UIMoveHandle(this, playerInv);
		new UICloseHandle(this, playerInv)
		{
			@Override
			public void onClose()
			{
				getParent().setVisible(false);
			}
		};

		window.add(panel);

		window.add(playerInv);

		addToScreen(window);
	}

	private UIContainer setInventoryContainer(MalisisInventory inventory)
	{
		UIContainer c = new UIContainer(this, 100, 30);
		c.setPosition(0, 50);

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			UISlot slot = new UISlot(this, inventory.getSlot(i)).setPosition(i * 18, 0);
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
		tab1.setColor(r << 16 | g << 8 | b);
	}
}