package mods.clayium.pan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mods.clayium.block.ClayEnergyLaser;
import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.tile.TileCAInjector;
import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.block.tile.TileClayEnergyLaser;
import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.block.tile.TileClayReactor;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.Recipes;
import mods.clayium.util.crafting.Recipes.RecipeCondition;
import mods.clayium.util.crafting.Recipes.RecipeResult;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PANACFactoryClayMachines
  implements IPANAdapterConversionFactory
{
  public boolean match(World world, int x, int y, int z)
  {
    return UtilBuilder.safeGetTileEntity(world, x, y, z) instanceof TileClayMachines;
  }
  
  public IPANConversion getConversion(IPANAdapter adapter)
  {
    boolean flag = true;
    ItemStack item;
    for (item : adapter.getPatternItems()) {
      if (item != null)
      {
        flag = false;
        break;
      }
    }
    if (flag) {
      return null;
    }
    TileEntity te = UtilBuilder.safeGetTileEntity(adapter.getConnectedWorld(), adapter.getConnectedXCoord(), adapter.getConnectedYCoord(), adapter.getConnectedZCoord());
    if ((te instanceof TileClayMachines))
    {
      Recipes recipe = Recipes.GetRecipes(((TileClayMachines)te).getRecipeId());
      if (recipe != null)
      {
        Object ingred = new ArrayList();
        ItemStack item;
        for (item : adapter.getPatternItems()) {
          if (item != null) {
            ((List)ingred).add(item);
          }
        }
        Recipes.RecipeCondition condition = recipe.getRecipeConditionFromInventory((ItemStack[])((List)ingred).toArray(new ItemStack[0]), 0, ((TileClayMachines)te).getRecipeTier());
        Recipes.RecipeResult result = condition != null ? recipe.getRecipeResult(condition) : null;
        if (result != null)
        {
          Object patterns = new ArrayList();
          for (Object material : condition.getMaterials()) {
            ((List)patterns).add(Recipes.convert(material));
          }
          List<ItemStack> results = new ArrayList(Arrays.asList(result.getResults()));
          
          long energy = ((float)result.getEnergy() * ((TileClayMachines)te).multConsumingEnergy);
          long time = ((float)result.getTime() * ((TileClayMachines)te).multCraftTime);
          if ((te instanceof TileClayReactor))
          {
            int b = 0;int g = 0;int r = 0;
            for (ItemStack item : adapter.getSubItems()) {
              if ((item != null) && ((item.getItem() instanceof ItemBlock)))
              {
                Block block = ((ItemBlock)item.getItem()).field_150939_a;
                if ((block instanceof ClayEnergyLaser)) {
                  switch (((ClayEnergyLaser)block).getTier(item))
                  {
                  case 7: 
                    b += item.stackSize; break;
                  case 8: 
                    g += item.stackSize; break;
                  case 9: 
                    r += item.stackSize;
                  }
                }
              }
            }
            long le = (ClayLaser.getEnergy(new int[] { b, g, r }) + 1.0D);
            time -= 1L;
            if (time <= 0L) {
              time = 1L;
            }
            time = time / le + 1L;
            energy += TileClayEnergyLaser.consumingEnergyBlue * b;
            energy += TileClayEnergyLaser.consumingEnergyGreen * g;
            energy += TileClayEnergyLaser.consumingEnergyRed * r;
          }
          if ((te instanceof TileCAReactor)) {
            if ((((TileCAReactor)te).isConstructed()) && (((TileCAReactor)te).getResultPureAntimatter() != null))
            {
              energy = (energy * ((TileCAReactor)te).getConsumingEnergyTotalMultiplier());
              time = (time * ((TileCAReactor)te).getCraftTimeTotalMultiplier());
              results.clear();
              results.add(((TileCAReactor)te).getResultPureAntimatter());
            }
            else
            {
              results.clear();
            }
          }
          if ((te instanceof TileCAInjector)) {
            time = (time * ((TileCAInjector)te).getCraftTimeMultiplier());
          }
          if (time <= 0L) {
            time = 1L;
          }
          if (results.size() > 0) {
            return new PANConversion((IItemPattern[])((List)patterns).toArray(new IItemPattern[0]), (ItemStack[])results.toArray(new ItemStack[0]), energy * time);
          }
        }
      }
    }
    return null;
  }
}
