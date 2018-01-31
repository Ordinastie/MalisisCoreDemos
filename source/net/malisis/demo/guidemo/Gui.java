package net.malisis.demo.guidemo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.base.Converter;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;

import net.malisis.core.MalisisCore;
import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.ComponentPosition;
import net.malisis.core.client.gui.GuiRenderer;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UIComponent;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIBackgroundContainer;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIListContainer;
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
import net.malisis.core.client.gui.component.decoration.UIProgressBar;
import net.malisis.core.client.gui.component.decoration.UITooltip;
import net.malisis.core.client.gui.component.interaction.UIButton;
import net.malisis.core.client.gui.component.interaction.UIButton.ClickEvent;
import net.malisis.core.client.gui.component.interaction.UICheckBox;
import net.malisis.core.client.gui.component.interaction.UIRadioButton;
import net.malisis.core.client.gui.component.interaction.UISelect;
import net.malisis.core.client.gui.component.interaction.UISlider;
import net.malisis.core.client.gui.component.interaction.UITab;
import net.malisis.core.client.gui.component.interaction.UITextField;
import net.malisis.core.client.gui.element.SimpleGuiShape;
import net.malisis.core.client.gui.event.ComponentEvent.ValueChange;
import net.malisis.core.inventory.MalisisInventory;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.inventory.MalisisSlot;
import net.malisis.core.renderer.font.FontOptions;
import net.malisis.core.renderer.icon.Icon;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/**
 * This class show most of the available components for GUI and how to use them.<br>
 */
public class Gui extends MalisisGui
{
	//private static MalisisFont fontMC = MalisisFont.minecraftFont;
	//private static MalisisFont fontBS = new MalisisFont(new ResourceLocation(MalisisDemos.modid + ":fonts/BrushScriptStd.otf"));
	//private static MalisisFont fontH = new MalisisFont(new ResourceLocation(MalisisDemos.modid + ":fonts/HoboStd.otf"));
	private UIPanel panel;
	private UITab tabSlider;
	private UIProgressBar bar;
	private UIButton btnL, btnR, btnHorizontal;
	private UIRadioButton rbMC, rbBS, rbH;
	private UICheckBox cb;
	private UISelect<String> select;
	private int selectSize = 100;
	private int currentSize = 100;

	public Gui(MalisisInventoryContainer inventoryContainer)
	{
		setInventoryContainer(inventoryContainer);
		guiscreenBackground = false;
	}

	@Override
	public void construct()
	{
		UIWindow window = new UIWindow(this, 320, 240).setPosition(0, -20, Anchor.CENTER | Anchor.MIDDLE).setZIndex(0);
		//allow contents to be drawn outside of the window's borders
		window.setClipContent(false);

		boolean debug = false;
		if (debug)
		{
			addToScreen(debug());
			return;
		}

		//get the first container
		UIContainer<?> tabCont1 = panel1();
		//get the second container
		UIContainer<?> tabCont2 = panel2();
		//get the slider demo tab
		UIContainer<?> tabSliderPanel = sliderPanel();
		//get the UIList panel
		UIContainer<?> tabList = listPanel();

		//create a panel to hold the containers
		panel = new UIPanel(this, UIComponent.INHERITED, 140);
		panel.add(tabCont1);
		panel.add(tabCont2);
		panel.add(tabSliderPanel);
		panel.add(tabList);

		//create the tabs for the containers
		UITab tab1 = new UITab(this, "Tab 1");
		UITab tab2 = new UITab(this, "Tab 2").setBgColor(0xCCCCFF);
		tabSlider = new UITab(this, "Slider tab");
		UITab tab3 = new UITab(this, "UIList");

		//create the group containing the tabs and their corresponding containers
		UITabGroup tabGroup = new UITabGroup(this, ComponentPosition.TOP);
		tabGroup.addTab(tab1, tabCont1);
		tabGroup.addTab(tab2, tabCont2);
		tabGroup.addTab(tabSlider, tabSliderPanel);
		tabGroup.addTab(tab3, tabList);

		tabGroup.setActiveTab(tab2);
		tabGroup.setSpacing(0);
		tabGroup.attachTo(panel, true);

		//add all the elements to the window
		window.add(tabGroup);
		window.add(panel);

		//add the player inventory (default position is bottom center)
		UIPlayerInventory playerInv = new UIPlayerInventory(this, inventoryContainer.getPlayerInventory());
		window.add(playerInv);
		//add a close icon for the player inventory
		//Note : it won't actively remove the player inventory from the container, it will just stop displaying it
		new UICloseHandle(this, playerInv);

		//add handles to the window
		new UIMoveHandle(this, window);
		new UIResizeHandle(this, window);
		new UICloseHandle(this, window);

		//add the window to the screen
		addToScreen(window);

	}

