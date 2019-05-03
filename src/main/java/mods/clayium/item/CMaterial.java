package mods.clayium.item;

public class CMaterial
{
  public String name;
  public String iname;
  public String oreDictionaryName;
  public int meta;
  public int[][] colors = { { 140, 140, 140 }, { 25, 25, 25 }, { 255, 255, 255 } };
  public float hardness = 1.0F;
  
  public CMaterial(String name, String iname, String oreDictionaryName, int meta)
  {
    this.name = name;
    this.iname = iname;
    this.oreDictionaryName = oreDictionaryName;
    this.meta = meta;
  }
  
  public CMaterial(String name, String iname, int meta)
  {
    this(name, iname, name, meta);
  }
  
  public boolean equals(Object obj)
  {
    return ((obj instanceof CMaterial)) && (((CMaterial)obj).name.equals(this.name)) && (((CMaterial)obj).iname.equals(this.iname)) && (((CMaterial)obj).meta == this.meta);
  }
  
  public int hashCode()
  {
    return this.meta;
  }
  
  public CMaterial setColor(int r, int g, int b, int index)
  {
    this.colors[index] = { r, g, b };
    return this;
  }
  
  public CMaterial setColor(int r, int g, int b)
  {
    this.colors[1] = { r / 6, g / 6, b / 6 };
    this.colors[0] = { r, g, b };
    this.colors[2] = { Math.min(r * 2, 255), Math.min(g * 2, 255), Math.min(b * 2, 255) };
    return this;
  }
  
  public CMaterial setColor(int r1, int g1, int b1, int r3, int g3, int b3)
  {
    this.colors[1] = { r1, g1, b1 };
    this.colors[0] = { (r1 + r3) / 2, (g1 + g3) / 2, (b1 + b3) / 2 };
    this.colors[2] = { r3, g3, b3 };
    return this;
  }
}
