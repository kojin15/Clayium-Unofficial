package mods.clayium.block.tile;

import net.minecraft.entity.player.EntityPlayer;

public abstract interface IGeneralInterface
{
  public abstract void markForStrongUpdate();
  
  public abstract void markForWeakUpdate();
  
  public abstract void setSyncFlag();
  
  public abstract void setInstantSyncFlag();
  
  public abstract void setRenderSyncFlag();
  
  public abstract void pushButton(EntityPlayer paramEntityPlayer, int paramInt);
}
