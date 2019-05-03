package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import java.util.HashMap;
import mods.clayium.util.UtilAdvancedTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class ClaySteelPickaxePacketHandler
  implements IMessageHandler<ClaySteelPickaxePacket, IMessage>
{
  public IMessage onMessage(ClaySteelPickaxePacket message, MessageContext ctx)
  {
    EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
    if (message.side != -2)
    {
      UtilAdvancedTools.sideList.put(entityPlayer, Integer.valueOf(message.side));
    }
    else
    {
      HashMap<String, Integer> map = new HashMap();
      map.put("x", Integer.valueOf(message.x));
      map.put("y", Integer.valueOf(message.y));
      map.put("z", Integer.valueOf(message.z));
      map.put("d", Integer.valueOf(message.dimid));
      DimensionManager.getWorld(message.dimid).markBlockForUpdate(message.x, message.y, message.z);
      UtilAdvancedTools.forceList.put(entityPlayer, map);
    }
    return null;
  }
}
