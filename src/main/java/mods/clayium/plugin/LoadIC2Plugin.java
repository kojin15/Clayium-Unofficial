package mods.clayium.plugin;

import cpw.mods.fml.common.Optional.Method;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mods.clayium.item.CItems;
import mods.clayium.item.CMaterials;
import mods.clayium.item.filter.ItemFilterSpecial;
import mods.clayium.util.crafting.CRecipes;
import mods.clayium.util.crafting.CRecipes.RecipeConditions;
import mods.clayium.util.crafting.OreDictionaryStack;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LoadIC2Plugin
{
  @Optional.Method(modid="IC2")
  public static void loadRecipes()
  {
    CRecipes.register1to1Recipe(CRecipes.recipeCondenser, CMaterials.getOD(CMaterials.IRIDIUM, CMaterials.INGOT), 5, Ic2Items.iridiumOre, 60L);
    CRecipes.register1to1Recipe(CRecipes.recipeCondenser, CMaterials.getOD(CMaterials.URANIUM, CMaterials.INGOT), 5, Ic2Items.Uran238, 60L);
    CRecipes.register1to1Recipe(CRecipes.recipeCondenser, CMaterials.getOD(CMaterials.PLUTONIUM, CMaterials.INGOT), 5, Ic2Items.Plutonium, 60L);
    CRecipes.register1to1Recipe(CRecipes.recipeSmelter, Ic2Items.iridiumOre, 5, CMaterials.getODExist(CMaterials.IRIDIUM, CMaterials.INGOT), 60L);
    CRecipes.register1to1Recipe(CRecipes.recipeSmelter, Ic2Items.Uran238, 5, CMaterials.getODExist(CMaterials.URANIUM, CMaterials.INGOT), 60L);
    CRecipes.register1to1Recipe(CRecipes.recipeSmelter, Ic2Items.Plutonium, 5, CMaterials.getODExist(CMaterials.PLUTONIUM, CMaterials.INGOT), 60L);
    if (!CMaterials.existOD("itemRawRubber")) {
      CRecipes.recipeCAInjector.addRecipe(CRecipes.ii(new ItemStack[] { CRecipes.i(Blocks.log), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM) }), 0, 10, 
        CRecipes.ii(new ItemStack[] { Ic2Items.resin }), CRecipes.e(2.0D, 10), 60L);
    }
    Map<IMachineRecipeManager, RecipeConditions> recipeMap = new HashMap();
    recipeMap.put(ic2.api.recipe.Recipes.metalformerExtruding, new RecipeConditions(CRecipes.recipeWireDrawingMachine, 4, 20L));
    recipeMap.put(ic2.api.recipe.Recipes.metalformerRolling, new RecipeConditions(CRecipes.recipePipeDrawingMachine, 4, 20L));
    recipeMap.put(ic2.api.recipe.Recipes.metalformerCutting, new RecipeConditions(CRecipes.recipeCuttingMachine, 4, 20L));
    recipeMap.put(ic2.api.recipe.Recipes.compressor, new RecipeConditions(CRecipes.recipeCondenser, 5, 20L));
    recipeMap.put(ic2.api.recipe.Recipes.macerator, new RecipeConditions(CRecipes.recipeGrinder, 6, 20L));
    recipeMap.put(ic2.api.recipe.Recipes.extractor, new RecipeConditions(CRecipes.recipeTransformer, 7, 20L));
    for (IMachineRecipeManager ic2recipes : recipeMap.keySet())
    {
      crecipes = ((RecipeConditions)recipeMap.get(ic2recipes)).recipes;
      tier = ((RecipeConditions)recipeMap.get(ic2recipes)).tier;
      time = ((RecipeConditions)recipeMap.get(ic2recipes)).time;
      for (i = ic2recipes.getRecipes().entrySet().iterator(); i.hasNext();)
      {
        Entry<IRecipeInput, RecipeOutput> entry = (Entry)i.next();
        IRecipeInput input = (IRecipeInput)entry.getKey();
        RecipeOutput output = (RecipeOutput)entry.getValue();
        if ((input instanceof RecipeInputItemStack)) {
          CRecipes.register1to1Recipe(crecipes, CRecipes.s(((RecipeInputItemStack)input).input.copy(), ((RecipeInputItemStack)input).amount), tier, 
            (ItemStack)output.items.get(0), time);
        } else if ((input instanceof RecipeInputOreDict)) {
          CRecipes.register1to1Recipe(crecipes, new OreDictionaryStack(((RecipeInputOreDict)input).input, ((RecipeInputOreDict)input).amount), tier, 
            (ItemStack)output.items.get(0), time);
        }
      }
    }
    mods.clayium.util.crafting.Recipes crecipes;
    int tier;
    long time;
    Iterator<Entry<IRecipeInput, RecipeOutput>> i;
    CItems.itemFilterBlockHarvestable.addSpecialFilter(new FilterIC2CropHarvestable());
  }
}
