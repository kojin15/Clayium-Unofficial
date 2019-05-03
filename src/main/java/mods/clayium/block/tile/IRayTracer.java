package mods.clayium.block.tile;

import mods.clayium.entity.RayTraceMemory;

public abstract interface IRayTracer
{
  public abstract void setRayTraceMemory(RayTraceMemory paramRayTraceMemory);
  
  public abstract boolean acceptRayTraceMemory(RayTraceMemory paramRayTraceMemory);
  
  public abstract RayTraceMemory getRayTraceMemory();
}
