package mods.clayium.util;

import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import mods.clayium.core.ClayiumCore;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Logger;

public class UtilFluid
{
  public static UtilFluid INSTANCE = new UtilFluid();
  static BiMap<Fluid, Integer> fluidIDs = HashBiMap.create();
  static BiMap<Integer, String> fluidNames = HashBiMap.create();
  static Set<String> overridableNames = new TreeSet();
  static final int offset = 4096;
  static boolean useRegistry = false;
  static Configuration cfgFluidIDs = null;
  protected static final String prefix = "[FluidID Loader] ";
  
  static
  {
    INSTANCE.subscribeFluidRegisterEvent(new FluidRegisterEvent("water", 1));
    INSTANCE.subscribeFluidRegisterEvent(new FluidRegisterEvent("lava", 2));
  }
  
  public static void log(String message)
  {
    if (ClayiumCore.cfgVerboseLoggingForFluidIDLoader) {
      ClayiumCore.logger.info("[FluidID Loader] " + message);
    }
  }
  
  protected static final Splitter splitter = Splitter.on('=').limit(2);
  
  public static void loadMapsFromConfig(Configuration cfg)
  {
    boolean error = false;
    BiMap<Integer, String> newFluidNames = HashBiMap.create();
    BiMap<Fluid, Integer> newFluidIDs = HashBiMap.create();
    BiMap<Integer, String> loadedFluidNames = HashBiMap.create();
    cfgFluidIDs = cfg;
    log("Started to load FluidID.");
    if ((cfgFluidIDs != null) && 
      (cfgFluidIDs.hasCategory("fluid")) && (cfgFluidIDs.getCategory("fluid").containsKey("IDsForCapsule")))
    {
      String[] cfgs = cfgFluidIDs.getStringList("IDsForCapsule", "fluid", new String[0], "ID Map for Clay Fluid Capsule.");
      for (String s : cfgs)
      {
        String[] result = (String[])Iterables.toArray(splitter.split(s), String.class);
        if ((result != null) && (result.length == 2))
        {
          loadedFluidNames.put(Integer.valueOf(Integer.parseInt(result[0])), result[1]);
          newFluidNames.put(Integer.valueOf(Integer.parseInt(result[0])), result[1]);
        }
      }
      log("Loading FluidID from cfg.");
      log("  List of FluidIDs in cfg");
      for (??? = newFluidNames.entrySet().iterator(); ((Iterator)???).hasNext();)
      {
        Object entry = (Map.Entry)((Iterator)???).next();
        String fluidName = (String)((Map.Entry)entry).getValue();
        int newID = ((Integer)((Map.Entry)entry).getKey()).intValue();
        log("    FluidID = " + newID + ", Fluid Name = " + fluidName);
      }
      log("  List of loaded FluidIDs");
      for (??? = fluidNames.entrySet().iterator(); ((Iterator)???).hasNext();)
      {
        Object entry = (Map.Entry)((Iterator)???).next();
        String fluidName = (String)((Map.Entry)entry).getValue();
        int newID = ((Integer)((Map.Entry)entry).getKey()).intValue();
        log("    FluidID = " + newID + ", Fluid Name = " + fluidName);
      }
      log("Scanning already loaded Fluid list");
      for (??? = fluidNames.entrySet().iterator(); ((Iterator)???).hasNext();)
      {
        Object entry = (Map.Entry)((Iterator)???).next();
        String fluidName = (String)((Map.Entry)entry).getValue();
        int oldID = ((Integer)((Map.Entry)entry).getKey()).intValue();
        int newID = oldID;
        log("  Checking FluidID = " + newID + ", Fluid Name = " + fluidName);
        if (loadedFluidNames.containsValue(fluidName))
        {
          newID = ((Integer)newFluidNames.inverse().get(fluidName)).intValue();
          log("    Found in cfg. Set FluidID to " + newID);
        }
        else
        {
          while (newFluidNames.containsKey(Integer.valueOf(newID))) {
            newID++;
          }
          newFluidNames.put(Integer.valueOf(newID), fluidName);
          log("    Not found in cfg. Set FluidID to " + newID);
        }
        Fluid fluid = (Fluid)fluidIDs.inverse().get(Integer.valueOf(oldID));
        try
        {
          newFluidIDs.put(fluid, Integer.valueOf(newID));
        }
        catch (IllegalArgumentException e)
        {
          ClayiumCore.logger.error("[FluidID Loader] " + e.getMessage());
          error = true;
        }
      }
      fluidNames = newFluidNames;
      fluidIDs = newFluidIDs;
    }
    log("Done.");
    if (error)
    {
      ClayiumCore.logger.error("[FluidID Loader] An exception occurred. The FluidID database may be corrupted.");
      ClayiumCore.logger.error("Enable B:VerboseLoggingForFluidIDLoader to get more information.");
    }
  }
  
