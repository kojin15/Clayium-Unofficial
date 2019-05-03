package mods.clayium.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mods.clayium.gui.client.GuiTemp;
import mods.clayium.gui.container.ContainerTemp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;

public class GuiTextFieldPacketHandler
  implements IMessageHandler<GuiTextFieldPacket, IMessage>
{
  public IMessage onMessage(GuiTextFieldPacket message, MessageContext ctx)
  {
    if (ctx.side == Side.SERVER)
    {
      EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
      ContainerTemp container = (ContainerTemp)entityPlayer.openContainer;
      if ((message.string != null) && (message.string.length() >= 1))
      {
        String s = message.string;
        container.setTextFieldString(entityPlayer, s, message.id);
      }
      else
      {
        container.setTextFieldString(entityPlayer, "", message.id);
      }
    }
    else
    {
      Gui g = Minecraft.getMinecraft().currentScreen;
      if ((g instanceof GuiTemp))
      {
        GuiTemp gui = (GuiTemp)g;
        gui.setTextFieldString(message.string, message.id, true);
      }
    }
    return null;
  }
}
