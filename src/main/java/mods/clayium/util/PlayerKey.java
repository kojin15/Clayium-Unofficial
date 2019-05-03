package mods.clayium.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class PlayerKey
{
  private HashMap<Integer, Integer> mouse = new HashMap();
  
  public static enum KeyType
  {
    SPRINT(0),  USE_ITEM(1);
    
    private int id = -1;
    
    private KeyType(int id)
    {
      this.id = id;
    }
    
    public static KeyType getKeyFromId(int id)
    {
      for (KeyType key : ) {
        if (key.id == id) {
          return key;
        }
      }
      return null;
    }
    
    public int getId()
    {
      return this.id;
    }
  }
  
  private HashMap<KeyType, Integer> keys = new HashMap();
  
  private Map.Entry<Integer, Integer> getMouseEntry(int input)
  {
    for (Iterator<Map.Entry<Integer, Integer>> iterator = this.mouse.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry<Integer, Integer> entry = (Map.Entry)iterator.next();
      if (((Integer)entry.getKey()).intValue() == input) {
        return entry;
      }
    }
    return null;
  }
  
  private Map.Entry<KeyType, Integer> getKeyEntry(KeyType key)
  {
    for (Iterator<Map.Entry<KeyType, Integer>> iterator = this.keys.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry<KeyType, Integer> entry = (Map.Entry)iterator.next();
      if (entry.getKey() == key) {
        return entry;
      }
    }
    return null;
  }
  
  public int getMouseLength(int input)
  {
    Map.Entry<Integer, Integer> entry = getMouseEntry(input);
    return entry == null ? Integer.MIN_VALUE : ((Integer)entry.getValue()).intValue();
  }
  
  public void setMouseInput(int input, boolean event)
  {
    Map.Entry<Integer, Integer> entry = getMouseEntry(input);
    if ((entry == null & event)) {
      this.mouse.put(Integer.valueOf(input), Integer.valueOf(0));
    }
    if (((entry != null ? 1 : 0) & (!event ? 1 : 0)) != 0) {
      this.mouse.remove(entry.getKey());
    }
  }
  
  public int getKeyLength(KeyType key)
  {
    Map.Entry<KeyType, Integer> entry = getKeyEntry(key);
    return entry == null ? Integer.MIN_VALUE : ((Integer)entry.getValue()).intValue();
  }
  
  public void setKeyInput(KeyType key, boolean event)
  {
    Map.Entry<KeyType, Integer> entry = getKeyEntry(key);
    if ((entry == null & event)) {
      this.keys.put(key, Integer.valueOf(0));
    }
    if (((entry != null ? 1 : 0) & (!event ? 1 : 0)) != 0) {
      this.keys.remove(entry.getKey());
    }
  }
  
  public void update()
  {
    for (Iterator<Map.Entry<Integer, Integer>> iterator = this.mouse.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry<Integer, Integer> entry = (Map.Entry)iterator.next();
      entry.setValue(Integer.valueOf(((Integer)entry.getValue()).intValue() + 1));
    }
    for (Iterator<Map.Entry<KeyType, Integer>> iterator = this.keys.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry<KeyType, Integer> entry = (Map.Entry)iterator.next();
      entry.setValue(Integer.valueOf(((Integer)entry.getValue()).intValue() + 1));
    }
  }
}
