package net.malisis.demo.guidemo;

import java.util.Arrays;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIInventory;
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
import net.malisis.core.client.gui.event.ComponentEvent.ValueChange;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.minecraft.init.Items;
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
		setInventoryContainer(inventoryContainer);

		/**
		 * CONTAINER 1
		 */
		cb = new UICheckBox(this, "CheckBox with label").setTooltip(new UITooltip(this, EnumChatFormatting.AQUA + "with a tooltip!"));

		UIRadioButton rb1 = new UIRadioButton(this, "newRb", "Radio value 1").setPosition(0, 14);
		UIRadioButton rb2 = new UIRadioButton(this, "newRb", "Radio value 2").setPosition(rb1.getWidth() + 10, 14);

		UISlider slider = new UISlider(this, 150, 0, 100, "Slider value : %.0f").setPosition(0, 26).register(this);

		UITextField tf = new UITextField(this, 200, "This textfield will only accept numbers.");
		tf.setPosition(0, 52);
		tf.setAutoSelectOnFocus(true);

		UISelect select = new UISelect(this, 100, UISelect.Option.fromList(Arrays.asList("Option 1", "Option 2",
				"Very ultra longer option 3", "Shorty", "Moar options", "Even more", "Even Steven", "And a potato too")));
		select.setPosition(0, 70);
		select.maxExpandedWidth(120);
		//select.maxDisplayedOptions(5);
		select.select(2);

		btn1 = new UIButton(this, "Horizontal", 90).setPosition(0, 85, Anchor.CENTER);
		btn2 = new UIButton(this, "<").setPosition(-50, 85, Anchor.CENTER).setSize(10, 10);
		btnOver = new UIButton(this, ">").setPosition(50, 85, Anchor.CENTER).setSize(10, 10);

		UIContainer tabCont1 = new UIContainer(this);
		tabCont1.add(cb);
		tabCont1.add(rb1);
		tabCont1.add(rb2);
		tabCont1.add(slider);

		tabCont1.add(tf);
		tabCont1.add(select);

		tabCont1.add(btn1);
		tabCont1.add(btn2);
		tabCont1.add(btnOver);

		/**
		 * CONTAINER 2
		 */
		UIContainer inv = new UIInventory(this, inventoryContainer.getInventory(1));

		UIContainer tabCont2 = new UIContainer(this);

		tabCont2.add(inv);
		tabCont2.add(new UIImage(this, MalisisGui.ITEM_TEXTURE, Items.diamond_axe.getIconFromDamage(0)).setPosition(0, 25));
		tabCont2.add(new UILabel(this, "This is LABEL!" + EnumChatFormatting.DARK_RED + " Colored!").setPosition(20, 30));

		/**
		 * PANEL
		 */
		panel = new UIPanel(this, UIComponent.INHERITED, 140);
		panel.add(tabCont1);
		panel.add(tabCont2);

		/**
		 * TAB GROUP
		 */
		UITabGroup tabGroup = new UITabGroup(this, Position.TOP);
		//		UIImage img = new UIImage(this, new ItemStack(Items.nether_star));
		//		UIImage img2 = new UIImage(this, new ItemStack(Blocks.gold_ore));

		tab1 = new UITab(this, "Tab 1");
		UITab tab2 = new UITab(this, "Tab 2");

		tab1.setColor(0xFFDDEE);
		tab2.setColor(0xCCCCFF);;

		tabGroup.addTab(tab1, tabCont1);
		tabGroup.addTab(tab2, tabCont2);

		tabGroup.setActiveTab(tab1);
		tabGroup.attachTo(panel, true);

		/**
		 * WINDOW
		 */
		UIPlayerInventory playerInv = new UIPlayerInventory(this, inventoryContainer.getPlayerInventory());
		new UICloseHandle(this, playerInv);

		UIWindow window = new UIWindow(this, 300, 240).setPosition(0, -40, Anchor.CENTER | Anchor.MIDDLE);
		window.add(tabGroup);
		window.add(panel);
		window.add(playerInv);

		new UIMoveHandle(this, window);
		new UIResizeHandle(this, window);
		new UICloseHandle(this, window);

		addToScreen(window);
	}

	@Subscribe
	public void onSliderChanged(ValueChange<UISlider, Float> event)
	{
		int v = (int) (event.getNewValue() / 100 * 255);
		int g = Math.abs(-255 + 2 * v);
		int r = v;
		int b = 255 - g;
		//MalisisCore.message(r + " > " + Integer.toHexString(r << 16 | 0x00FFFF));
		tab1.setColor(r << 16 | g << 8 | b);
	}
}