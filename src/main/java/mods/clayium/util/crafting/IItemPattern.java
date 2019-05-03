package mods.clayium.util.crafting;

import net.minecraft.item.ItemStack;

public abstract interface IItemPattern
{
  public abstract boolean match(ItemStack paramItemStack, boolean paramBoolean);
  
  public abstract boolean hasIntersection(IItemPattern paramIItemPattern, boolean paramBoolean);
  
  public abstract int getStackSize(ItemStack paramItemStack);
  
  public abstract ItemStack[] toItemStacks();
  
  public abstract ItemStack isSimple();
  
  public abstract boolean isAvailable();
}
