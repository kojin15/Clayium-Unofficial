package mods.clayium.util.crafting;

import net.minecraft.item.ItemStack;

public abstract interface ISpecialResultRecipe
{
  public abstract RecipeMap[] getUsageRecipeMap(ItemStack paramItemStack);
  
  public abstract RecipeMap[] getCraftingRecipeMap(ItemStack paramItemStack);
}
