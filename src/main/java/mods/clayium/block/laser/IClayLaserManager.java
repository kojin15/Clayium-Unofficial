package mods.clayium.block.laser;

import mods.clayium.util.UtilDirection;

public abstract interface IClayLaserManager
{
  public abstract ClayLaser getClayLaser();
  
  public abstract UtilDirection getDirection();
  
  public abstract int getLaserLength();
  
  public abstract int[] getTargetCoord();
  
  public abstract boolean hasTarget();
  
  public abstract boolean isIrradiating();
}
