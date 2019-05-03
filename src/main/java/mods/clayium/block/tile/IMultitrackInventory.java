package mods.clayium.block.tile;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public abstract interface IMultitrackInventory
  extends ISidedInventory
{
  public abstract int[] getAccessibleSlotsFromSide(int paramInt1, int paramInt2);
  
  public abstract boolean canInsertItem(int paramInt1, ItemStack paramItemStack, int paramInt2, int paramInt3);
  
  public abstract boolean canExtractItem(int paramInt1, ItemStack paramItemStack, int paramInt2, int paramInt3);
}