	private UIContainer<?> panel1()
	{
		//CheckBox
		cb = new UICheckBox(this,
							"CheckBox with label").setTooltip(new UITooltip(this, TextFormatting.AQUA + "with delayed a tooltip!", 5));

		//RadioButton with custom fonts
		rbMC = new UIRadioButton(this, "newRb", "Minecraft font").setPosition(0, 14).setSelected();
		//rbMC.setFont(fontMC);
		rbBS = new UIRadioButton(this, "newRb", "Brush Script").setPosition(rbMC.getWidth() + 10, 14);
		//rbBS.setFont(fontBS);
		rbH = new UIRadioButton(this, "newRb", "Hobo").setPosition(rbMC.getWidth() + rbBS.getWidth() + 20, 14);
		//rbH.setFont(fontH);

		//Textfield
		UITextField tf = new UITextField(this, "This is a textfield. You can type in it.");
		tf.setSize(200, 0);
		tf.setPosition(0, 52);
		tf.setAutoSelectOnFocus(true);
		new UIResizeHandle(this, tf);
		//tf.setOptions(0x660000, 0xFFCCCC, 0x770000, 0xFF0000, false);

		//Select
		select = new UISelect<>(this,
								100,
								Arrays.asList(	"Option 1",
												"Option 2",
												"Very ultra longer option 3",
												"Shorty",
												"Moar options",
												"Even more",
												"Even Steven",
												"And a potato too"));
		select.setPosition(0, 70);
		select.setOptionsWidth(UISelect.SELECT_WIDTH);
		//select.maxDisplayedOptions(5);
		select.select("Option 2");
		//select.setColors(0x660000, 0xFFCCCC, 0xFF0000, 0x999999, 0x6600CC, 0x664444, false);

		//uiselect size change button
		UIButton selectSizeButton = new UIButton(this, "<-").setPosition(200, 70).setSize(20, 12).register(this);

		//progress bar
		bar = new UIProgressBar(this, 16, 16, BLOCK_TEXTURE, Icon.from(Items.IRON_PICKAXE), Icon.from(Items.DIAMOND_PICKAXE));
		bar.setPosition(-30, 40, Anchor.RIGHT);
		bar.setVertical();

		//3 Buttons
		btnHorizontal = new UIButton(this, "Horizontal").setSize(90).setPosition(0, 85, Anchor.CENTER);
		btnHorizontal.setEnabled(true);
		btnHorizontal.setTooltip("Button horizontal");
		btnL = new UIButton(this, "O").setPosition(-49, 85, Anchor.CENTER).setAutoSize(false).setSize(10, 10);
		btnR = new UIButton(this, "O").setPosition(50, 85, Anchor.CENTER).setAutoSize(false).setSize(10, 10);

		btnL.setOffset(0, 1);
		btnR.setOffset(1, 0);

		//Add all elements
		UIContainer<?> tabCont1 = new UIContainer<>(this);
		tabCont1.add(cb);
		tabCont1.add(rbMC);
		tabCont1.add(rbBS);
		tabCont1.add(rbH);

		tabCont1.add(tf);
		tabCont1.add(select);
		tabCont1.add(selectSizeButton);
		tabCont1.add(bar);

		tabCont1.add(btnHorizontal);
		tabCont1.add(btnL);
		tabCont1.add(btnR);

		//Create 5 buttons with itemStack as images
		int i = 0;
		for (Item item : new Item[] {	Items.COOKED_PORKCHOP,
										Items.COOKED_BEEF,
										Items.COOKED_MUTTON,
										Items.COOKED_CHICKEN,
										Item.getItemFromBlock(Blocks.GLASS_PANE) })
		{
			UIImage img = new UIImage(this, new ItemStack(item));
			UIButton btnImage = new UIButton(this, img).setPosition(0, i++ * 19, Anchor.RIGHT);
			tabCont1.add(btnImage);
		}

		return tabCont1;
	}

