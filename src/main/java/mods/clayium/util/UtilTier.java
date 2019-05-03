package mods.clayium.util;

import java.util.HashMap;
import java.util.Map.Entry;
import mods.clayium.block.ClayMachines;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.core.ClayiumCore;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class UtilTier
{
  public static TierManager tierSmelter;
  public static TierManager tierGeneric;
  public static TierManager tierCACondenser;
  public static TierManager tierMachineTransport;
  public static TierManager tierBufferTransport;
  public static final String multCraftTime = "multCraftTime";
  public static final String multConsumingEnergy = "multConsumingEnergy";
  public static final String autoInsertInterval = "autoInsertInterval";
  public static final String autoExtractInterval = "autoExtractInterval";
  public static final String maxAutoInsertDefault = "maxAutoInsertDefault";
  public static final String maxAutoExtractDefault = "maxAutoExtractDefault";
  
  public static boolean canAutoTransfer(int tier)
  {
    return tier >= 3;
  }
  
  public static boolean canManufactualCraft(int tier)
  {
    return tier <= 2;
  }
  
  public static boolean acceptWaterWheel(int tier)
  {
    return (tier == 2) || (tier == 3);
  }
  
  public static boolean acceptEnergyClay(int tier)
  {
    return tier >= 4;
  }
  
  public static void setTierManagers()
  {
    tierGeneric = TierManager.getMachineTierManager("MachineBase_Crafting");
    ((TieredStatus)tierGeneric.get("multCraftTime")).put(Integer.valueOf(5), Float.valueOf(0.25F));
    ((TieredStatus)tierGeneric.get("multCraftTime")).put(Integer.valueOf(6), Float.valueOf(0.0625F));
    ((TieredStatus)tierGeneric.get("multCraftTime")).put(Integer.valueOf(10), Float.valueOf(0.01F));
    ((TieredStatus)tierGeneric.get("multConsumingEnergy")).put(Integer.valueOf(5), Float.valueOf(5.0F));
    ((TieredStatus)tierGeneric.get("multConsumingEnergy")).put(Integer.valueOf(6), Float.valueOf(25.0F));
    ((TieredStatus)tierGeneric.get("multConsumingEnergy")).put(Integer.valueOf(10), Float.valueOf(250.0F));
    
    tierSmelter = TierManager.getMachineTierManager("MachineSmelter_Crafting");
    ((TieredStatus)tierSmelter.get("multCraftTime")).put(Integer.valueOf(4), Float.valueOf(2.0F));
    ((TieredStatus)tierSmelter.get("multCraftTime")).put(Integer.valueOf(5), Float.valueOf(0.5F));
    ((TieredStatus)tierSmelter.get("multCraftTime")).put(Integer.valueOf(6), Float.valueOf(0.125F));
    ((TieredStatus)tierSmelter.get("multCraftTime")).put(Integer.valueOf(7), Float.valueOf(0.03F));
    ((TieredStatus)tierSmelter.get("multCraftTime")).put(Integer.valueOf(8), Float.valueOf(0.01F));
    ((TieredStatus)tierSmelter.get("multCraftTime")).put(Integer.valueOf(9), Float.valueOf(0.0025F));
    ((TieredStatus)tierSmelter.get("multConsumingEnergy")).put(Integer.valueOf(4), Float.valueOf(1.0F));
    ((TieredStatus)tierSmelter.get("multConsumingEnergy")).put(Integer.valueOf(5), Float.valueOf(14.0F));
    ((TieredStatus)tierSmelter.get("multConsumingEnergy")).put(Integer.valueOf(6), Float.valueOf(200.0F));
    ((TieredStatus)tierSmelter.get("multConsumingEnergy")).put(Integer.valueOf(7), Float.valueOf(2800.0F));
    ((TieredStatus)tierSmelter.get("multConsumingEnergy")).put(Integer.valueOf(8), Float.valueOf(40000.0F));
    ((TieredStatus)tierSmelter.get("multConsumingEnergy")).put(Integer.valueOf(9), Float.valueOf(560000.0F));
    
    tierCACondenser = TierManager.getMachineTierManager("MachineCACondenser_Crafting");
    ((TieredStatus)tierCACondenser.get("multCraftTime")).put(Integer.valueOf(10), Float.valueOf(0.1F));
    ((TieredStatus)tierCACondenser.get("multConsumingEnergy")).put(Integer.valueOf(10), Float.valueOf(10.0F));
    ((TieredStatus)tierCACondenser.get("multCraftTime")).put(Integer.valueOf(11), Float.valueOf(0.01F));
    ((TieredStatus)tierCACondenser.get("multConsumingEnergy")).put(Integer.valueOf(11), Float.valueOf(100.0F));
    
    tierMachineTransport = TierManager.getTransportTierManager("MachineBase_Transport");
    tierBufferTransport = TierManager.getTransportTierManager("MachineBuffer_Transport");
    for (int tier = 0; tier <= 13; tier++)
    {
      if (tier <= 4)
      {
        ((TieredStatus)tierMachineTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(20));
        ((TieredStatus)tierMachineTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(20));
        ((TieredStatus)tierMachineTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(8));
        ((TieredStatus)tierMachineTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(8));
      }
      else if (tier == 5)
      {
        ((TieredStatus)tierMachineTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(2));
        ((TieredStatus)tierMachineTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(2));
        ((TieredStatus)tierMachineTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(16));
        ((TieredStatus)tierMachineTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(16));
      }
      else if (tier >= 6)
      {
        ((TieredStatus)tierMachineTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(1));
        ((TieredStatus)tierMachineTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(1));
        ((TieredStatus)tierMachineTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(64));
        ((TieredStatus)tierMachineTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(64));
      }
      if (tier <= 4)
      {
        ((TieredStatus)tierBufferTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(8));
        ((TieredStatus)tierBufferTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(8));
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(1));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(1));
      }
      if (tier >= 7)
      {
        ((TieredStatus)tierBufferTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(1));
        ((TieredStatus)tierBufferTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(1));
      }
      switch (tier)
      {
      case 5: 
        ((TieredStatus)tierBufferTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(4));
        ((TieredStatus)tierBufferTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(4));
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(4));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(4));
        break;
      case 6: 
        ((TieredStatus)tierBufferTransport.get("autoExtractInterval")).put(Integer.valueOf(tier), Integer.valueOf(2));
        ((TieredStatus)tierBufferTransport.get("autoInsertInterval")).put(Integer.valueOf(tier), Integer.valueOf(2));
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(16));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(16));
        break;
      case 7: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(64));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(64));
        break;
      case 8: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(128));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(128));
        break;
      case 9: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(192));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(192));
        break;
      case 10: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(256));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(256));
        break;
      case 11: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(512));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(512));
        break;
      case 12: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(1024));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(1024));
        break;
      case 13: 
        ((TieredStatus)tierBufferTransport.get("maxAutoExtractDefault")).put(Integer.valueOf(tier), Integer.valueOf(6400));
        ((TieredStatus)tierBufferTransport.get("maxAutoInsertDefault")).put(Integer.valueOf(tier), Integer.valueOf(6400));
      }
    }
  }
  
  public static void loadConfig(Configuration cfg)
  {
    tierSmelter.loadAllConfig(cfg);
    tierGeneric.loadAllConfig(cfg);
    tierCACondenser.loadAllConfig(cfg);
    
    tierMachineTransport.loadAllConfig(cfg);
    tierBufferTransport.loadAllConfig(cfg);
  }
  
  public static class TierManager
    extends HashMap<String, TieredStatus>
  {
    protected String configName;
    
    public static TierManager getMachineTierManager()
    {
      return getMachineTierManager("");
    }
    
    public static TierManager getMachineTierManager(String configName)
    {
      TierManager res = new TierManager(configName);
      res.put("multCraftTime", new TieredNumericStatus(Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0E9F)));
      res.put("multConsumingEnergy", new TieredNumericStatus(Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0E9F)));
      return res;
    }
    
    public static TierManager getTransportTierManager(String configName)
    {
      TierManager res = new TierManager(configName);
      res.put("autoInsertInterval", new TieredNumericStatus(Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(999)));
      res.put("autoExtractInterval", new TieredNumericStatus(Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(999)));
      res.put("maxAutoInsertDefault", new TieredNumericStatus(Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(9999)));
      res.put("maxAutoExtractDefault", new TieredNumericStatus(Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(9999)));
      return res;
    }
    
    public TierManager(String configName)
    {
      this.configName = configName;
    }
    
    public static void applyMachineTierManager(Block[] machines, TierManager manager)
    {
      for (int i = 0; i < machines.length; i++) {
        if ((machines[i] instanceof ClayMachines))
        {
          ((ClayMachines)machines[i]).multCraftTime = manager.getF("multCraftTime", i);
          ((ClayMachines)machines[i]).multConsumingEnergy = manager.getF("multConsumingEnergy", i);
        }
      }
    }
    
    public static void applyTransportTierManager(TileClayContainer tile, int tier, TierManager manager)
    {
      tile.autoExtractInterval = manager.getI("autoExtractInterval", tier);
      tile.autoInsertInterval = manager.getI("autoInsertInterval", tier);
      tile.maxAutoExtractDefault = manager.getI("maxAutoExtractDefault", tier);
      tile.maxAutoInsertDefault = manager.getI("maxAutoInsertDefault", tier);
    }
    
    public Object get(String string, int tier)
    {
      return containsKey(string) ? ((TieredStatus)get(string)).get(tier) : null;
    }
    
    public float getF(String string, int tier)
    {
      Object res = get(string, tier);
      return (res instanceof Float) ? ((Float)res).floatValue() : 0.0F;
    }
    
    public int getI(String string, int tier)
    {
      Object res = get(string, tier);
      return (res instanceof Integer) ? ((Integer)res).intValue() : 0;
    }
    
    public void loadAllConfig(Configuration cfg)
    {
      for (Map.Entry<String, TieredStatus> entry : entrySet()) {
        ((TieredStatus)entry.getValue()).loadAllConfig(this.configName + "_" + (String)entry.getKey(), cfg);
      }
    }
  }
  
  public static class TieredStatus<T>
    extends HashMap<Integer, T>
  {
    public T defvalue;
    public String configComment = "";
    public String configCategory = "tier balance";
    
    public TieredStatus(T defvalue)
    {
      this.defvalue = defvalue;
    }
    
    public T get(int tier)
    {
      return (T)(containsKey(Integer.valueOf(tier)) ? get(Integer.valueOf(tier)) : this.defvalue);
    }
    
    public Object getConfig(String name, Object value, Configuration cfg)
    {
      if ((value instanceof Boolean)) {
        return Boolean.valueOf(cfg.getBoolean(name, this.configCategory, ((Boolean)value).booleanValue(), this.configComment));
      }
      if ((value instanceof String)) {
        return cfg.getString(name, this.configCategory, (String)value, this.configComment);
      }
      return null;
    }
    
    public void loadAllConfig(String name, Configuration cfg)
    {
      for (Integer tier : keySet())
      {
        int t = tier.intValue();
        String cstr = String.format(name + "_%02d", new Object[] { Integer.valueOf(t) });
        Object value = getConfig(cstr, get(t), cfg);
        try
        {
          put(Integer.valueOf(t), value);
        }
        catch (Exception e)
        {
          ClayiumCore.logger.error("Config Error @ " + cstr);
          ClayiumCore.logger.catching(e);
        }
      }
      if (this.defvalue != null)
      {
        String cstr = name + "_def";
        Object value = getConfig(cstr, this.defvalue, cfg);
        try
        {
          this.defvalue = value;
        }
        catch (Exception e)
        {
          ClayiumCore.logger.error("Config Error @ " + cstr);
          ClayiumCore.logger.catching(e);
        }
      }
    }
  }
  
  public static class TieredNumericStatus<T extends Number>
    extends TieredStatus<T>
  {
    public T min;
    public T max;
    
    public TieredNumericStatus(T defvalue)
    {
      super();
    }
    
    public TieredNumericStatus(T defvalue, T min, T max)
    {
      this(defvalue);
      this.min = min;
      this.max = max;
    }
    
    public Object getConfig(String name, Object value, Configuration cfg)
    {
      if ((value instanceof Integer))
      {
        int minValue = (this.min instanceof Integer) ? ((Integer)this.min).intValue() : Integer.MIN_VALUE;
        int maxValue = (this.max instanceof Integer) ? ((Integer)this.max).intValue() : Integer.MAX_VALUE;
        return Integer.valueOf(cfg.getInt(name, this.configCategory, ((Integer)value).intValue(), minValue, maxValue, this.configComment));
      }
      if ((value instanceof Float))
      {
        float minValue = (this.min instanceof Float) ? ((Float)this.min).floatValue() : Float.MIN_VALUE;
        float maxValue = (this.max instanceof Float) ? ((Float)this.max).floatValue() : Float.MAX_VALUE;
        return Float.valueOf(cfg.getFloat(name, this.configCategory, ((Float)value).floatValue(), minValue, maxValue, this.configComment));
      }
      return super.getConfig(name, value, cfg);
    }
  }
}
