package mods.clayium.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public abstract interface IItemExRenderer
{
  @SideOnly(Side.CLIENT)
  public abstract void preRenderItem(IItemRenderer.ItemRenderType paramItemRenderType, ItemStack paramItemStack, Object... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  public abstract void postRenderItem(IItemRenderer.ItemRenderType paramItemRenderType, ItemStack paramItemStack, Object... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  public abstract void preRenderItemPass(IItemRenderer.ItemRenderType paramItemRenderType, ItemStack paramItemStack, int paramInt, Object... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  public abstract void postRenderItemPass(IItemRenderer.ItemRenderType paramItemRenderType, ItemStack paramItemStack, int paramInt, Object... paramVarArgs);
}
