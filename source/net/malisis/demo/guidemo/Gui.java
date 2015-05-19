package net.malisis.demo.guidemo;

import java.util.Arrays;

import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.ComponentPosition;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPanel;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.container.UITabGroup;
import net.malisis.core.client.gui.component.container.UIWindow;
import net.malisis.core.client.gui.component.control.UICloseHandle;
import net.malisis.core.client.gui.component.control.UIMoveHandle;
import net.malisis.core.client.gui.component.control.UIResizeHandle;
import net.malisis.core.client.gui.component.control.UIScrollBar;
import net.malisis.core.client.gui.component.control.UISlimScrollbar;
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
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.malisis.core.renderer.font.FontRenderOptions;
import net.malisis.core.renderer.font.MalisisFont;
import net.malisis.core.util.bbcode.gui.BBCodeEditor.BBCodeChangeEvent;
import net.malisis.demo.MalisisDemos;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import com.google.common.eventbus.Subscribe;

public class Gui extends MalisisGui
{
	private MalisisFont fontMC = MalisisFont.minecraftFont;
	private MalisisFont fontBS;
	private MalisisFont fontH;
	private UIPanel panel;
	private UITab tab1;
	private UIButton btnL, btnR, btnHorizontal;
	private UIRadioButton rbMC, rbBS, rbH;
	private UICheckBox cb;
	//	private BBCodeEditor bbEditor;
	private UILabel bbLabel;
	private UILabel bbLabel2;

	public Gui(MalisisInventoryContainer inventoryContainer)
	{
		setInventoryContainer(inventoryContainer);
		guiscreenBackground = false;

		ResourceLocation rl1 = new ResourceLocation(MalisisDemos.modid + ":fonts/BrushScriptStd.otf");
		ResourceLocation rl2 = new ResourceLocation(MalisisDemos.modid + ":fonts/HoboStd.otf");
		fontBS = new MalisisFont(rl1);
		fontH = new MalisisFont(rl2);
	}

	@Override
	public void construct()
	{
		UIWindow window = new UIWindow(this, 320, 240).setPosition(0, -20, Anchor.CENTER | Anchor.MIDDLE).setZIndex(0);

		//		UIContainer tabCont3 = new UIContainer(this, "BBEditor");
		//
		//		bbEditor = new BBCodeEditor(this).setSize(0, 100).setPosition(0, 0);
		//		bbEditor.setMenuPosition(ComponentPosition.TOP);
		//		bbEditor.setButtonAnchor(Anchor.LEFT);
		//
		//		bbLabel = new UILabel(this, true);
		//		bbLabel.setSize(150, 90).setPosition(0, 105);
		//
		//		new UISlimScrollbar(this, bbLabel, UIScrollBar.Type.VERTICAL);
		//
		//		bbLabel2 = new UILabel(this, true);
		//		bbLabel2.setSize(150, 90).setPosition(0, 105, Anchor.RIGHT).setFontScale(2F / 3F);
		//		new UISlimScrollbar(this, bbLabel2, UIScrollBar.Type.VERTICAL);
		//
		//		tabCont3.add(bbEditor);
		//		tabCont3.add(bbLabel);
		//		tabCont3.add(bbLabel2);

		UIContainer tabCont1 = panel1();
		UIContainer tabCont2 = panel2();

		panel = new UIPanel(this, UIComponent.INHERITED, 140);
		panel.add(tabCont1);
		panel.add(tabCont2);

		tab1 = new UITab(this, "Tab 1");
		UITab tab2 = new UITab(this, "Tab 2");
		tab2.setBgColor(0xCCCCFF);

		UITabGroup tabGroup = new UITabGroup(this, ComponentPosition.TOP);
		tabGroup.addTab(tab1, tabCont1);
		tabGroup.addTab(tab2, tabCont2);
		//tabGroup.addTab(tab3, tabCont3);

		tabGroup.setActiveTab(tab1);
		tabGroup.setSpacing(3);
		tabGroup.attachTo(panel, true);

		window.add(tabGroup);
		window.add(panel);

		UIPlayerInventory playerInv = new UIPlayerInventory(this, inventoryContainer.getPlayerInventory());
		new UICloseHandle(this, playerInv);
		window.add(playerInv);

		//window.add(debug());

		//		String str = EnumChatFormatting.ITALIC + "Testi" + EnumChatFormatting.GOLD + "ng another " + EnumChatFormatting.RED + " color"
		//				+ EnumChatFormatting.RESET + " and reset";
		//		UILabel label = new UILabel(this, str);
		//		label.setColor(0x339966);
		//		label.setDrawShadow(true);
		//		window.add(label);

		//		window.add(bbEditor);
		//		window.add(bbLabel);
		//		window.add(bbLabel2);

		new UIMoveHandle(this, window);
		new UIResizeHandle(this, window);
		new UICloseHandle(this, window);

		/**
		 * UIBackgroundContainer
		 */
		//		UIBackgroundContainer bgc = new UIBackgroundContainer(this, "Container Background", 0, 60);
		//
		//		bgc.setTopLeftColor(0x993333);
		//		bgc.setTopRightColor(0x3333FF);
		//		bgc.setBottomRightColor(0x993333);
		//		bgc.setBottomLeftColor(0x3333FF);
		//		bgc.setBottomAlpha(0);

		//addToScreen(bgc);
		addToScreen(window);

	}