  public static void saveMapsToConfig()
  {
    if (cfgFluidIDs != null)
    {
      String[] toSave = new String[fluidNames.size()];
      int pos = 0;
      for (Map.Entry<Integer, String> entry : fluidNames.entrySet())
      {
        String fluidName = (String)entry.getValue();
        int ID = ((Integer)entry.getKey()).intValue();
        toSave[pos] = (ID + "=" + fluidName);
        pos++;
      }
      cfgFluidIDs.get("fluid", "IDsForCapsule", new String[0]).set(toSave);
      cfgFluidIDs.save();
    }
  }
  
  protected static boolean overrideFlag = true;
  
  @SubscribeEvent
  public void subscribeFluidRegisterEvent(FluidRegisterEvent event)
  {
    Fluid fluid = FluidRegistry.getFluid(event.fluidName);
    registerFluid(fluid, 1, overrideFlag);
    saveMapsToConfig();
  }
  
  protected static boolean registerFluid(Fluid fluid, int fluidID, boolean overrideFluidID)
  {
    while (fluidIDs.containsValue(Integer.valueOf(fluidID))) {
      fluidID++;
    }
    String fluidName = fluid.getName();
    if (fluidNames.containsValue(fluidName)) {
      if (overridableNames.contains(fluidName))
      {
        if (overrideFluidID)
        {
          int oldID = ((Integer)fluidNames.inverse().get(fluidName)).intValue();
          fluidIDs.inverse().remove(Integer.valueOf(oldID));
          fluidNames.remove(Integer.valueOf(oldID));
        }
        else
        {
          return false;
        }
      }
      else
      {
        fluidID = ((Integer)fluidNames.inverse().get(fluidName)).intValue();
        if (fluidIDs.inverse().containsKey(Integer.valueOf(fluidID))) {
          fluidIDs.inverse().remove(Integer.valueOf(fluidID));
        }
        fluidIDs.put(fluid, Integer.valueOf(fluidID));
        return true;
      }
    }
    fluidIDs.put(fluid, Integer.valueOf(fluidID));
    fluidNames.put(Integer.valueOf(fluidID), fluidName);
    return true;
  }
  
  static int index = 4096;
  
  public static void registerFluid(Fluid fluid)
  {
    registerFluid(fluid, index, false);
    overridableNames.add(fluid.getName());
    index += 1;
    
    overrideFlag = false;
    FluidRegistry.registerFluid(fluid);
    overrideFlag = true;
  }
  
  public static int getFluidID(String fluidName)
  {
    if (useRegistry) {
      return FluidRegistry.getFluidID(fluidName);
    }
    Integer ret = (Integer)fluidIDs.get(FluidRegistry.getFluid(fluidName));
    if ((ret == null) && (fluidName != null))
    {
      ret = (Integer)fluidNames.inverse().get(fluidName);
      if (ret == null) {
        ClayiumCore.logger.error("Can't get Fluid ID! FluidName = " + fluidName);
      }
    }
    return ret == null ? -1 : ret.intValue();
  }
  
  public static int getFluidID(Fluid fluid)
  {
    if (useRegistry) {
      return FluidRegistry.getFluidID(fluid);
    }
    Integer ret = (Integer)fluidIDs.get(fluid);
    if ((ret == null) && (fluid != null)) {
      return getFluidID(fluid.getName());
    }
    return ret == null ? -1 : ret.intValue();
  }
  
  public static int getFluidID(FluidStack fluidStack)
  {
    return fluidStack != null ? getFluidID(fluidStack.getFluid()) : -1;
  }
  
  public static Fluid getFluid(int fluidID)
  {
    return useRegistry ? FluidRegistry.getFluid(fluidID) : (Fluid)fluidIDs.inverse().get(Integer.valueOf(fluidID));
  }
  
  public static String getFluidName(int fluidID)
  {
    return useRegistry ? FluidRegistry.getFluidName(fluidID) : (String)fluidNames.get(Integer.valueOf(fluidID));
  }
}
