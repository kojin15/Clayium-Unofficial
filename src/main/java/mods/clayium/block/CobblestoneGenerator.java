package mods.clayium.block;

import mods.clayium.block.tile.TileCobblestoneGenerator;

public class CobblestoneGenerator
  extends ClayNoRecipeMachines
{
  public CobblestoneGenerator(int tier)
  {
    super(null, "clayium:cobblestonegenerator", tier, TileCobblestoneGenerator.class, 2);
    this.guiId = 11;
  }
}
