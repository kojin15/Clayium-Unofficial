package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mods.clayium.util.UtilKeyInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;

public class MouseClickEventPacketHandler
  implements IMessageHandler<MouseClickEventPacket, IMessage>
{
  public IMessage onMessage(MouseClickEventPacket message, MessageContext ctx)
  {
    EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
    UtilKeyInput.setMouseInput(ctx.getServerHandler().playerEntity, message.button, message.buttonstate);
    return null;
  }
}
