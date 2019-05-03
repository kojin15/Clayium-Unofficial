package mods.clayium.plugin.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mods.clayium.gui.FDText;
import mods.clayium.gui.FDText.FDTextHandler;
import mods.clayium.gui.IFunctionalDrawer;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public abstract class NEITemp
  extends TemplateRecipeHandler
{
  public List<PositionedStack> generateIngredientPositionedStacks(Object[] ingredients)
  {
    List<PositionedStack> inputList = new ArrayList();
    for (int i = 0; i < ingredients.length; i++) {
      inputList.add(generatePermutationsFixed(new PositionedStack(UtilItemStack.object2ItemStacks(ingredients[i]), 52 - i * 17, 21, false)));
    }
    return inputList;
  }
  
  public List<PositionedStack> generateResultPositionedStacks(ItemStack[] results)
  {
    List<PositionedStack> resultList = new ArrayList();
    for (int i = 0; i < results.length; i++) {
      resultList.add(new PositionedStack(results[i], 98 + i * 17, 21));
    }
    return resultList;
  }
  
  public abstract class NEIEntryWithFD
    extends TemplateRecipeHandler.CachedRecipe
    implements INEIRecipeEntry
  {
    public NEIEntryWithFD()
    {
      super();
    }
    
    protected TemplateRecipeHandler handler = NEITemp.this;
    protected List<IFunctionalDrawer<Object>> fdList = new ArrayList();
    
    public List<IFunctionalDrawer<Object>> getFDList()
    {
      return this.fdList;
    }
    
    public void setFDList(List<IFunctionalDrawer<Object>> fdList)
    {
      this.fdList = fdList;
    }
    
    public void drawExtras()
    {
      for (IFunctionalDrawer<Object> fd : this.fdList) {
        fd.draw(this);
      }
    }
    
    public void setHandler(TemplateRecipeHandler handler)
    {
      this.handler = handler;
    }
    
    public TemplateRecipeHandler getHandler()
    {
      return this.handler;
    }
  }
  
  public IFunctionalDrawer<Object> drawerProgressBar = new IFunctionalDrawer()
  {
    public Object draw(Object param)
    {
      if ((param instanceof INEIRecipeEntry))
      {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture("clayium:textures/gui/progressbarfurnace.png");
        ((INEIRecipeEntry)param).getHandler().drawProgressBar(71, 21, 0, 0, 24, 17, 40, 0);
      }
      return param;
    }
  };
  
  public static class NEITextSetterTier<T>
    extends FDTextHandler<T>
  {
    public NEITextSetterTier()
    {
      this.handleString = true;
    }
    
    public String applyString(T param)
    {
      int tier = getTier(param);
      return "Tier: " + tier;
    }
    
    public int getTier(T param)
    {
      return (param instanceof INEIEntryTiered) ? ((INEIEntryTiered)param).getTier() : -1;
    }
  }
  
  public static FDTextHandler<Object> setterTier = new NEITextSetterTier();
  public static IFunctionalDrawer<Object> drawerTier = FDText.INSTANCE.getFDText(setterTier, 6, 43, -16777216, -1);
  
  public static class NEITextSetterEnergy<T>
    extends FDTextHandler<T>
  {
    public NEITextSetterEnergy()
    {
      this.handleString = true;
    }
    
    public String applyString(T param)
    {
      long energy = getEnergy(param);
      long time = getTime(param);
      if (energy < 0L)
      {
        if (time < 0L) {
          return "";
        }
        return UtilLocale.craftTimeNumeral(time) + "t";
      }
      if (time < 0L) {
        return UtilLocale.ClayEnergyNumeral(energy) + "CE";
      }
      return UtilLocale.ClayEnergyNumeral(energy) + "CE/t x " + UtilLocale.craftTimeNumeral(time) + "t = " + UtilLocale.ClayEnergyNumeral(energy * time) + "CE";
    }
    
    public long getEnergy(T param)
    {
      return (param instanceof INEIEntryEnergy) ? ((INEIEntryEnergy)param).getEnergy() : -1L;
    }
    
    public long getTime(T param)
    {
      return (param instanceof INEIEntryEnergy) ? ((INEIEntryEnergy)param).getTime() : -1L;
    }
  }
  
  public static FDTextHandler<Object> setterEnergy = new NEITextSetterEnergy();
  public static IFunctionalDrawer<Object> drawerEnergy = FDText.INSTANCE.getFDText(setterEnergy, 6, 52, -16777216, -1);
  
  public static abstract interface INEIEntryTiered
  {
    public abstract int getTier();
  }
  
  public static abstract interface INEIEntryEnergy
  {
    public abstract long getEnergy();
    
    public abstract long getTime();
  }
  
  public class NEITemplateEntry
    extends NEIEntryWithFD
    implements INEIRecipeEntry
  {
    protected List<PositionedStack> inputs;
    protected List<PositionedStack> results;
    protected Object[] inputObjects;
    
    public NEITemplateEntry(Object[] inputs, ItemStack[] results)
    {
      this(inputs, NEITemp.this.generateIngredientPositionedStacks(inputs), NEITemp.this.generateResultPositionedStacks(results));
    }
    
    public NEITemplateEntry(List<PositionedStack> inputObjects, List<PositionedStack> inputs, boolean results)
    {
      super();
      this.inputObjects = inputObjects;
      this.inputs = inputs;
      this.results = results;
      
      List<IFunctionalDrawer<Object>> list = getFDList();
      if (drawProgressBar) {
        list.add(NEITemp.this.drawerProgressBar);
      }
      list.add(NEITemp.drawerTier);
      list.add(NEITemp.drawerEnergy);
    }
    
    public NEITemplateEntry(List<PositionedStack> inputObjects, List<PositionedStack> inputs)
    {
      this(inputObjects, inputs, results, true);
    }
    
    public PositionedStack getResult()
    {
      return null;
    }
    
    public List<PositionedStack> getIngredients()
    {
      return getCycledIngredients(NEITemp.this.cycleticks / 10, this.inputs);
    }
    
    public List<PositionedStack> getIngredientsToSort()
    {
      return this.inputs;
    }
    
    public List<PositionedStack> getOtherStacks()
    {
      return this.results;
    }
    
    public void computeVisuals()
    {
      for (PositionedStack p : this.inputs) {
        NEITemp.generatePermutationsFixed(p);
      }
    }
    
    public TemplateRecipeHandler.CachedRecipe asCachedRecipe()
    {
      return this;
    }
    
    public boolean matchForUsageRecipe(ItemStack ingredient)
    {
      if (ingredient == null) {
        return false;
      }
      ItemStack material2 = ingredient.copy();material2.stackSize = material2.getMaxStackSize();
      for (Object material1 : this.inputObjects) {
        if ((material1 != null) && 
          (Recipes.canBeCraftedOD(material2, material1))) {
          return true;
        }
      }
      return false;
    }
    
    public boolean matchForCraftingRecipe(ItemStack result)
    {
      for (PositionedStack result0 : getOtherStacks()) {
        for (ItemStack result_ : result0.items) {
          if (((result_ instanceof ItemStack)) && (
            (UtilItemStack.areItemDamageEqualOrDamageable(result_, result)) || (UtilItemStack.haveSameOD(result_, result)))) {
            return true;
          }
        }
      }
      return false;
    }
  }
  
  public static PositionedStack generatePermutationsFixed(PositionedStack p)
  {
    if ((p.items == null) || (p.items.length == 0)) {
      return p;
    }
    int stacksize = p.items[0].stackSize;
    p.generatePermutations();
    for (ItemStack item : p.items) {
      item.stackSize = stacksize;
    }
    return p;
  }
  
  public static boolean isAvailable(Object[] objects)
  {
    if ((objects == null) || (objects.length == 0)) {
      return false;
    }
    for (Object object : objects)
    {
      ItemStack[] itemstacks = UtilItemStack.object2ItemStacks(object);
      if ((itemstacks == null) || (itemstacks.length == 0)) {
        return false;
      }
    }
    return true;
  }
  
  public abstract Comparator<TemplateRecipeHandler.CachedRecipe> getComparator();
  
  public static class NEIEntryComparator
    implements Comparator<TemplateRecipeHandler.CachedRecipe>
  {
    public int compare(TemplateRecipeHandler.CachedRecipe a1, TemplateRecipeHandler.CachedRecipe b1)
    {
      if (((a1 instanceof INEIEntryTiered)) && ((b1 instanceof INEIEntryTiered)))
      {
        int a = ((INEIEntryTiered)a1).getTier();
        int b = ((INEIEntryTiered)b1).getTier();
        if (a > b) {
          return 1;
        }
        if (b > a) {
          return -1;
        }
      }
      if (((a1 instanceof INEIEntryEnergy)) && ((b1 instanceof INEIEntryEnergy)))
      {
        long ae = ((INEIEntryEnergy)a1).getEnergy();
        long be = ((INEIEntryEnergy)b1).getEnergy();
        long at = ((INEIEntryEnergy)a1).getTime();
        long bt = ((INEIEntryEnergy)b1).getTime();
        double a = ae * at;
        double b = be * bt;
        if (a > b) {
          return 1;
        }
        if (b > a) {
          return -1;
        }
        if (ae > be) {
          return 1;
        }
        if (be > ae) {
          return -1;
        }
        if (at > bt) {
          return 1;
        }
        if (bt > at) {
          return -1;
        }
      }
      if (((a1 instanceof INEIRecipeEntry)) && ((b1 instanceof INEIRecipeEntry))) {
        return NEITemp.getStackComparator().compare(((INEIRecipeEntry)a1).getIngredientsToSort(), ((INEIRecipeEntry)b1).getIngredientsToSort());
      }
      return 0;
    }
  }
  
  private static final StackComparator defaultStackComparator = new StackComparator();
  
  public static StackComparator getStackComparator()
  {
    return defaultStackComparator;
  }
  
  public abstract Iterable<INEIRecipeEntry> getMatchedSet();
  
  public static class StackComparator
    implements Comparator<List<PositionedStack>>
  {
    public int compare(List<PositionedStack> la, List<PositionedStack> lb)
    {
      if ((la == null) && (lb == null)) {
        return 0;
      }
      if (lb == null) {
        return 1;
      }
      if (la == null) {
        return -1;
      }
      if (la.size() > lb.size()) {
        return 1;
      }
      if (lb.size() > la.size()) {
        return -1;
      }
      int[] aa = getMaxId(la);
      int[] bb = getMaxId(lb);
      if (aa[0] > bb[0]) {
        return 1;
      }
      if (bb[0] > aa[0]) {
        return -1;
      }
      if (aa[1] > bb[1]) {
        return 1;
      }
      if (bb[1] > aa[1]) {
        return -1;
      }
      if (aa[2] > bb[2]) {
        return 1;
      }
      if (bb[2] > aa[2]) {
        return -1;
      }
      return 0;
    }
    
    private int[] getMaxId(List<PositionedStack> a)
    {
      int id = Integer.MIN_VALUE;
      int damage = Integer.MIN_VALUE;
      int stacksize = Integer.MIN_VALUE;
      for (PositionedStack s : a)
      {
        int[] m = getMin(s.items);
        if ((m[0] > id) || ((m[0] == id) && (m[1] > damage)) || ((m[0] == id) && (m[1] == damage) && (m[2] > stacksize)))
        {
          id = m[0];
          damage = m[1];
          stacksize = m[2];
        }
      }
      return new int[] { id, damage, stacksize };
    }
    
    private int[] getMin(ItemStack[] a)
    {
      int id = Integer.MAX_VALUE;
      int damage = Integer.MAX_VALUE;
      int stacksize = Integer.MAX_VALUE;
      for (ItemStack item : a)
      {
        int id1 = Item.getIdFromItem(item.getItem());
        int damage1 = item.getItemDamage();
        int stacksize1 = item.stackSize;
        if ((id1 < id) || ((id1 == id) && (damage1 < damage)) || ((id1 == id) && (damage1 == damage) && (stacksize1 < stacksize)))
        {
          id = id1;
          damage = damage1;
          stacksize = stacksize1;
        }
      }
      return new int[] { id, damage, stacksize };
    }
  }
  
  public boolean matchForOutputId(String outputId, Object... results)
  {
    return (outputId == null) || (outputId.equals(getOverlayIdentifier()));
  }
  
  public void loadCraftingRecipes(String outputId, Object... results)
  {
    if (matchForOutputId(outputId, results))
    {
      Iterable<INEIRecipeEntry> set = getMatchedSet();
      if (set == null) {
        return;
      }
      for (INEIRecipeEntry entry : getMatchedSet()) {
        this.arecipes.add(entry.asCachedRecipe());
      }
    }
    else
    {
      super.loadCraftingRecipes(outputId, results);
    }
    Collections.sort(this.arecipes, getComparator());
  }
  
  public void loadCraftingRecipes(ItemStack result)
  {
    Iterable<INEIRecipeEntry> set = getMatchedSet();
    if (set == null) {
      return;
    }
    for (INEIRecipeEntry entry : getMatchedSet()) {
      if (entry.matchForCraftingRecipe(result)) {
        this.arecipes.add(entry.asCachedRecipe());
      }
    }
    Collections.sort(this.arecipes, getComparator());
  }
  
  public void loadUsageRecipes(ItemStack ingredient)
  {
    if (ingredient == null) {
      return;
    }
    Iterable<INEIRecipeEntry> set = getMatchedSet();
    if (set == null) {
      return;
    }
    for (INEIRecipeEntry entry : getMatchedSet()) {
      if (entry.matchForUsageRecipe(ingredient)) {
        this.arecipes.add(entry.asCachedRecipe());
      }
    }
    Collections.sort(this.arecipes, getComparator());
  }
  
  public void drawExtras(int recipe)
  {
    TemplateRecipeHandler.CachedRecipe re = (TemplateRecipeHandler.CachedRecipe)this.arecipes.get(recipe);
    if ((re instanceof INEIRecipeEntry)) {
      ((INEIRecipeEntry)re).drawExtras();
    }
  }
  
  public void drawProgressBar(int x, int y, int tx, int ty, int w, int h, float completion, int direction)
  {
    GuiDraw.drawTexturedModalRect(x, y, tx, ty, w, h);
    super.drawProgressBar(x, y, tx, ty + h, w, h, completion, direction);
  }
  
  public String getRecipeName()
  {
    return I18n.format("recipe." + getOverlayIdentifier(), new Object[0]);
  }
  
  @Deprecated
  public static void drawTier(int tier)
  {
    Minecraft.getMinecraft().fontRenderer.drawString("Tier: " + tier, 6, 43, -16777216);
  }
  
  @Deprecated
  public static void drawEnergy(long energy, long time)
  {
    Minecraft.getMinecraft().fontRenderer.drawString(UtilLocale.ClayEnergyNumeral(energy) + "CE/t x " + UtilLocale.craftTimeNumeral(time) + "t = " + 
      UtilLocale.ClayEnergyNumeral(energy * time) + "CE", 6, 52, -16777216);
  }
}