	private UIContainer panel1()
	{
		cb = new UICheckBox(this, "CheckBox with label").setTooltip(new UITooltip(this, EnumChatFormatting.AQUA + "with a tooltip!", 5));

		rbMC = new UIRadioButton(this, "newRb", "Minecraft font").setPosition(0, 14).setSelected().register(this);
		rbMC.setFont(fontMC, null);
		rbBS = new UIRadioButton(this, "newRb", "Brush Script").setPosition(rbMC.getWidth() + 10, 14).register(this);
		rbBS.setFont(fontBS, null);
		rbH = new UIRadioButton(this, "newRb", "Hobo").setPosition(rbMC.getWidth() + rbBS.getWidth() + 20, 14).register(this);
		rbH.setFont(fontH, null);

		UISlider slider = new UISlider(this, 150, 0, 100, "Slider value : %.0f").setPosition(0, 26).register(this);

		UITextField tf = new UITextField(this, "This textfield will only accept numbers.");
		tf.setSize(200, 0);
		tf.setPosition(0, 52);
		tf.setAutoSelectOnFocus(true);
		//tf.setOptions(0x660000, 0xFFCCCC, 0x770000, 0xFF0000, false);

		UISelect select = new UISelect(this, 100, Arrays.asList("Option 1", "Option 2", "Very ultra longer option 3", "Shorty",
				"Moar options", "Even more", "Even Steven", "And a potato too"));
		select.setPosition(0, 70);
		select.setMaxExpandedWidth(120);
		//select.maxDisplayedOptions(5);
		select.select(2);
		//select.setColors(0x660000, 0xFFCCCC, 0xFF0000, 0x999999, 0x6600CC, 0x664444, false);

		btnHorizontal = new UIButton(this, "Horizontal").setSize(90).setPosition(0, 85, Anchor.CENTER);
		//btnHorizontal.setOptions(0x660000, 0xFF0000, 0xFFCCCC, false);
		btnL = new UIButton(this, "O").setPosition(-49, 85, Anchor.CENTER).setAutoSize(false).setSize(10, 10);
		//btnL.setOptions(0x660000, 0xFF0000, 0xFFCCCC, false);
		btnR = new UIButton(this, "O").setPosition(50, 85, Anchor.CENTER).setAutoSize(false).setSize(10, 10);
		//btnR.setOptions(0x660000, 0xFF0000, 0xFFCCCC, false);

		btnL.setOffset(0, 1);
		btnR.setOffset(1, 0);

		UIContainer tabCont1 = new UIContainer(this);
		tabCont1.add(cb);
		tabCont1.add(rbMC);
		tabCont1.add(rbBS);
		tabCont1.add(rbH);
		tabCont1.add(slider);

		tabCont1.add(tf);
		tabCont1.add(select);

		tabCont1.add(btnHorizontal);
		tabCont1.add(btnL);
		tabCont1.add(btnR);

		int i = 0;
		for (Item item : new Item[] { Items.cooked_porkchop, Items.cooked_beef, Items.cooked_chicken, Items.baked_potato,
				Item.getItemFromBlock(Blocks.glass_pane) })
		{
			UIImage img = new UIImage(this, new ItemStack(item));
			UIButton btnImage = new UIButton(this, img).setPosition(0, i++ * 19, Anchor.RIGHT);
			tabCont1.add(btnImage);
		}

		return tabCont1;
	}