	private UIContainer<?> panel2()
	{
		UIImage img = new UIImage(this, MalisisGui.BLOCK_TEXTURE, Icon.from(Items.DIAMOND_HORSE_ARMOR)).setPosition(0, 0);
		//Colored Label
		UILabel label1 = new UILabel(this, TextFormatting.UNDERLINE.toString() + TextFormatting.YELLOW + "Colored label!").setPosition(	20,
																																		0);

		//Smaller label with FontRenderOptions
		FontOptions fro = FontOptions.builder().scale(2F / 3F).color(0x660066).build();
		UILabel label2 = new UILabel(this, "Smaller label!").setPosition(20, 10).setFontOptions(fro);
		//label2.setFontScale(2F / 3F);

		//Multiline Textfield with FontRendererOptions
		fro = FontOptions.builder().scale(2F / 3F).color(0x006600).build();

		UITextField mltf = new UITextField(this, true);
		mltf.setSize(125, 50);
		mltf.setPosition(0, 25);
		mltf.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras quis semper mi. Pellentesque dapibus diam egestas orci vulputate, a tempor ex hendrerit. Nullam tristique lacinia quam, a dapibus leo gravida eu. Donec placerat, turpis ut egestas dignissim, sem nibh tincidunt neque, eu facilisis massa felis eu nisl. Aenean pellentesque sed nunc et ultrices. Aenean facilisis convallis mauris in mollis. In porta hendrerit tellus id vehicula. Sed non interdum eros, vel condimentum diam. Sed vestibulum tincidunt velit, ac laoreet metus blandit quis. Aliquam sit amet ullamcorper velit. In tristique viverra imperdiet. Mauris facilisis ac leo non molestie.\r\n"
				+ "\r\n"
				+ "Phasellus orci metus, bibendum in molestie eu, interdum lacinia nulla. Nulla facilisi. Duis sagittis suscipit est vitae eleifend. Morbi bibendum tortor nec tincidunt pharetra. Vivamus tortor tortor, egestas sed condimentum ac, tristique non risus. Curabitur magna metus, porta sit amet dictum in, vulputate a dolor. Phasellus viverra euismod tortor, porta ultrices metus imperdiet a. Nulla pellentesque ipsum quis eleifend blandit. Aenean neque nulla, rhoncus et vestibulum eu, feugiat quis erat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse lacus justo, porttitor aliquam tellus eu, commodo tristique leo. Suspendisse scelerisque blandit nisl at malesuada. Proin ut tincidunt augue. Phasellus vel nisl sapien.\r\n"
				+ "\r"
				+ "Sed ut lacinia tellus. Nam arcu ligula, accumsan id lorem id, dapibus bibendum tortor. Cras eleifend varius est, eget eleifend est commodo at. Vivamus sapien purus, faucibus ac urna id, scelerisque sagittis elit. Curabitur commodo elit nec diam vulputate finibus vitae porttitor magna. Nullam nec feugiat dolor. Pellentesque malesuada dolor arcu, ut sagittis mi mattis eu. Vivamus et tortor non nulla venenatis hendrerit nec faucibus quam. Aliquam laoreet leo in risus tempus placerat. In lobortis nulla id enim semper posuere a et libero. Nullam sit amet sapien commodo, egestas nisi eu, viverra nulla. Cras ac vulputate tellus, nec auctor elit.\r\n"
				+ "\n"
				+ "In commodo finibus urna, eu consectetur quam commodo dapibus. Pellentesque metus ligula, ullamcorper non lorem a, dapibus elementum quam. Praesent iaculis pellentesque dui eget pellentesque. Nunc vel varius dui. Aliquam sit amet ex feugiat, aliquet ipsum nec, sollicitudin dolor. Ut ac rhoncus enim. Quisque maximus diam nec neque placerat, euismod blandit purus congue. Integer finibus tellus ligula, eget pretium magna luctus vel. Pellentesque gravida pretium nisl sit amet fermentum. Quisque odio nunc, tristique vitae pretium ut, imperdiet a nunc. Sed eu purus ultricies, tincidunt sapien et, condimentum nunc. Duis luctus augue ac congue luctus. Integer ut commodo turpis, vitae hendrerit quam. Vivamus vulputate efficitur est nec dignissim. Praesent convallis posuere lacus ut suscipit. Aliquam at odio viverra, cursus nulla eget, maximus purus.\r\n"
				+ "\r\n"
				+ "Donec convallis tortor in pretium hendrerit. Maecenas mollis ullamcorper sapien, rhoncus pretium nibh condimentum ut. Phasellus tincidunt aliquet ligula in blandit. Nunc ornare vel ligula eu vulputate. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Suspendisse vitae ultricies nunc. Morbi lorem purus, tempor eget magna at, placerat posuere massa. Donec hendrerit risus a pharetra bibendum. ");
		//mltf.setText("Some §5colored test");
		mltf.setFontOptions(fro);
		mltf.getScrollbar().setAutoHide(true);
		new UIResizeHandle(this, mltf);

