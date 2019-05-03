package mods.clayium.gui.container;

import mods.clayium.block.tile.Inventories;
import mods.clayium.block.tile.InventoryMultiPage;
import mods.clayium.block.tile.InventoryOffsetted;
import mods.clayium.block.tile.TilePANAdapter;
import mods.clayium.gui.RectangleTexture;
import mods.clayium.gui.SlotMemory;
import mods.clayium.gui.SlotWithTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerPANAdapter
  extends ContainerTemp
{
  TilePANAdapter tilePANAdapter;
  
  public ContainerPANAdapter(InventoryPlayer player, TilePANAdapter tile, Block block)
  {
    super(player, getInventory(tile), block, new Object[0]);
    this.tilePANAdapter = tile;
    this.tilePANAdapter.onSlotChange();
  }
  
  private static IInventory getInventory(TilePANAdapter tile)
  {
    return new Inventories(new IInventory[] { new InventoryMultiPage(tile, 0, 18, tile.getPageNum()), new InventoryOffsetted(tile, 144, 9) });
  }
  
  public void setMachineInventorySlots(InventoryPlayer player)
  {
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < 3; i++) {
        addMachineSlotToContainer(new SlotMemory(this, this.tile, i + j * 3, i * 18 + (this.machineGuiSizeX - 54) / 2 - 30 + 1, j * 18 + 18, RectangleTexture.SmallSlotFilterTexture));
      }
    }
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < 3; i++)
      {
        SlotWithTexture slot = new SlotMemory(this, this.tile, i + j * 3 + 9, i * 18 + (this.machineGuiSizeX - 54) / 2 + 30 + 1, j * 18 + 18);
        
        addMachineSlotToContainer(slot);
      }
    }
    for (int i = 0; i < 9; i++)
    {
      SlotWithTexture slot = new SlotWithTexture(this, this.tile, 18 + i, i * 18 + (this.machineGuiSizeX - 162) / 2 + 1, 74);
      
      addMachineSlotToContainer(slot);
    }
  }
  
  protected void initParameters(InventoryPlayer player)
  {
    this.machineGuiSizeY = 96;
    
    super.initParameters(player);
  }
  
  public boolean canTransferToMachineInventory(ItemStack itemstack1)
  {
    return false;
  }
  
  public boolean transferStackToMachineInventory(ItemStack itemstack1)
  {
    return false;
  }
  
  public boolean transferStackFromMachineInventory(ItemStack itemstack1, int slot)
  {
    return false;
  }
  
  public boolean isMultipage()
  {
    return ((InventoryMultiPage)((Inventories)this.tile).get(0)).isMultiPage();
  }
  
  public int getPresentPageNum()
  {
    return ((InventoryMultiPage)((Inventories)this.tile).get(0)).getPresentPage() + 1;
  }
  
  public int getMaxPageNum()
  {
    return ((InventoryMultiPage)((Inventories)this.tile).get(0)).pageNum;
  }
  
  public boolean pushClientButton(EntityPlayer player, int action)
  {
    int action0 = Inventories.getInnerActionId(action);
    if ((action0 == ContainerNormalInventory.buttonIdPrevious) || (action0 == ContainerNormalInventory.buttonIdNext)) {
      return enchantItem(player, action);
    }
    return true;
  }
  
  public void onCraftMatrixChanged(IInventory p_75130_1_)
  {
    this.tilePANAdapter.onSlotChange();
  }
}
