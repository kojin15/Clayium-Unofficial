package mods.clayium.item.filter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IFilterSizeChecker
{
  public abstract void checkFilterSize(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld);
}