		//Multiline label
		fro = FontOptions.builder().scale(2F / 3F).color(0x338899).build();
		UILabel ipsum = new UILabel(this, true);
		ipsum.setPosition(0, 0, Anchor.RIGHT);
		ipsum.setSize(150, 0);
		ipsum.setText(TextFormatting.UNDERLINE + "Contrairement à une opinion répandue, " + TextFormatting.BOLD
				+ "le Lorem Ipsum n'est pas simplement du texte aléatoire" + TextFormatting.RESET
				+ ". Il trouve ses racines dans une oeuvre de la littérature latine classique" + TextFormatting.AQUA
				+ " datant de 45 av. J.-C., le rendant" + TextFormatting.RESET + " vieux de 2000 ans." + TextFormatting.BLUE
				+ "Un professeur du " + TextFormatting.RESET + "Hampden-Sydney College" + TextFormatting.BLUE
				+ ", en Virginie, s'est intéressé" + TextFormatting.RESET + " à un des mots latins les plus obscurs, "
				+ TextFormatting.UNDERLINE + TextFormatting.DARK_RED + "consectetur" + TextFormatting.RESET
				+ ", extrait d'un passage du Lorem Ipsum, et en étudiant tous les usages de ce mot dans la littérature classique, découvrit la source incontestable du Lorem Ipsum. Il provient en fait des sections 1.10.32 et 1.10.33 du \"De Finibus Bonorum et Malorum\" (Des Suprêmes Biens et des Suprêmes Maux) de Cicéron. Cet ouvrage, très populaire pendant la Renaissance, est un traité sur la théorie de l'éthique. Les premières lignes du Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", proviennent de la section 1.10.32");
		ipsum.setFontOptions(fro);
		//ipsum.setFont(new MalisisFont(new ResourceLocation(MalisisDemos.modid + ":fonts/HoboStd.otf")));
		new UISlimScrollbar(this, ipsum, UIScrollBar.Type.VERTICAL);

		//Add all elements
		UIContainer<?> tabCont2 = new UIContainer<>(this);
		tabCont2.add(img);
		tabCont2.add(label1);
		tabCont2.add(label2);
		tabCont2.add(mltf);
		tabCont2.add(ipsum);

		//Add the block's inventory's slots directly into the container
		tabCont2.add(new UILabel(this, "Block's inventory").setPosition(12, 84));
		MalisisInventory inv = inventoryContainer.getInventory(0);
		int i = 0;
		for (MalisisSlot slot : inv.getSlots())
		{
			tabCont2.add(new UISlot(this, slot).setPosition(i * 18 + 10, 95).setTooltip("Slot number " + (i + 1)));
			i++;
		}

		return tabCont2;
	}

