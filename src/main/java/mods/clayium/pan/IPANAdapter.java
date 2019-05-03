package mods.clayium.pan;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IPANAdapter
{
  public abstract ItemStack[] getPatternItems();
  
  public abstract ItemStack[] getSubItems();
  
  public abstract World getConnectedWorld();
  
  public abstract int getConnectedXCoord();
  
  public abstract int getConnectedYCoord();
  
  public abstract int getConnectedZCoord();
}
