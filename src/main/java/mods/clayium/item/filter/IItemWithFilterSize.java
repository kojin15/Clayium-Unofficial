package mods.clayium.item.filter;

import net.minecraft.nbt.NBTTagCompound;

public abstract interface IItemWithFilterSize
{
  public abstract int getFilterSize(NBTTagCompound paramNBTTagCompound);
}