	UISlider<Integer> sliderRed;
	UISlider<Integer> sliderGreen;
	UISlider<Integer> sliderBlue;
	UILabel sliderColorLabel;

	private UIContainer<?> sliderPanel()
	{
		//Sliders with event caught to change the panel background color
		Converter<Float, Integer> colorConv = Converter.from(f -> (int) (f * 255), i -> (float) i / 255);
		sliderRed = new UISlider<>(this, 150, colorConv, "{slider.red} %d")	.setPosition(0, 0)
																			.setSize(150, 12)
																			.setValue(255)
																			.setScrollStep(1 / 255F)
																			.register(this);
		sliderGreen = new UISlider<>(this, 150, colorConv, "{slider.green} %d")	.setPosition(0, 21)
																				.setSize(150, 12)
																				.setValue(255)
																				.setScrollStep(1 / 255F)
																				.register(this);
		sliderBlue = new UISlider<>(this, 150, colorConv, "{slider.blue} %d")	.setPosition(0, 42)
																				.setSize(150, 12)
																				.setValue(255)
																				.setScrollStep(1 / 255F)
																				.register(this);
		sliderColorLabel = new UILabel(this, "Color : 0xFFFFFF").setPosition(160, 25);

		//Slider with custom values with days of the week
		Converter<Float, DayOfWeek> dayConv = Converter.from(f -> DayOfWeek.values()[Math.round(f * 6)], d -> (float) d.ordinal() / 6);
		UISlider<DayOfWeek> sliderDay = new UISlider<>(this, 70, dayConv, "%s")	.setPosition(0, 64, Anchor.CENTER)
																				.setSize(240, 30)
																				.setValue(LocalDate.now().getDayOfWeek())
																				.setScrollStep(1 / 6F);

		UIContainer<?> sliderPanel = new UIContainer<>(this);
		sliderPanel.add(sliderRed);
		sliderPanel.add(sliderGreen);
		sliderPanel.add(sliderBlue);
		sliderPanel.add(sliderColorLabel);
		sliderPanel.add(sliderDay);

		return sliderPanel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private UIContainer<?> listPanel()
	{

		List<Item> items = ImmutableList.of(Items.APPLE,
											Items.BED,
											Items.LEAD,
											Items.SNOWBALL,
											Items.ARROW,
											Items.WHEAT,
											Items.TNT_MINECART,
											Items.WATER_BUCKET,
											Items.COOKED_MUTTON);

		UIListContainer<Item> itemList = new UIListContainer<>(this);
		itemList.setComponentFactory((gui, item) -> {
			return new UIBackgroundContainer(gui)
			{
				{
					setSize(-20, 20);
					setBorder(0x6666DD, 1, 255);

					ItemStack is = new ItemStack(item);
					UIImage img = new UIImage(gui, is);
					img.setPosition(0, 0, Anchor.MIDDLE);
					add(img);

					UILabel label = new UILabel(gui, is.getUnlocalizedName() + ".name");
					label.setPosition(20, 0, Anchor.MIDDLE);
					add(label);

					UIButton btn1 = new UIButton(gui, "1");
					btn1.setPosition(-1, 1, Anchor.RIGHT | Anchor.TOP);
					btn1.setAutoSize(false);
					btn1.setSize(15, 10);
					btn1.attachData(1);
					btn1.register(this);
					add(btn1);
					int stackSize = is.getMaxStackSize();
					if (stackSize != 1)
					{
						UIButton btn2 = new UIButton(gui, "" + stackSize);
						btn2.setPosition(-1, -1, Anchor.RIGHT | Anchor.BOTTOM);
						btn2.setAutoSize(false);
						btn2.setSize(15, 10);
						btn2.attachData(stackSize);
						btn2.register(this);
						add(btn2);
					}

				}

				@Subscribe
				public void onClick(ClickEvent event)
				{
					MalisisCore.message("Spawning " + event.getComponent().getData() + " of " + item.getUnlocalizedName());
				}
			};
		});

		itemList.setElementSpacing(2);
		itemList.setElements(items);

		return itemList;
	}

	@Subscribe
	public void onClick(UIButton.ClickEvent event)
	{
		currentSize += 20;
		if (currentSize > 190)
			currentSize = selectSize;
		select.setSize(currentSize, 12);
	}

	@Subscribe
	public void onSliderChanged(ValueChange<UISlider<Integer>, Integer> event)
	{
		if (!(event.getComponent() instanceof UISlider))
			return;

		//get the different values
		int r = sliderRed.getValue();
		int g = sliderGreen.getValue();
		int b = sliderBlue.getValue();
		//use the updated value
		if (event.getComponent() == sliderRed)
			r = event.getNewValue();
		if (event.getComponent() == sliderGreen)
			g = event.getNewValue();
		if (event.getComponent() == sliderBlue)
			b = event.getNewValue();

		int color = r << 16 | g << 8 | b;

		//no black color
		if (color == 0)
		{
			event.cancel();
			return;
		}

		sliderColorLabel.setText(String.format("Color : 0x%06X", color));
		tabSlider.setBgColor(color);
	}

	@SuppressWarnings("unchecked")
	private UIComponent<?> debug()
	{
		Random r = new Random();
		UIWindow layout = new UIWindow(this, 400, 100);
		//		layout.setClipContent(false);

		//UIPanel c = new UIPanel(this);// raw typers because cant construct scrollbar otherwise
		UIPanel c = new UIPanel(this);
		c.setPosition(0, 5);
		c.setSize(150, 50);
		//c.setColor(r.nextInt(0x555555) + 0xAAAAAA);
		for (int i = 0; i < 30; i++)
			c.add(new UILabel(this, "Test " + i).setPosition(0, i * 10));
		layout.add(c);

		UIScrollBar scrollbar = new UIScrollBar(this, c, UIScrollBar.Type.VERTICAL);
		scrollbar.setPosition(-10, 0, Anchor.RIGHT);
		scrollbar.setVisible(true);

		layout.add(new UILabel(this, "Padding").setPosition(0, 250));

		UIScrollBar scrollbar3 = new UIScrollBar(this, layout, UIScrollBar.Type.VERTICAL);
		scrollbar3.setPosition(0, 0, Anchor.RIGHT);
		scrollbar3.setVisible(true);

		return layout;
	}

	@Override
	public void updateScreen()
	{
		if (bar == null)
			return;
		float t = (System.currentTimeMillis() % 2000) / 2000f;
		bar.setProgress(t);
	}

	public static class ContTest extends UIBackgroundContainer
	{
		public ContTest(MalisisGui gui)
		{
			super(gui);
		}

		@Override
		public void drawForeground(GuiRenderer renderer, int mouseX, int mouseY, float partialTick)
		{
			SimpleGuiShape shape = new SimpleGuiShape();
			shape.setSize(50, 50);
			shape.setPosition(-10, 0);
			renderer.drawShape(shape, rp);
			//renderer.next();// remove this and it won't be clipped
		}
	}
}