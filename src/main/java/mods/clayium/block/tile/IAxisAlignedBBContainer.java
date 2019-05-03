package mods.clayium.block.tile;

import net.minecraft.util.AxisAlignedBB;

public abstract interface IAxisAlignedBBContainer
{
  public abstract AxisAlignedBB getAxisAlignedBB();
  
  public abstract void setAxisAlignedBB(AxisAlignedBB paramAxisAlignedBB);
  
  public abstract boolean hasAxisAlignedBB();
  
  public abstract int getBoxAppearance();
}
