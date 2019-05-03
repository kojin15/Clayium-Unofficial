package mods.clayium.item;

import net.minecraft.item.ItemStack;

public abstract interface ITieredItem
{
  public abstract int getTier(ItemStack paramItemStack);
}
