package mods.clayium.gui;

import net.minecraft.client.gui.Gui;

public abstract interface ITexture
{
  public abstract int getSizeX();
  
  public abstract int getSizeY();
  
  public abstract void draw(Gui paramGui, int paramInt1, int paramInt2);
}
