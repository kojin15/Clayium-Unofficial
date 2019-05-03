package mods.clayium.block.tile;

import net.minecraft.util.AxisAlignedBB;

public abstract interface IAxisAlignedBBProvider
{
  public abstract AxisAlignedBB getAxisAlignedBB();
  
  public abstract boolean hasAxisAlignedBB();
  
  public abstract void setAxisAlignedBBToMachine();
}
