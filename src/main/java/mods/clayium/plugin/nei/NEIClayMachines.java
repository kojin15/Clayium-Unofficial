package mods.clayium.plugin.nei;

import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import mods.clayium.gui.client.GuiClayMachines;
import mods.clayium.util.UtilTier.TierManager;
import mods.clayium.util.crafting.Recipes;
import mods.clayium.util.crafting.Recipes.RecipeCondition;
import mods.clayium.util.crafting.Recipes.RecipeResult;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class NEIClayMachines
  extends NEITemp
{
  private Recipes recipes;
  public UtilTier.TierManager tierManager;
  private String overlayId;
  
  public NEIClayMachines(String overlayId)
  {
    this(overlayId, UtilTier.TierManager.getMachineTierManager());
  }
  
  public NEIClayMachines(String overlayId, UtilTier.TierManager tierManager)
  {
    this.recipes = null;
    this.overlayId = overlayId;
    this.tierManager = tierManager;
    loadTransferRects();
  }
  
  public NEIClayMachines(Recipes recipes)
  {
    this(recipes, UtilTier.TierManager.getMachineTierManager());
  }
  
  public NEIClayMachines(Recipes recipes, UtilTier.TierManager tierManager)
  {
    this(recipes.recipeid, tierManager);
    this.recipes = recipes;
  }
  
  public void setTierManager(UtilTier.TierManager tierManager)
  {
    if (tierManager != null) {
      this.tierManager = tierManager;
    }
  }
  
  public class NEIRecipeEntry
    extends NEITemplateEntry
    implements INEIEntryTiered, INEIEntryEnergy
  {
    public int method;
    public int tier;
    public long energy;
    public long time;
    
    public NEIRecipeEntry(Object[] inputs, int method, int tier, ItemStack[] results, long energy, long time)
    {
      super(inputs, results);
      this.method = method;this.tier = tier;this.energy = energy;this.time = time;
    }
    
    public NEIRecipeEntry(int inputs, int method, List<ItemStack> tier, long results, long arg7)
    {
      this(inputs.toArray(new Object[0]), method, tier, (ItemStack[])results.toArray(new ItemStack[0]), energy, time);
    }
    
    public long getEnergy()
    {
      return this.energy;
    }
    
    public long getTime()
    {
      return this.time;
    }
    
    public int getTier()
    {
      return this.tier;
    }
  }
  
  private Comparator<TemplateRecipeHandler.CachedRecipe> comp = new NEIRecipeComparator();
  
  public Comparator<TemplateRecipeHandler.CachedRecipe> getComparator()
  {
    return this.comp;
  }
  
  public class NEIRecipeComparator
    extends NEIEntryComparator
  {
    public NEIRecipeComparator() {}
    
    public int compare(TemplateRecipeHandler.CachedRecipe a1, TemplateRecipeHandler.CachedRecipe b1)
    {
      if (((a1 instanceof NEIRecipeEntry)) && ((b1 instanceof NEIRecipeEntry)))
      {
        NEIRecipeEntry a = (NEIRecipeEntry)a1;
        NEIRecipeEntry b = (NEIRecipeEntry)b1;
        if (a.method > b.method) {
          return 1;
        }
        if (b.method > a.method) {
          return -1;
        }
      }
      return super.compare(a1, b1);
    }
  }
  
  public Class<? extends GuiContainer> getGuiClass()
  {
    return GuiClayMachines.class;
  }
  
  public String getOverlayIdentifier()
  {
    return this.overlayId;
  }
  
  public void loadTransferRects()
  {
    if (this.overlayId != null) {
      this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(69, 20, 28, 18), this.overlayId, new Object[0]));
    }
  }
  
  public Iterable<INEIRecipeEntry> getMatchedSet()
  {
    if (this.recipes == null) {
      return null;
    }
    List<INEIRecipeEntry> list = new ArrayList();
    for (Entry<RecipeCondition, RecipeResult> entry : this.recipes.recipeResultMap.entrySet())
    {
      Object[] materiallist = ((RecipeCondition)entry.getKey()).getMaterials();
      ItemStack[] resultlist = ((RecipeResult)entry.getValue()).getResults();
      int method = ((RecipeCondition)entry.getKey()).getMethod();
      int tier = ((RecipeCondition)entry.getKey()).getTier();
      long energy = ((RecipeResult)entry.getValue()).getEnergy();
      long time = ((RecipeResult)entry.getValue()).getTime();
      if (NEITemp.isAvailable(materiallist))
      {
        NEIRecipeEntry arecipe = new NEIRecipeEntry(materiallist, method, tier, resultlist, ((float)energy * this.tierManager.getF("multConsumingEnergy", tier)), ((float)time * this.tierManager.getF("multCraftTime", tier)));
        arecipe.computeVisuals();
        list.add(arecipe);
      }
    }
    return list;
  }
  
  public String getRecipeName()
  {
    return I18n.format("recipe." + getOverlayIdentifier(), new Object[0]);
  }
  
  public String getGuiTexture()
  {
    return "clayium:textures/gui/nei.png";
  }
  
  public TemplateRecipeHandler newInstance()
  {
    return new NEIClayMachines(this.recipes, this.tierManager);
  }
}
