package mods.clayium.block.tile;

public abstract interface IExternalControl
{
  public abstract void doWorkOnce();
  
  public abstract void startWork();
  
  public abstract void stopWork();
  
  public abstract boolean isScheduled();
  
  public abstract boolean isDoingWork();
}
