package mods.clayium.gui.container;

import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotResultWithTexture;
import mods.clayium.gui.SlotWithTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerClayAssembler
  extends ContainerClayMachines
{
  public ContainerClayAssembler(InventoryPlayer player, TileClayMachines tile, Block block)
  {
    super(player, tile, block);
  }
  
  protected void initParameters(InventoryPlayer player)
  {
    super.initParameters(player);
    this.resultSlotIndex = 2;
  }
  
  public void setMachineInventorySlots(InventoryPlayer player)
  {
    addMachineSlotToContainer(new SlotWithTexture(this.tile, 0, 32, 35, RectangleTexture.SmallSlotImport1Texture));
    addMachineSlotToContainer(new SlotWithTexture(this.tile, 1, 50, 35, RectangleTexture.SmallSlotImport2Texture));
    addMachineSlotToContainer(new SlotResultWithTexture(this.tile, 2, 116, 35, RectangleTexture.LargeSlotTexture));
  }
}
