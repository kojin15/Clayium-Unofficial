package mods.clayium.gui.client;

import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class GuiStorageContainer
  extends GuiTemp
{
  protected TileStorageContainer tile;
  
  public GuiStorageContainer(ContainerTemp container, TileStorageContainer tile)
  {
    super(container);
    this.tile = tile;
  }
  
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ)
  {
    super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
    ContainerTemp container = (ContainerTemp)this.inventorySlots;
    int size = this.tile.containerItemStacks[0] == null ? 0 : this.tile.containerItemStacks[0].stackSize;
    int width = this.fontRendererObj.getStringWidth("" + size);
    this.fontRendererObj.drawString("" + size + " / " + this.tile.maxStorageSize, container.machineGuiSizeX / 2 - 8 - width, container.machineGuiSizeY - 12, 4210752);
  }
}
