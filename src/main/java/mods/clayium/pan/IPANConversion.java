package mods.clayium.pan;

import mods.clayium.util.crafting.IItemPattern;
import net.minecraft.item.ItemStack;

public abstract interface IPANConversion
{
  public abstract IItemPattern[] getPatterns();
  
  public abstract ItemStack[] getResults();
  
  public abstract double getEnergy();
}
