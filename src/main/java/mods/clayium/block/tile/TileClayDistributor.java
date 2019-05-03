package mods.clayium.block.tile;

import java.util.ArrayList;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilTransfer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileClayDistributor
  extends TileClayContainerTiered
{
  public int inventoryX = 0;
  public int inventoryY = 0;
  public int inventoryColonyX = 0;
  public int inventoryColonyY = 0;
  public int startSide = 0;
  public int autoInsertColony = 0;
  public int autoExtractColony = 0;
  public boolean autoInsertDelayFlag = false;
  
  public void initParams()
  {
    super.initParams();
    this.insertRoutes = new int[] { -1, -1, -1, 0, -1, -1 };
    this.extractRoutes = new int[] { 0, 0, 0, -1, 0, 0 };
    this.autoInsert = true;
    this.autoExtract = true;
    this.containerItemStacks = new ItemStack[48];
  }
  
  public void initParamsByTier(int tier)
  {
    switch (tier)
    {
    case 7: 
      this.inventoryX = (this.inventoryY = 2);this.maxAutoInsertDefault = (this.maxAutoExtractDefault = 64);
      this.inventoryColonyX = (this.inventoryColonyY = 2);
      this.autoExtractInterval = (this.autoInsertInterval = 1); break;
    case 8: 
      this.inventoryX = (this.inventoryY = 2);this.maxAutoInsertDefault = (this.maxAutoExtractDefault = '');
      this.inventoryColonyX = ((this.inventoryColonyY = 2) + 1);
      this.autoExtractInterval = (this.autoInsertInterval = 1); break;
    case 9: 
      this.inventoryX = (this.inventoryY = 2);this.maxAutoInsertDefault = (this.maxAutoExtractDefault = 'Ȁ');
      this.inventoryColonyX = ((this.inventoryColonyY = 3) + 1);
      this.autoExtractInterval = (this.autoInsertInterval = 1); break;
    default: 
      this.inventoryX = (this.inventoryY = 1);this.maxAutoInsertDefault = (this.maxAutoExtractDefault = 1);
      this.inventoryColonyX = (this.inventoryColonyY = 1);
      this.autoExtractInterval = (this.autoInsertInterval = 8);
    }
    int slotNum = this.inventoryX * this.inventoryY * this.inventoryColonyX * this.inventoryColonyY;
    
    int[] slots = new int[slotNum];
    int[] slots2 = new int[slotNum];
    for (int i = 0; i < slots.length; i++)
    {
      slots[i] = i;
      slots2[i] = (slots.length - i - 1);
    }
    this.listSlotsInsert.add(slots);
    this.listSlotsExtract.add(slots2);
    
    this.slotsDrop = slots;
  }
  
  public void openInventory() {}
  
  public void closeInventory() {}
  
  public boolean isItemValidForSlot(int slot, ItemStack itemstack)
  {
    for (int i = 0; i < this.listSlotsInsert.size(); i++) {
      for (int j = 0; j < ((int[])this.listSlotsInsert.get(i)).length; j++) {
        if (((int[])this.listSlotsInsert.get(i))[j] == slot) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void updateEntity()
  {
    if (!this.worldObj.isRemote)
    {
      if (this.autoExtractColony >= this.inventoryColonyX * this.inventoryColonyY) {
        this.autoExtractColony = 0;
      }
      if (this.autoInsertColony >= this.inventoryColonyX * this.inventoryColonyY) {
        this.autoInsertColony = 0;
      }
      boolean syncFlag = false;
      if ((this.autoExtractColony != this.autoInsertColony) || (!this.autoInsertDelayFlag))
      {
        int o = this.inventoryX * this.inventoryY * this.autoExtractColony;
        boolean flag = false;
        for (int i = o; i < o + this.inventoryX * this.inventoryY; i++) {
          if (this.containerItemStacks[i] != null)
          {
            flag = true;
            break;
          }
        }
        if ((flag) && (++this.autoExtractColony >= this.inventoryColonyX * this.inventoryColonyY))
        {
          this.autoExtractColony = 0;
          this.autoInsertDelayFlag = true;
        }
        if (flag) {
          syncFlag = true;
        }
        int[] slots = new int[this.inventoryX * this.inventoryY];
        for (int i = 0; i < slots.length; i++) {
          slots[i] = (i + this.inventoryX * this.inventoryY * this.autoExtractColony);
        }
        this.listSlotsInsert = new ArrayList();
        this.listSlotsInsert.add(slots);
      }
      if ((this.autoExtractColony != this.autoInsertColony) || (this.autoInsertDelayFlag))
      {
        int o = this.inventoryX * this.inventoryY * this.autoInsertColony;
        boolean flag = true;
        for (int i = o; i < o + this.inventoryX * this.inventoryY; i++) {
          if (this.containerItemStacks[i] != null)
          {
            flag = false;
            break;
          }
        }
        if ((flag) && (++this.autoInsertColony >= this.inventoryColonyX * this.inventoryColonyY))
        {
          this.autoInsertColony = 0;
          this.autoInsertDelayFlag = false;
        }
        if (flag) {
          syncFlag = true;
        }
      }
      int[] slots = new int[this.inventoryX * this.inventoryY];
      for (int i = 0; i < slots.length; i++) {
        slots[i] = (i + this.inventoryX * this.inventoryY * this.autoInsertColony);
      }
      this.listSlotsExtract = new ArrayList();
      this.listSlotsExtract.add(slots);
      if ((this.autoExtractColony == this.autoInsertColony) && (this.autoInsertDelayFlag))
      {
        slots = new int[0];
        this.listSlotsInsert = new ArrayList();
        this.listSlotsInsert.add(slots);
      }
      if (syncFlag) {
        setSyncFlag();
      }
    }
    super.updateEntity();
  }
  
  public boolean isCrowded()
  {
    return (this.autoInsertDelayFlag) && (this.autoExtractColony == this.autoInsertColony);
  }
  
  public void doAutoInsert()
  {
    if (!canGetFrontDirection()) {
      return;
    }
    setSyncFlag();
    for (int i = 0; i < 6; i++) {
      if (this.extractRoutes[i] >= this.listSlotsExtract.size()) {
        this.extractRoutes[i] = -1;
      }
    }
    int front = getFrontDirection();
    
    int side = 0;
    int max = this.maxAutoInsertDefault;int oldMax = max + 1;
    while (oldMax > max)
    {
      oldMax = max;
      int nextStartSide = this.startSide;
      for (int i = 0; i < 6; i++)
      {
        if ((side = this.startSide + i) >= 6) {
          side -= 6;
        }
        int route = this.extractRoutes[side];
        if ((route >= 0) && (route < this.listSlotsExtract.size()) && (max >= 1) && 
          (UtilTransfer.insert(this, (int[])this.listSlotsExtract.get(route), UtilDirection.getOrientation(front), UtilDirection.getSide(side), 1, new Object[0]) == 0))
        {
          max--;
          if ((nextStartSide = side + 1) >= 6) {
            nextStartSide -= 6;
          }
        }
      }
      this.startSide = nextStartSide;
    }
  }
  
  public void readFromNBT(NBTTagCompound tagCompound)
  {
    super.readFromNBT(tagCompound);
    this.startSide = tagCompound.getShort("StartSide");
    this.autoInsertColony = tagCompound.getShort("AutoInsertColony");
    this.autoExtractColony = tagCompound.getShort("AutoExtractColony");
    this.autoInsertDelayFlag = tagCompound.getBoolean("AutoInsertDelayFlag");
    
    this.inventoryX = tagCompound.getShort("InventoryX");
    this.inventoryY = tagCompound.getShort("InventoryY");
    this.inventoryColonyX = tagCompound.getShort("InventoryColonyX");
    this.inventoryColonyY = tagCompound.getShort("InventoryColonyY");
  }
  
  public void writeToNBT(NBTTagCompound tagCompound)
  {
    super.writeToNBT(tagCompound);
    tagCompound.setShort("StartSide", (short)this.startSide);
    tagCompound.setShort("AutoInsertColony", (short)this.autoInsertColony);
    tagCompound.setShort("AutoExtractColony", (short)this.autoExtractColony);
    tagCompound.setBoolean("AutoInsertDelayFlag", this.autoInsertDelayFlag);
    
    tagCompound.setShort("InventoryX", (short)this.inventoryX);
    tagCompound.setShort("InventoryY", (short)this.inventoryY);
    tagCompound.setShort("InventoryColonyX", (short)this.inventoryColonyX);
    tagCompound.setShort("InventoryColonyY", (short)this.inventoryColonyY);
  }
}
