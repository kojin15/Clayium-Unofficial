package mods.clayium.util;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

public class UtilNetwork
{
  public static void sendParticlePacketFromServer(WorldServer server, String particle, double posX, double posY, double posZ, int number, double motionX, double motionY, double motionZ, double diffusion, double distance)
  {
    S2APacketParticles s2apacketparticles = new S2APacketParticles(particle, (float)posX, (float)posY, (float)posZ, (float)motionX, (float)motionY, (float)motionZ, (float)diffusion, number);
    for (int j = 0; j < server.playerEntities.size(); j++)
    {
      EntityPlayerMP entityplayermp = (EntityPlayerMP)server.playerEntities.get(j);
      ChunkCoordinates chunkcoordinates = entityplayermp.getPlayerCoordinates();
      double d7 = posX - chunkcoordinates.posX;
      double d8 = posY - chunkcoordinates.posY;
      double d9 = posZ - chunkcoordinates.posZ;
      double d10 = d7 * d7 + d8 * d8 + d9 * d9;
      if (d10 <= distance * distance) {
        entityplayermp.playerNetServerHandler.sendPacket(s2apacketparticles);
      }
    }
  }
}
