package mods.clayium.block;

import java.util.List;
import net.minecraft.item.ItemStack;

public abstract interface ISpecialToolTip
{
  public abstract List getTooltip(ItemStack paramItemStack);
}