	private UIContainer panel2()
	{
		UILabel label1 = new UILabel(this, EnumChatFormatting.UNDERLINE.toString() + EnumChatFormatting.YELLOW + "Colored label!")
				.setPosition(20, 30);
		FontRenderOptions fro = new FontRenderOptions();
		fro.color = 0x660066;
		fro.fontScale = 2 / 3F;
		UILabel label2 = new UILabel(this, "Smaller label!").setPosition(20, 40).setFont(null, fro);
		//label2.setFontScale(2F / 3F);

		UIContainer tabCont2 = new UIContainer(this);

		MalisisInventory inv = inventoryContainer.getInventory(1);
		int i = 0;
		for (MalisisSlot slot : inv.getSlots())
		{
			tabCont2.add(new UISlot(this, slot).setPosition(i * 18, 0).setTooltip("Slot number " + (i + 1)));
			i++;
		}

		fro = new FontRenderOptions();
		fro.fontScale = 2 / 3F;

		UITextField mltf = new UITextField(this, true);
		mltf.setSize(125, 50);
		mltf.setPosition(0, 55);
		//mltf.setFontScale(2 / 3F);
		//mltf.setEditable(false);
		mltf.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras quis semper mi. Pellentesque dapibus diam egestas orci vulputate, a tempor ex hendrerit. Nullam tristique lacinia quam, a dapibus leo gravida eu. Donec placerat, turpis ut egestas dignissim, sem nibh tincidunt neque, eu facilisis massa felis eu nisl. Aenean pellentesque sed nunc et ultrices. Aenean facilisis convallis mauris in mollis. In porta hendrerit tellus id vehicula. Sed non interdum eros, vel condimentum diam. Sed vestibulum tincidunt velit, ac laoreet metus blandit quis. Aliquam sit amet ullamcorper velit. In tristique viverra imperdiet. Mauris facilisis ac leo non molestie.\r\n"
				+ "\r\n"
				+ "Phasellus orci metus, bibendum in molestie eu, interdum lacinia nulla. Nulla facilisi. Duis sagittis suscipit est vitae eleifend. Morbi bibendum tortor nec tincidunt pharetra. Vivamus tortor tortor, egestas sed condimentum ac, tristique non risus. Curabitur magna metus, porta sit amet dictum in, vulputate a dolor. Phasellus viverra euismod tortor, porta ultrices metus imperdiet a. Nulla pellentesque ipsum quis eleifend blandit. Aenean neque nulla, rhoncus et vestibulum eu, feugiat quis erat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse lacus justo, porttitor aliquam tellus eu, commodo tristique leo. Suspendisse scelerisque blandit nisl at malesuada. Proin ut tincidunt augue. Phasellus vel nisl sapien.\r\n"
				+ "\r"
				+ "Sed ut lacinia tellus. Nam arcu ligula, accumsan id lorem id, dapibus bibendum tortor. Cras eleifend varius est, eget eleifend est commodo at. Vivamus sapien purus, faucibus ac urna id, scelerisque sagittis elit. Curabitur commodo elit nec diam vulputate finibus vitae porttitor magna. Nullam nec feugiat dolor. Pellentesque malesuada dolor arcu, ut sagittis mi mattis eu. Vivamus et tortor non nulla venenatis hendrerit nec faucibus quam. Aliquam laoreet leo in risus tempus placerat. In lobortis nulla id enim semper posuere a et libero. Nullam sit amet sapien commodo, egestas nisi eu, viverra nulla. Cras ac vulputate tellus, nec auctor elit.\r\n"
				+ "\n"
				+ "In commodo finibus urna, eu consectetur quam commodo dapibus. Pellentesque metus ligula, ullamcorper non lorem a, dapibus elementum quam. Praesent iaculis pellentesque dui eget pellentesque. Nunc vel varius dui. Aliquam sit amet ex feugiat, aliquet ipsum nec, sollicitudin dolor. Ut ac rhoncus enim. Quisque maximus diam nec neque placerat, euismod blandit purus congue. Integer finibus tellus ligula, eget pretium magna luctus vel. Pellentesque gravida pretium nisl sit amet fermentum. Quisque odio nunc, tristique vitae pretium ut, imperdiet a nunc. Sed eu purus ultricies, tincidunt sapien et, condimentum nunc. Duis luctus augue ac congue luctus. Integer ut commodo turpis, vitae hendrerit quam. Vivamus vulputate efficitur est nec dignissim. Praesent convallis posuere lacus ut suscipit. Aliquam at odio viverra, cursus nulla eget, maximus purus.\r\n"
				+ "\r\n"
				+ "Donec convallis tortor in pretium hendrerit. Maecenas mollis ullamcorper sapien, rhoncus pretium nibh condimentum ut. Phasellus tincidunt aliquet ligula in blandit. Nunc ornare vel ligula eu vulputate. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Suspendisse vitae ultricies nunc. Morbi lorem purus, tempor eget magna at, placerat posuere massa. Donec hendrerit risus a pharetra bibendum. ");
		//mltf.setText("Line 1\n" + "Line 2\r\n" + "Line 3\r" + "Line 4");
		mltf.setFont(null, fro);
		mltf.getScrollbar().setAutoHide(true);

