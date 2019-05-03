package mods.clayium.block.tile;

import net.minecraft.inventory.IInventory;

public abstract interface INormalInventory
  extends IInventory
{
  public abstract int getInventoryX();
  
  public abstract int getInventoryY();
  
  public abstract int getInventoryP();
  
  public abstract int getInventoryStart();
}
