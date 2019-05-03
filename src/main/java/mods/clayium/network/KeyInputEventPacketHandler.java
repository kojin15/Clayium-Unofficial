package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mods.clayium.util.UtilKeyInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;

public class KeyInputEventPacketHandler
  implements IMessageHandler<KeyInputEventPacket, IMessage>
{
  public IMessage onMessage(KeyInputEventPacket message, MessageContext ctx)
  {
    EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
    UtilKeyInput.setKeyInput(ctx.getServerHandler().playerEntity, message.key, message.keystate);
    return null;
  }
}
