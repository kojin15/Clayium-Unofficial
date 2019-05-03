package mods.clayium.block.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import mods.clayium.block.CBlocks;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.item.CShape;
import mods.clayium.pan.IPANComponent;
import mods.clayium.pan.IPANConversion;
import mods.clayium.pan.IPANConversionProvider;
import mods.clayium.pan.UtilPAN;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.WeightedList;
import mods.clayium.util.crafting.WeightedResult;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class TilePANCore
  extends TileClayContainer
{
  public static int refreshRate = 200;
  protected static Random random = new Random();
  protected int refreshPhase = 0;
  protected long lastTotalWorldTime = -1L;
  protected Set<ItemStackWithEnergy> conversionItemSet;
  protected Set<ItemStackWithEnergy> ingredientItemSet;
  protected Set<ItemStackWithEnergy> prohibitedItemSet;
  public List<IPANConversion> conversionList;
  private Set<Vec> conductors;
  private int depth;
  private int num;
  
  public TilePANCore()
  {
    this.insertRoutes = new int[] { -1, -1, -1, 0, -1, -1 };
    this.extractRoutes = new int[] { -1, -1, -1, -1, -1, -1 };
    this.autoInsert = true;
    this.autoExtract = true;
    this.containerItemStacks = new ItemStack[1];
    
    this.maxAutoInsertDefault = (this.maxAutoExtractDefault = 64);
    this.autoExtractInterval = (this.autoInsertInterval = 1);
    
    this.listSlotsInsert.clear();
    this.listSlotsExtract.clear();
    this.listSlotsInsert.add(new int[] { 0 });
    
    this.refreshPhase = random.nextInt(refreshRate);
  }
  
  public Set<ItemStackWithEnergy> getConversionItemSet()
  {
    return this.conversionItemSet;
  }
  
  public Set<ItemStackWithEnergy> getIngredientItemSet()
  {
    return this.ingredientItemSet;
  }
  
  public Set<ItemStackWithEnergy> getProhibitedItemSet()
  {
    return this.prohibitedItemSet;
  }
  
  public static int maxDepth = 1000;
  public static int maxNum = 10000;
  
  public void updateEntity()
  {
    super.updateEntity();
    if (this.lastTotalWorldTime != -1L)
    {
      this.refreshPhase -= (int)(this.worldObj.getTotalWorldTime() - this.lastTotalWorldTime);
      if (this.refreshPhase > refreshRate) {
        this.refreshPhase = refreshRate;
      }
    }
    if (this.worldObj != null) {
      this.lastTotalWorldTime = this.worldObj.getTotalWorldTime();
    }
    if (this.refreshPhase <= 0)
    {
      this.refreshPhase = refreshRate;
      refreshNetwork();
      if (getConversionItemSet() == null) {
        refreshItemSet();
      }
    }
  }
  
  public void refreshNetwork()
  {
    if (!this.worldObj.isRemote)
    {
      Vec v = Vec.get(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
      this.conductors = new TreeSet(new DistanceComparator(v));this.depth = 0;this.num = 0;
      this.conversionList = new ArrayList();
      
      search(v);
      for (Vec conductor : this.conductors)
      {
        TileEntity te = getTileEntity(conductor);
        if ((!(te instanceof TilePANCore)) || 
        
          ((te instanceof IPANConversionProvider)))
        {
          IPANConversion[] conversions = ((IPANConversionProvider)te).getConversion();
          if (conversions != null) {
            this.conversionList.addAll(Arrays.asList(conversions));
          }
        }
        if ((te instanceof IPANComponent)) {
          ((IPANComponent)te).setPANCore(this, refreshRate * 2);
        }
      }
    }
  }
  
  public void refreshItemSet()
  {
    if (this.worldObj.isRemote) {
      return;
    }
    Set<ItemStackWithEnergy> clays = new TreeSet(new ItemStackComparator());
    double d0 = 1.0D;
    clays.add(new ItemStackWithEnergy(new ItemStack(Blocks.clay), d0, d0));
    for (int i = 0; i < 13; i++)
    {
      d0 *= 10.0D;
      clays.add(new ItemStackWithEnergy(new ItemStack(CBlocks.blockCompressedClay, 1, i), d0, d0));
    }
    Set<ItemStackWithEnergy> basematerials = new TreeSet(new ItemStackComparator());
    d0 = TileCobblestoneGenerator.progressMax * TileSaltExtractor.energyPerWork / 6;
    basematerials.add(new ItemStackWithEnergy(CMaterials.get(CMaterials.SALT, CMaterials.DUST), d0, d0));
    basematerials.add(new ItemStackWithEnergy(CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 100000.0D, 100000.0D));
    
    Set<ItemStackWithEnergy> prohibiteds = new TreeSet(new ItemStackComparator());
    for (CMaterial material : new CMaterial[] { CMaterials.CLAY, CMaterials.DENSE_CLAY, CMaterials.IND_CLAY, CMaterials.ADVIND_CLAY, CMaterials.ANTIMATTER, CMaterials.OCTUPLE_CLAY, CMaterials.COMPRESSED_PURE_ANTIMATTER[0], CMaterials.COMPRESSED_PURE_ANTIMATTER[1], CMaterials.COMPRESSED_PURE_ANTIMATTER[2], CMaterials.COMPRESSED_PURE_ANTIMATTER[3], CMaterials.COMPRESSED_PURE_ANTIMATTER[4], CMaterials.COMPRESSED_PURE_ANTIMATTER[5], CMaterials.COMPRESSED_PURE_ANTIMATTER[6], CMaterials.COMPRESSED_PURE_ANTIMATTER[7], CMaterials.COMPRESSED_PURE_ANTIMATTER[8] }) {
      for (CShape shape : new CShape[] { CMaterials.PLATE, CMaterials.STICK, CMaterials.SHORT_STICK, CMaterials.RING, CMaterials.SMALL_RING, CMaterials.GEAR, CMaterials.BLADE, CMaterials.NEEDLE, CMaterials.DISC, CMaterials.SMALL_DISC, CMaterials.CYLINDER, CMaterials.PIPE, CMaterials.LARGE_BALL, CMaterials.LARGE_PLATE, CMaterials.GRINDING_HEAD, CMaterials.BEARING, CMaterials.SPINDLE, CMaterials.CUTTING_HEAD, CMaterials.WATER_WHEEL, CMaterials.BLOCK, CMaterials.BALL, CMaterials.DUST, CMaterials.INGOT, CMaterials.GEM }) {
        if (CMaterials.exist(material, shape)) {
          prohibiteds.add(new ItemStackWithEnergy(CMaterials.get(material, shape), 0.0D, 0.0D));
        }
      }
    }
    Object impuredusts = new TreeSet(new ItemStackComparator());
    Object recipeResults = TileChemicalMetalSeparator.results;
    double base = TileChemicalMetalSeparator.baseConsumingEnergy * TileChemicalMetalSeparator.baseCraftTime + 1000;
    for (??? = ((WeightedList)recipeResults).iterator(); ((Iterator)???).hasNext();)
    {
      Object result = (WeightedResult)((Iterator)???).next();
      double rate = ((WeightedResult)result).weight / ((WeightedList)recipeResults).getWeightSum();
      ((Set)impuredusts).add(new ItemStackWithEnergy((ItemStack)((WeightedResult)result).result, base / rate, base / rate));
    }
    Object list = new TreeSet(new ItemStackComparator());
    ((Set)list).add(new ItemStackWithEnergy(null, 0.0D, 0.0D));
    ((Set)list).add(new ItemStackWithEnergy(new ItemStack(Blocks.cobblestone), 1.0D, 1.0D));
    ((Set)list).add(new ItemStackWithEnergy(new ItemStack(Blocks.log), 1.0D, 1.0D));
    
    ((Set)list).addAll(clays);
    ((Set)list).addAll((Collection)impuredusts);
    ((Set)list).addAll(basematerials);
    
    boolean flag = true;
    label1232:
    label1256:
    label1262:
    while (flag)
    {
      flag = false;
      for (int i = 0;; i++)
      {
        if (i >= this.conversionList.size()) {
          break label1262;
        }
        double cost = 0.0D;
        double consumption = 0.0D;
        IItemPattern[] patterns = ((IPANConversion)this.conversionList.get(i)).getPatterns();
        IItemPattern[] arrayOfIItemPattern1 = patterns;int n = arrayOfIItemPattern1.length;
        for (IItemPattern localIItemPattern1 = 0; localIItemPattern1 < n; localIItemPattern1++)
        {
          pattern = arrayOfIItemPattern1[localIItemPattern1];
          if (pattern != null)
          {
            double mincost = 0.0D;
            double minconsumption = Double.MAX_VALUE;
            for (ItemStackWithEnergy ie : (Set)list)
            {
              int stackSize = 1;
              if ((pattern.match(ie.itemstack, false)) && (minconsumption > ie.consumption * (stackSize = pattern.getStackSize(ie.itemstack))))
              {
                minconsumption = ie.consumption * stackSize;
                mincost = ie.cost * stackSize;
              }
            }
            if (minconsumption == Double.MAX_VALUE) {
              break label1256;
            }
            cost += mincost;
            consumption += minconsumption;
          }
        }
        ItemStack[] results = ((IPANConversion)this.conversionList.get(i)).getResults();
        consumption += ((IPANConversion)this.conversionList.get(i)).getEnergy();
        
        ItemStack[] arrayOfItemStack1 = results;localIItemPattern1 = arrayOfItemStack1.length;
        for (IItemPattern pattern = 0; pattern < localIItemPattern1; pattern++)
        {
          ItemStack result = arrayOfItemStack1[pattern];
          for (ItemStackWithEnergy l : (Set)list) {
            if (UtilItemStack.areTypeEqual(result, l.itemstack)) {
              break label1232;
            }
          }
          ItemStack r = result.copy();
          r.stackSize = 1;
          ((Set)list).add(new ItemStackWithEnergy(r, cost / Math.max(result.stackSize, 1), consumption / Math.max(result.stackSize, 1)));
        }
        this.conversionList.remove(i);
        flag = true;
        break;
      }
    }
    Object list2 = new TreeSet(new ItemStackComparator());
    ((Set)list2).addAll((Collection)list);
    
    ((Set)list).removeAll(clays);
    prohibiteds.addAll(clays);
    ((Set)list).removeAll(prohibiteds);
    
    this.prohibitedItemSet = prohibiteds;
    this.ingredientItemSet = ((Set)list2);
    this.conversionItemSet = ((Set)list);
  }
  
  public void debug(EntityPlayer player)
  {
    if (!player.worldObj.isRemote)
    {
      refreshNetwork();
      refreshItemSet();
      ItemStackWithEnergy localItemStackWithEnergy;
      for (Iterator localIterator = this.conversionItemSet.iterator(); localIterator.hasNext(); localItemStackWithEnergy = (ItemStackWithEnergy)localIterator.next()) {}
    }
  }
  
  public static class ItemStackWithEnergy
  {
    public double cost;
    public double consumption;
    public ItemStack itemstack;
    
    public ItemStackWithEnergy(ItemStack itemstack, double cost, double consumption)
    {
      this.cost = cost;
      this.consumption = consumption;
      this.itemstack = (itemstack != null ? itemstack.copy() : null);
    }
    
    public String toString()
    {
      return "[ " + (this.itemstack == null ? "null" : this.itemstack.getDisplayName()) + " : " + UtilLocale.ClayEnergyNumeral(this.cost) + "CE : " + UtilLocale.ClayEnergyNumeral(this.consumption) + "CE ]";
    }
  }
  
  public static class ItemStackComparator
    implements Comparator<ItemStackWithEnergy>
  {
    public int compare(ItemStackWithEnergy o1, ItemStackWithEnergy o2)
    {
      ItemStack s1 = o1.itemstack;
      ItemStack s2 = o2.itemstack;
      if ((s1 == null) && (s2 == null)) {
        return 0;
      }
      if (s1 == null) {
        return -1;
      }
      if (s2 == null) {
        return 1;
      }
      int i1 = Item.getIdFromItem(s1.getItem());
      int i2 = Item.getIdFromItem(s2.getItem());
      if (i1 < i2) {
        return -1;
      }
      if (i1 > i2) {
        return 1;
      }
      if (s1.getItemDamage() < s2.getItemDamage()) {
        return -1;
      }
      if (s1.getItemDamage() > s2.getItemDamage()) {
        return 1;
      }
      return 0;
    }
  }
  
  public void search(Vec v)
  {
    this.depth += 1;this.num += 1;
    this.conductors.add(v);
    if ((this.depth < maxDepth) && (this.num < maxNum)) {
      for (Vec u : getConnection(v)) {
        if (!this.conductors.contains(u)) {
          search(u);
        }
      }
    }
    this.depth -= 1;
  }
  
  public static Vec[] getConnection(Vec v)
  {
    List<Vec> neighbors = new ArrayList(Arrays.asList(getNeighbor(v)));
    
    List<Vec> res = new ArrayList();
    for (Vec neighbor : neighbors) {
      if (isConductor(neighbor)) {
        res.add(neighbor);
      }
    }
    return (Vec[])res.toArray(new Vec[0]);
  }
  
  public static boolean isConductor(Vec v)
  {
    return UtilPAN.isPANConductor(DimensionManager.getWorld(v.d), v.x, v.y, v.z);
  }
  
  public static Block getBlock(Vec v)
  {
    World world = DimensionManager.getWorld(v.d);
    return world == null ? null : world.getBlock(v.x, v.y, v.z);
  }
  
  public static TileEntity getTileEntity(Vec v)
  {
    World world = DimensionManager.getWorld(v.d);
    return world == null ? null : UtilBuilder.safeGetTileEntity(world, v.x, v.y, v.z);
  }
  
  public static Vec[] getNeighbor(Vec v)
  {
    return new Vec[] { Vec.get(v.x + 1, v.y, v.z, v.d), Vec.get(v.x - 1, v.y, v.z, v.d), Vec.get(v.x, v.y + 1, v.z, v.d), Vec.get(v.x, v.y - 1, v.z, v.d), Vec.get(v.x, v.y, v.z + 1, v.d), Vec.get(v.x, v.y, v.z - 1, v.d) };
  }
  
  public void openInventory() {}
  
  public void closeInventory() {}
  
  public static class DistanceComparator
    implements Comparator<Vec>
  {
    protected Vec center;
    
    public DistanceComparator(Vec center)
    {
      this.center = center;
    }
    
    public int compare(Vec o1, Vec o2)
    {
      double d1 = dimDistance(o1);
      double d2 = dimDistance(o2);
      if (d1 < d2) {
        return -1;
      }
      if (d1 > d2) {
        return 1;
      }
      d1 = coordDistance(o1);
      d2 = coordDistance(o2);
      if (d1 < d2) {
        return -1;
      }
      if (d1 > d2) {
        return 1;
      }
      if (o1.x < o2.x) {
        return -1;
      }
      if (o1.x > o2.x) {
        return 1;
      }
      if (o1.z < o2.z) {
        return -1;
      }
      if (o1.z > o2.z) {
        return 1;
      }
      if (o1.y < o2.y) {
        return -1;
      }
      if (o1.y > o2.y) {
        return 1;
      }
      return 0;
    }
    
    protected double dimDistance(Vec o1)
    {
      return o1.d >= this.center.d ? o1.d - this.center.d : this.center.d - o1.d - 0.5D;
    }
    
    protected double coordDistance(Vec o1)
    {
      double dx = o1.x - this.center.x;
      double dy = o1.y - this.center.y;
      double dz = o1.z - this.center.z;
      return dx * dx + dy * dy + dz * dz;
    }
  }
  
  public static class Vec
  {
    public int x;
    public int y;
    public int z;
    public int d;
    
    private Vec(int x, int y, int z, int d)
    {
      this.x = x;this.y = y;this.z = z;this.d = d;
    }
    
    public static Vec get(int x, int y, int z, int d)
    {
      return new Vec(x, y, z, d);
    }
    
    public boolean equals(Object object)
    {
      return (this.x == ((Vec)object).x) && (this.y == ((Vec)object).y) && (this.z == ((Vec)object).z) && (this.d == ((Vec)object).d);
    }
    
    public int hashCode()
    {
      return this.x + 23059 * this.y + 668243 * this.z + 9118819 * this.d;
    }
    
    public String toString()
    {
      return "(" + this.x + "," + this.y + "," + this.z + ";" + this.d + ")";
    }
  }
}
