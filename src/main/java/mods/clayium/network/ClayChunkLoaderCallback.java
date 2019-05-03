package mods.clayium.network;

import java.util.ArrayList;
import java.util.List;
import mods.clayium.block.IClayChunkLoader;
import mods.clayium.block.tile.TileClayChunkLoader;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilBuilder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import org.apache.logging.log4j.Logger;

public class ClayChunkLoaderCallback
  implements OrderedLoadingCallback
{
  public void ticketsLoaded(List<Ticket> tickets, World world)
  {
    for (Ticket ticket : tickets)
    {
      int x = ticket.getModData().getInteger("chunkLoaderX");
      int y = ticket.getModData().getInteger("chunkLoaderY");
      int z = ticket.getModData().getInteger("chunkLoaderZ");
      TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
      if ((te instanceof IClayChunkLoader))
      {
        IClayChunkLoader chunkloader = (IClayChunkLoader)te;
        if (chunkloader.hasTicket()) {
          chunkloader.releaseTicket();
        }
        chunkloader.appendTicket(ticket);
        chunkloader.forceChunk();
        if (TileClayChunkLoader.chunkLoaderLog) {
          ClayiumCore.logger.info("Activating the chunkLoader " + x + " " + y + " " + z);
        }
      }
      else
      {
        ForgeChunkManager.releaseTicket(ticket);
      }
    }
  }
  
  public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount)
  {
    List<Ticket> res = new ArrayList();
    for (Ticket ticket : tickets)
    {
      int x = ticket.getModData().getInteger("chunkLoaderX");
      int y = ticket.getModData().getInteger("chunkLoaderY");
      int z = ticket.getModData().getInteger("chunkLoaderZ");
      TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
      if ((te instanceof IClayChunkLoader))
      {
        IClayChunkLoader chunkloader = (IClayChunkLoader)te;
        if (!chunkloader.isPassive(world, x, y, z)) {
          res.add(ticket);
        }
      }
    }
    return res;
  }
}
