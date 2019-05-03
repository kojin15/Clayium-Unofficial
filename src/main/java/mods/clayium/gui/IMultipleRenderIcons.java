package mods.clayium.gui;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract interface IMultipleRenderIcons
{
  public abstract int getRenderPasses();
  
  public abstract IIcon getIconFromPass(int paramInt);
  
  public abstract int getColorFromPass(int paramInt);
  
  public abstract void registerIcons(IIconRegister paramIIconRegister);
}