		UILabel ipsum = new UILabel(this, true);
		ipsum.setPosition(0, 0, Anchor.RIGHT);
		ipsum.setSize(150, 0);
		ipsum.setText("Contrairement à une opinion répandue, "
				+ EnumChatFormatting.DARK_GREEN
				+ "le Lorem Ipsum n'est pas simplement du texte aléatoire"
				+ EnumChatFormatting.RESET
				+ ". Il trouve ses racines dans une oeuvre de la littérature latine classique"
				+ EnumChatFormatting.AQUA
				+ " datant de 45 av. J.-C., le rendant"
				+ EnumChatFormatting.RESET
				+ " vieux de 2000 ans."
				+ EnumChatFormatting.BLUE
				+ "Un professeur du "
				+ EnumChatFormatting.ITALIC
				+ "Hampden-Sydney College"
				+ EnumChatFormatting.BLUE
				+ ", en Virginie, s'est intéressé"
				+ EnumChatFormatting.RESET
				+ " à un des mots latins les plus obscurs, consectetur, extrait d'un passage du Lorem Ipsum, et en étudiant tous les usages de ce mot dans la littérature classique, découvrit la source incontestable du Lorem Ipsum. Il provient en fait des sections 1.10.32 et 1.10.33 du \"De Finibus Bonorum et Malorum\" (Des Suprêmes Biens et des Suprêmes Maux) de Cicéron. Cet ouvrage, très populaire pendant la Renaissance, est un traité sur la théorie de l'éthique. Les premières lignes du Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", proviennent de la section 1.10.32");

		ipsum.setFont(null, fro);
		new UISlimScrollbar(this, ipsum, UIScrollBar.Type.VERTICAL);

		tabCont2.add(new UIImage(this, MalisisGui.ITEM_TEXTURE, Items.diamond_axe.getIconFromDamage(0)).setPosition(0, 25));
		tabCont2.add(label1);
		tabCont2.add(label2);
		tabCont2.add(mltf);

		tabCont2.add(ipsum);

		return tabCont2;
	}

	private UIComponent debug()
	{
		FontRenderOptions fro = new FontRenderOptions();
		fro.fontScale = 2 / 3f;

		UILabel ipsum = new UILabel(this, true);
		ipsum.setPosition(0, 0, Anchor.CENTER);
		ipsum.setSize(150, 0);
		ipsum.setText("Contrairement à une opinion répandue, "
				+ EnumChatFormatting.DARK_GREEN
				+ "le Lorem Ipsum n'est pas simplement du texte aléatoire"
				+ EnumChatFormatting.RESET
				+ ". Il trouve ses racines dans une oeuvre de la littérature latine classique"
				+ EnumChatFormatting.AQUA
				+ " datant de 45 av. J.-C., le rendant"
				+ EnumChatFormatting.RESET
				+ " vieux de 2000 ans."
				+ EnumChatFormatting.BLUE
				+ "Un professeur du "
				+ EnumChatFormatting.ITALIC
				+ "Hampden-Sydney College"
				+ EnumChatFormatting.BLUE
				+ ", en Virginie, s'est intéressé"
				+ EnumChatFormatting.RESET
				+ " à un des mots latins les plus obscurs, consectetur, extrait d'un passage du Lorem Ipsum, et en étudiant tous les usages de ce mot dans la littérature classique, découvrit la source incontestable du Lorem Ipsum. Il provient en fait des sections 1.10.32 et 1.10.33 du \"De Finibus Bonorum et Malorum\" (Des Suprêmes Biens et des Suprêmes Maux) de Cicéron. Cet ouvrage, très populaire pendant la Renaissance, est un traité sur la théorie de l'éthique. Les premières lignes du Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", proviennent de la section 1.10.32");

		ipsum.setFont(null, fro);
		new UISlimScrollbar(this, ipsum, UIScrollBar.Type.VERTICAL);

		return ipsum;
	}

	@Subscribe
	public void onBBCodeChanged(BBCodeChangeEvent event)
	{
		bbLabel.setText(event.getComponent().getBBText());
		bbLabel2.setText(event.getComponent().getBBFormattedTex());
	}

	@Subscribe
	public void onSliderChanged(ValueChange<UIComponent, Float> event)
	{
		if (!(event.getComponent() instanceof UISlider))
			return;

		int v = (int) (event.getNewValue() / 100 * 255);
		int g = Math.abs(-255 + 2 * v);
		int r = v;
		int b = 255 - g;
		//MalisisCore.message(r + " > " + Integer.toHexString(r << 16 | 0x00FFFF));
		tab1.setBgColor(r << 16 | g << 8 | b);
	}

	@Subscribe
	public void onSelect(UIRadioButton.SelectEvent event)
	{
		if (event.getComponent() == rbMC)
			renderer.setDefaultFont(fontMC, getFontRenderOptions());
		else if (event.getComponent() == rbBS)
			renderer.setDefaultFont(fontBS, getFontRenderOptions());
		else if (event.getComponent() == rbH)
			renderer.setDefaultFont(fontH, getFontRenderOptions());
	}
}