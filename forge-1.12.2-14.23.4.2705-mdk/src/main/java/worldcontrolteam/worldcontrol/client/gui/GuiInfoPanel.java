package worldcontrolteam.worldcontrol.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import worldcontrolteam.worldcontrol.client.gui.features.CompactButton;
import worldcontrolteam.worldcontrol.client.gui.features.GuiInfoPanelShowLabels;
import worldcontrolteam.worldcontrol.container.ContainerInfoPanel;
import worldcontrolteam.worldcontrol.network.ChannelHandler;
import worldcontrolteam.worldcontrol.network.messages.PacketUpdateShowLabels;


@SideOnly(Side.CLIENT)
public class GuiInfoPanel extends GuiContainer {
  private static final String TEXTURE_FILE = "worldcontrol:textures/gui/gui_info_panel.png";
  private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(TEXTURE_FILE);

  protected String name;
  protected ContainerInfoPanel container;
  public ItemStack prevCard;
  protected GuiTextField textboxTitle;
  protected boolean modified;

  public GuiInfoPanel(Container container) {
    super(container);
    ySize = 190;
    this.container = (ContainerInfoPanel) container;
    name = I18n.format("tile.blockInfoPanel.name");
    modified = false;
    // inverted value on start to force initControls

  }

	/*
	 * @SuppressWarnings("unchecked") protected void drawItemStack(ItemStack
	 * itemStack, int par2, int par3){
	 *
	 * @SuppressWarnings("rawtypes") List list =
	 * itemStack.getTooltip(this.mc.thePlayer,
	 * this.mc.gameSettings.advancedItemTooltips);
	 *
	 * if(container.panel.getIsWeb() && itemStack.hasTagCompound()) {
	 * NBTTagCompound tags = itemStack.getTagCompound();
	 * if(tags.hasKey("_webSensorId")){ long id = tags.getLong("_webSensorId");
	 * if(id>0) list.add("Web Id: "+id); } }
	 *
	 * for (int k = 0; k < list.size(); ++k){ if (k == 0){ list.set(k, "\u00a7"
	 * + Integer.toHexString(itemStack.getRarity().rarityColor) +
	 * (String)list.get(k)); }else{ list.set(k, EnumChatFormatting.GRAY +
	 * (String)list.get(k)); } }
	 *
	 * FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
	 * drawHoveringText(list, par2, par3, (font == null ? fontRenderer :
	 * font)); }
	 */

