package mods.clayium.pan;

import net.minecraft.world.World;

public abstract interface IPANAdapterConversionFactory
{
  public abstract boolean match(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract IPANConversion getConversion(IPANAdapter paramIPANAdapter);
}
