package mods.clayium.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryInItemStack
  extends NBTInventoryWrapper
{
  protected ItemStack itemstack;
  protected String tagname;
  
  public InventoryInItemStack(ItemStack itemstack, String tagname, int sizeInventory)
  {
    super((itemstack == null) || (itemstack.stackTagCompound == null) || (!itemstack.stackTagCompound.hasKey(tagname)) ? new NBTTagList() : itemstack.stackTagCompound
      .getTagList(tagname, 10), sizeInventory);
    this.tagname = tagname;
    this.itemstack = itemstack;
    initTagCompound();
  }
  
  public void setItemStack(ItemStack itemstack)
  {
    this.itemstack = itemstack;
    
    NBTTagList tagList = (itemstack == null) || (itemstack.stackTagCompound == null) || (!itemstack.stackTagCompound.hasKey(this.tagname)) ? new NBTTagList() : itemstack.stackTagCompound.getTagList(this.tagname, 10);
    setTagList(tagList);
    initTagCompound();
  }
  
  public void initTagCompound()
  {
    if ((this.itemstack != null) && (this.itemstack.stackTagCompound == null)) {
      this.itemstack.setTagCompound(new NBTTagCompound());
    }
    if ((this.itemstack != null) && (this.itemstack.stackTagCompound != null) && (!this.itemstack.stackTagCompound.hasKey(this.tagname))) {
      this.itemstack.stackTagCompound.setTag(this.tagname, this.tagList);
    }
    if (this.itemstack != null) {
      setInventoryName(this.itemstack.getDisplayName());
    }
  }
  
  public void markDirty()
  {
    super.markDirty();
    this.itemstack.stackTagCompound.setTag(this.tagname, this.tagList);
  }
}
