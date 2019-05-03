package mods.clayium.block.tile;

import net.minecraft.world.World;

public abstract interface ISynchronizedInterface
{
  public abstract boolean setCoreBlockCoord(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean setCoreBlockDimension(int paramInt);
  
  public abstract int getCoreBlockXCoord();
  
  public abstract int getCoreBlockYCoord();
  
  public abstract int getCoreBlockZCoord();
  
  public abstract World getCoreWorld();
  
  public abstract boolean isSynced();
  
  public abstract boolean acceptCoordChanger();
}
