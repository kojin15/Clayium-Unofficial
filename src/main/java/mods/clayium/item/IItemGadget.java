package mods.clayium.item;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IItemGadget
{
  public abstract boolean match(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean);
  
  public abstract void update(List<ItemStack> paramList, Entity paramEntity, boolean paramBoolean);
}
