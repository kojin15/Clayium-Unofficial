package mods.clayium.block;

import mods.clayium.block.tile.TileClayContainer;
import net.minecraft.world.IBlockAccess;

public abstract interface IClayContainerModifier
{
  public abstract void modifyClayContainer(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, TileClayContainer paramTileClayContainer);
}
