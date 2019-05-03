package mods.clayium.plugin.nei;

import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import mods.clayium.block.tile.TileChemicalMetalSeparator;
import mods.clayium.item.CMaterials;
import mods.clayium.util.crafting.WeightedList;
import mods.clayium.util.crafting.WeightedResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class NEIMetalSeparator
  extends NEIClayMachines
{
  public NEIMetalSeparator()
  {
    super("ChemicalMetalSeparator");
  }
  
  public class NEIRecipeEntryMetalSeparator
    extends NEIRecipeEntry
  {
    public double probability;
    
    public NEIRecipeEntryMetalSeparator(Object[] inputs, int method, int tier, ItemStack[] results, int energy, int time, double probability)
    {
      super(inputs, method, tier, results, energy, time);
      this.probability = probability;
    }
    
    public NEIRecipeEntryMetalSeparator(int inputs, int method, List<ItemStack> tier, int results, int energy, double time)
    {
      super(inputs, method, tier, results, energy, time);
      this.probability = probability;
    }
    
    public void drawExtras()
    {
      super.drawExtras();
      Minecraft.getMinecraft().fontRenderer.drawString(String.format("%3.2f%%", new Object[] { Double.valueOf(this.probability * 100.0D) }), 69, 10, -16777216);
    }
  }
  
  public class NEIRecipeComparator
    extends NEIClayMachines.NEIRecipeComparator
  {
    public NEIRecipeComparator()
    {
      super();
    }
    
    public int compare(TemplateRecipeHandler.CachedRecipe a1, TemplateRecipeHandler.CachedRecipe b1)
    {
      if ((!(a1 instanceof NEIRecipeEntryMetalSeparator)) || (!(b1 instanceof NEIRecipeEntryMetalSeparator))) {
        return 0;
      }
      NEIRecipeEntryMetalSeparator a = (NEIRecipeEntryMetalSeparator)a1;
      NEIRecipeEntryMetalSeparator b = (NEIRecipeEntryMetalSeparator)b1;
      if (a.probability < b.probability) {
        return 1;
      }
      if (b.probability < a.probability) {
        return -1;
      }
      return super.compare(a1, b1);
    }
  }
  
  private Comparator<TemplateRecipeHandler.CachedRecipe> comp = new NEIRecipeComparator();
  
  public Comparator<TemplateRecipeHandler.CachedRecipe> getComparator()
  {
    return this.comp;
  }
  
  public Iterable<INEIRecipeEntry> getMatchedSet()
  {
    List<INEIRecipeEntry> list = new ArrayList();
    WeightedList<ItemStack> recipeResults = TileChemicalMetalSeparator.results;
    for (int i = 0; i < recipeResults.size(); i++) {
      list.add(createRecipeCacher(recipeResults, i));
    }
    return list;
  }
  
  public List<Object> getMaterialList(WeightedList<ItemStack> recipeResults, int index)
  {
    List<Object> materiallist = new ArrayList();
    materiallist.add(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST));
    return materiallist;
  }
  
  public List<ItemStack> getResultList(WeightedList<ItemStack> recipeResults, int index)
  {
    List<ItemStack> resultlist = new ArrayList();
    resultlist.add(((WeightedResult)recipeResults.get(index)).result);
    return resultlist;
  }
  
  public NEIRecipeEntryMetalSeparator createRecipeCacher(WeightedList<ItemStack> recipeResults, int index)
  {
    List<Object> materiallist = getMaterialList(recipeResults, index);
    List<ItemStack> resultlist = getResultList(recipeResults, index);
    
    return new NEIRecipeEntryMetalSeparator(materiallist, 0, 6, resultlist, TileChemicalMetalSeparator.baseConsumingEnergy, TileChemicalMetalSeparator.baseCraftTime, ((WeightedResult)recipeResults.get(index)).weight / recipeResults.getWeightSum());
  }
  
  public TemplateRecipeHandler newInstance()
  {
    return new NEIMetalSeparator();
  }
}
