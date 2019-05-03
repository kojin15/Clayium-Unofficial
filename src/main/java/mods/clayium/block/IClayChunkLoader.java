package mods.clayium.block;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public abstract interface IClayChunkLoader
{
  public abstract boolean isPassive(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean hasTicket();
  
  public abstract ForgeChunkManager.Ticket requestTicket();
  
  public abstract void releaseTicket();
  
  public abstract void appendTicket(ForgeChunkManager.Ticket paramTicket);
  
  public abstract void forceChunk();
}
