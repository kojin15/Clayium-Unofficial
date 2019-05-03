package mods.clayium.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public abstract interface ITieredBlock
{
  public abstract int getTier(ItemStack paramItemStack);
  
  public abstract int getTier(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3);
}
