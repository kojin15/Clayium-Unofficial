package mods.clayium.plugin.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mods.clayium.block.CBlocks;
import mods.clayium.block.tile.TileQuartzCrucible;
import mods.clayium.block.tile.TileSaltExtractor;
import mods.clayium.block.tile.TileSolarClayFabricator;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.FDText;
import mods.clayium.gui.client.GuiClayCraftingTable;
import mods.clayium.gui.client.GuiClayMachines;
import mods.clayium.gui.container.ContainerClayCraftingTable;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.UtilTier;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LoadNEIConfig
{
  public static void load()
  {
    API.registerRecipeHandler(new NEIShapelessSpecialResultRecipe());
    API.registerUsageHandler(new NEIShapelessSpecialResultRecipe());
    for (Iterator<Entry<String, Recipes>> iterator = Recipes.RecipeMap.entrySet().iterator(); iterator.hasNext();)
    {
      entry = (Entry)iterator.next();
      recipes = (Recipes)entry.getValue();
      
      catchRecipe = new NEIClayMachines(recipes);
      if (entry.getKey().equals("Smelter")) {
        catchRecipe.setTierManager(UtilTier.tierSmelter);
      }
      GuiCraftingRecipe.craftinghandlers.add(catchRecipe);
      GuiUsageRecipe.usagehandlers.add(catchRecipe);
      
      API.registerGuiOverlay(GuiClayMachines.class, catchRecipe.getOverlayIdentifier(), 0, 0);
    }
    TemplateRecipeHandler[] catchRecipes = { new NEIClayWorkTable(), new NEIMetalSeparator() };
    
    Entry entry = catchRecipes;Recipes recipes = entry.length;
    for (NEIClayMachines catchRecipe = 0; catchRecipe < recipes; catchRecipe++)
    {
      TemplateRecipeHandler catchRecipe = entry[catchRecipe];
      GuiCraftingRecipe.craftinghandlers.add(catchRecipe);
      GuiUsageRecipe.usagehandlers.add(catchRecipe);
      API.registerGuiOverlay(catchRecipe.getGuiClass(), catchRecipe.getOverlayIdentifier(), 0, 0);
    }
    API.registerGuiOverlay(GuiClayCraftingTable.class, "crafting");
    API.registerGuiOverlayHandler(GuiClayCraftingTable.class, new DefaultOverlayHandler()
    {
      public boolean canMoveFrom(Slot slot, GuiContainer gui)
      {
        if ((gui.inventorySlots instanceof ContainerClayCraftingTable))
        {
          IInventory[] inventories = ((ContainerClayCraftingTable)gui.inventorySlots).inventories;
          for (int i = 1; i < inventories.length; i++) {
            if (slot.inventory == inventories[i]) {
              return true;
            }
          }
        }
        return slot.inventory instanceof InventoryPlayer;
      }
    }, "crafting");
    
    int posX = 6;
    int posuY = 23;
    int posY = 43;
    int posdY = 9;
    int width = 158;
    int colorBlack = -16777216;
    int colorGray = 4210752;
    
    NEIDescription description = new NEIDescription("CobblestoneGenerator", 1);
    GuiCraftingRecipe.craftinghandlers.add(description);
    GuiUsageRecipe.usagehandlers.add(description);
    
    List<INEIRecipeEntry> list = description.getEntryList();
    ItemStack[] cs = { new ItemStack(Blocks.cobblestone) };
    
    int[] effciencies = { 0, 2, 5, 15, 50, 200, 1000, 8000 };
    for (int i = 1; i <= 7; i++)
    {
      List<PositionedStack> machineList = new ArrayList();
      ItemStack[] is = { new ItemStack(CBlocks.blocksCobblestoneGenerator[i]) };
      machineList.add(new PositionedStack(is, 75, 5)); NEIDescription 
        tmp422_420 = description;tmp422_420.getClass();NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(tmp422_420, is, machineList, description.generateResultPositionedStacks(cs));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.CobblestoneGenerator.efficiency", new Object[] { Double.valueOf(ClayiumCore.multiplyProgressionRateI(effciencies[i]) / 100.0D) })), posX, posY, colorBlack, width));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.CobblestoneGenerator.description", new Object[0])), posX, posY + posdY, colorGray, width));
      list.add(entry);
    }
    description = new NEIDescription("SaltExtractor", 1);
    GuiCraftingRecipe.craftinghandlers.add(description);
    GuiUsageRecipe.usagehandlers.add(description);
    
    list = description.getEntryList();
    ItemStack[] salt = { CMaterials.get(CMaterials.SALT, CMaterials.DUST) };
    for (int i = 4; i <= 7; i++)
    {
      List<PositionedStack> machineList = new ArrayList();
      ItemStack[] is = { new ItemStack(CBlocks.blocksSaltExtractor[i]) };
      machineList.add(new PositionedStack(is, 75, 5)); NEIDescription 
        tmp681_679 = description;tmp681_679.getClass();NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(tmp681_679, is, machineList, description.generateResultPositionedStacks(salt));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.SaltExtractor.efficiency", new Object[] { Double.valueOf(ClayiumCore.multiplyProgressionRateI(effciencies[i]) / 100.0D) })), posX, posY, colorBlack, width));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.SaltExtractor.energyConsumption", new Object[] { UtilLocale.ClayEnergyNumeral(ClayiumCore.multiplyProgressionRateI(effciencies[i]) * TileSaltExtractor.energyPerWork) })), posX, posY + posdY, colorBlack, width));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.SaltExtractor.description", new Object[0])), posX, posY + posdY * 2, colorGray, width));
      list.add(entry);
    }
    description = new NEIDescription("QuartzCrucible", 1);
    GuiCraftingRecipe.craftinghandlers.add(description);
    GuiUsageRecipe.usagehandlers.add(description);
    
    list = description.getEntryList();
    for (int i = 1; i <= 9; i++)
    {
      ItemStack[] toSeacrh = { new ItemStack(CBlocks.blockQuartzCrucible), CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT, i), new ItemStack(Items.string) };
      
      List<PositionedStack> ingredList = new ArrayList();
      ItemStack[] is = { new ItemStack(CBlocks.blockQuartzCrucible) };
      ingredList.add(new PositionedStack(is, 75, 5));
      ItemStack[] ingredItems = { CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT, i), new ItemStack(Items.string) };
      ingredList.addAll(description.generateIngredientPositionedStacks(ingredItems));
      
      ItemStack[] resultItems = { CMaterials.get(CMaterials.SILICON, CMaterials.INGOT, i) }; NEIDescription 
      
        tmp1100_1098 = description;tmp1100_1098.getClass();NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(tmp1100_1098, toSeacrh, ingredList, description.generateResultPositionedStacks(resultItems));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.QuartzCrucible.timeToCraft", new Object[] { Integer.valueOf(TileQuartzCrucible.timeToCraft * i), Integer.valueOf(TileQuartzCrucible.timeToCraft * i / 20) })), posX, posY, colorBlack, width));
      entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.QuartzCrucible.description", new Object[0])), posX, posY + posdY, colorGray, width));
      list.add(entry);
    }
    description = new NEIDescription("SolarClayFabricator", 2);
    GuiCraftingRecipe.craftinghandlers.add(description);
    GuiUsageRecipe.usagehandlers.add(description);
    
    list = description.getEntryList();
    Block[] blocks = new Block[16];
    blocks[5] = CBlocks.blockSolarClayFabricatorMK1;
    blocks[6] = CBlocks.blockSolarClayFabricatorMK2;
    blocks[7] = CBlocks.blockLithiumSolarClayFabricator;
    int[] acceptableTiers = { 0, 0, 0, 0, 0, 4, 6, 9 };
    float[] baseCraftTimes = { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 2.0F };
    float[] efficiencies = { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5000.0F, 50000.0F, 3000000.0F };
    List<Object>[] compressedClay = new List[13];
    for (int t = 0; t <= 12; t++)
    {
      List<Object> clist = new ArrayList();
      clist.add(TileSolarClayFabricator.getCompressedClay(t));
      if (t == 2) {
        clist.add(new ItemStack(Blocks.sand));
      }
      if (t == 8) {
        clist.add(CMaterials.getOD(CMaterials.LITHIUM, CMaterials.INGOT));
      }
      compressedClay[t] = clist;
    }
    for (int i = 5; i <= 7; i++)
    {
      ItemStack machine = new ItemStack(blocks[i]);
      
      float multCraftTime = (float)(Math.pow(10.0D, acceptableTiers[i] + 1) * (baseCraftTimes[i] - 1.0F) / (baseCraftTimes[i] * (Math.pow(baseCraftTimes[i], acceptableTiers[i]) - 1.0D)) / (ClayiumCore.multiplyProgressionRateF(efficiencies[i]) / 20.0F));
      long energy;
      for (int t = 0; t <= acceptableTiers[i]; t++)
      {
        energy = (t + 1 < 5 ? 0.0D : Math.pow(10.0D, t + 1));
        for (Object clay : compressedClay[t])
        {
          Object[] toSeacrh = { machine, clay };
          
          List<PositionedStack> ingredList = new ArrayList();
          ItemStack[] is = { machine };
          ingredList.add(new PositionedStack(is, 75, 5));
          Object[] ingredItems = { clay };
          ingredList.addAll(description.generateIngredientPositionedStacks(ingredItems));
          
          ItemStack[] resultItems = { TileSolarClayFabricator.getCompressedClay(t + 1) };
          
          long timeToCraft = (Math.pow(baseCraftTimes[i], t) * multCraftTime); NEIDescription 
            tmp1829_1827 = description;tmp1829_1827.getClass();NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(tmp1829_1827, toSeacrh, ingredList, description.generateResultPositionedStacks(resultItems));
          entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.SolarClayFabricator.timeToCraft", new Object[] { UtilLocale.craftTimeNumeral(timeToCraft), Long.valueOf(timeToCraft / 20L), UtilLocale.ClayEnergyNumeral(energy / timeToCraft) })), posX, posY, colorBlack, width));
          entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.SolarClayFabricator.description", new Object[0])), posX, posY + posdY, colorGray, width));
          
          list.add(entry);
        }
      }
    }
    description = new NEIDescription("ClayTree", "clayium:textures/gui/back.png", null, 1);
    GuiCraftingRecipe.craftinghandlers.add(description);
    GuiUsageRecipe.usagehandlers.add(description);
    
    list = description.getEntryList();
    
    List<PositionedStack> treeList = new ArrayList();
    ItemStack[] is = { new ItemStack(CBlocks.blockClayTreeSapling), new ItemStack(CBlocks.blockClayTreeLog), new ItemStack(CBlocks.blockClayTreeLeaf) };
    treeList.add(new PositionedStack(is, 75, 5)); NEIDescription 
      tmp2111_2109 = description;tmp2111_2109.getClass();NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(tmp2111_2109, new Object[0], new ArrayList(), treeList, false);
    entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.format("recipe.ClayTree.description", new Object[0])), posX, posuY, colorGray, width));
    list.add(entry);
  }
}