  @Override
  protected void drawHoveringText(List par1List, int par2, int par3,
      FontRenderer font) {
    if (!par1List.isEmpty()) {
      GL11.glDisable(GL12.GL_RESCALE_NORMAL);
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable(GL11.GL_LIGHTING);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      int k = 0;
      Iterator iterator = par1List.iterator();

      while (iterator.hasNext()) {
        String s = (String) iterator.next();
        int l = font.getStringWidth(s);

        if (l > k) {
          k = l;
        }
      }

      int i1 = par2 + 12;
      int j1 = par3 - 12;
      int k1 = 8;

      if (par1List.size() > 1) {
        k1 += 2 + (par1List.size() - 1) * 10;
      }

      if (i1 + k > this.width) {
        i1 -= 28 + k;
      }

      if (j1 + k1 + 6 > this.height) {
        j1 = this.height - k1 - 6;
      }

      this.zLevel = 300.0F;
      itemRender.zLevel = 300.0F;
      int l1 = -267386864;
      this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
      this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
      this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
      this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
      this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
      int i2 = 1347420415;
      int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
      this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
      this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
      this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
      this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

      for (int k2 = 0; k2 < par1List.size(); ++k2) {
        String s1 = (String) par1List.get(k2);
        font.drawStringWithShadow(s1, i1, j1, -1);

        if (k2 == 0)
          j1 += 2;

        j1 += 10;
      }

      this.zLevel = 0.0F;
      itemRender.zLevel = 0.0F;
      GL11.glEnable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      RenderHelper.enableStandardItemLighting();
      GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
  }

  @SuppressWarnings("unchecked")
  protected void initControls() {
    buttonList.clear();
    buttonList.add(new GuiInfoPanelShowLabels(0, guiLeft + xSize - 25, guiTop + 42, container.panel));
    /*ItemStack card = this.getActiveCard();
    if (((card == null && prevCard == null) || (card != null && card.equals(prevCard))) && this.container.panel.getColored() == isColored)
      return;
    int h = fontRenderer.FONT_HEIGHT + 1;

    prevCard = card;
    //isColored = container.panel.getColored();

    int delta = 0;
    if (isColored) {
      buttonList.add(new CompactButton(112, guiLeft + xSize - 25, guiTop + 55, 18, 12, "T"));
      delta = 15;
    }
    if (card != null && card.getItem() instanceof IPanelDataSource) {
      byte slot = container.panel.getIndexOfCard(card);
      IPanelDataSource source = (IPanelDataSource) card.getItem();
      if (source instanceof IAdvancedCardSettings) {
        buttonList.add(new CompactButton(111, guiLeft + xSize - 25, guiTop + 55 + delta, 18, 12, "..."));
      }
      int row = 0;
      List<PanelSetting> settingsList = null;
      if (card.getItem() instanceof IPanelMultiCard) {
        settingsList = ((IPanelMultiCard) source).getSettingsList(new CardWrapperImpl(card, (byte) 0));
      } else {
        settingsList = source.getSettingsList();
      }

      if (settingsList != null)
        for (PanelSetting panelSetting : settingsList) {
          buttonList.add(new GuiInfoPanelCheckBox(0, guiLeft + 32, guiTop + 40 + h * row, panelSetting, container.panel, slot, fontRenderer));
          row++;
        }
      if (!modified) {
        textboxTitle = new GuiTextField(0, fontRenderer, 7, 16, 162, 18);
        textboxTitle.setFocused(true);
        textboxTitle.setText(new CardWrapperImpl(card, 0).getTitle());
      }
    } else {
      modified = false;
      textboxTitle = null;
    }*/
  }

  @Override
  public void initGui() {
    super.initGui();
    initControls();
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    fontRenderer.drawString(name,(xSize - fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
    fontRenderer.drawString(I18n.format("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    if (textboxTitle != null)
      textboxTitle.drawTextBox();
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    mc.renderEngine.bindTexture(TEXTURE_LOCATION);
    int left = (width - xSize) / 2;
    int top = (height - ySize) / 2;
    drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
  }

  @Override
  protected void mouseClicked(int x, int y, int par3) throws IOException{
    super.mouseClicked(x, y, par3);
    if (textboxTitle != null)
      textboxTitle.mouseClicked(x - guiLeft, y - guiTop, par3);
  }

  @Override
  protected void mouseReleased(int mouseX, int mouseY, int state) {
    super.mouseReleased(mouseX, mouseY, state);
  }

  @Override
  public void updateScreen() {
    super.updateScreen();
    if (textboxTitle != null)
      textboxTitle.updateCursorCounter();
    initControls();
  }

  protected ItemStack getActiveCard() {
    return container.panel.getStackInSlot(0);
  }

  protected void updateTitle() {
    if (textboxTitle == null)
      return;
    ItemStack card = getActiveCard();
    //TODO
    /*if (container.panel.getWorldObj().isRemote) {
      NuclearNetworkHelper.setNewAlarmSound(container.panel.xCoord, container.panel.yCoord, container.panel.zCoord, container.panel.getIndexOfCard(card), textboxTitle.getText());
    }
    if (card != null && card.getItem() instanceof IPanelDataSource) {
      new CardWrapperImpl(card, 0).setTitle(textboxTitle.getText());
    }*/
  }

  @Override
  public void onGuiClosed() {
    updateTitle();
    super.onGuiClosed();
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    //TODO
    if(button.id == 0){
      //This is the showLabels button
      boolean update = !this.container.panel.shouldShowLabels();
      this.container.panel.updateShowLabels(update);
      ChannelHandler.network.sendToServer(new PacketUpdateShowLabels(update, this.container.panel.getPos()));
    }

    /*if (button.id == 112) { // color upgrade

      GuiScreen colorGui = new GuiScreenColor(this, container.panel);
      mc.displayGuiScreen(colorGui);
    } else if (button.id == 111) {
      ItemStack card = getActiveCard();
      if (card == null)
        return;
      if (card != null && card.getItem() instanceof IAdvancedCardSettings) {
        ICardWrapper helper = new CardWrapperImpl(card, 0);
        Object guiObject = ((IAdvancedCardSettings) card.getItem()).getSettingsScreen(helper);
        if (!(guiObject instanceof GuiScreen)) {
          IC2NuclearControl.logger.warn("Invalid card, getSettingsScreen method should return GuiScreen object");
          return;
        }
        GuiScreen gui = (GuiScreen) guiObject;
        ICardSettingsWrapper wrapper = new CardSettingsWrapperImpl(card, container.panel, this, 0);
        ((ICardGui) gui).setCardSettingsHelper(wrapper);
        mc.displayGuiScreen(gui);
      }
    }*/
  }

  @Override
  protected void keyTyped(char par1, int par2) throws IOException{
    if (textboxTitle != null && textboxTitle.isFocused())
      if (par2 == 1)
        mc.player.closeScreen();
      else if (par1 == 13)
        updateTitle();
      else{
        modified = true;
        textboxTitle.textboxKeyTyped(par1, par2);
      }
    else
      super.keyTyped(par1, par2);
  }
}

