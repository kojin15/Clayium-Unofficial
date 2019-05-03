package mods.clayium.plugin.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import java.util.List;
import net.minecraft.item.ItemStack;

public abstract interface INEIRecipeEntry
{
  public abstract boolean matchForCraftingRecipe(ItemStack paramItemStack);
  
  public abstract boolean matchForUsageRecipe(ItemStack paramItemStack);
  
  public abstract void drawExtras();
  
  public abstract TemplateRecipeHandler.CachedRecipe asCachedRecipe();
  
  public abstract List<PositionedStack> getIngredientsToSort();
  
  public abstract TemplateRecipeHandler getHandler();
  
  public abstract void setHandler(TemplateRecipeHandler paramTemplateRecipeHandler);
}
