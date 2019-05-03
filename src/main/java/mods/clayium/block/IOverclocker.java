package mods.clayium.block;

import net.minecraft.world.IBlockAccess;

public abstract interface IOverclocker
{
  public abstract double getOverclockFactor(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3);
}
