package mods.clayium.block.tile;

import java.util.ArrayList;
import java.util.Random;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileWaterWheel
  extends TileClayContainerTiered
{
  public int progress = 0;
  public int progressEfficiency = 1000;
  protected static int progressMax = 20000;
  private static Random random = new Random();
  
  public void initParams()
  {
    super.initParams();
    this.progressEfficiency = ((int)(this.progressEfficiency * Math.pow(Math.max(this.baseTier, 1.0D), 3.0D)));
    this.containerItemStacks = new ItemStack[1];
    this.listSlotsInsert.add(new int[] { 0 });
    this.listSlotsExtract.add(new int[] { 0 });
    
    this.insertRoutes = new int[] { -1, -1, -1, -1, -1, -1 };
    this.extractRoutes = new int[] { -1, -1, -1, -1, -1, -1 };
    this.slotsDrop = new int[] { 0 };
    this.autoInsert = false;
    this.autoExtract = false;
  }
  
  public void readFromNBT(NBTTagCompound tagCompound)
  {
    super.readFromNBT(tagCompound);
    
    this.progress = tagCompound.getInteger("Progress");
    this.progressEfficiency = tagCompound.getInteger("ProgressEfficiency");
  }
  
  public void writeToNBT(NBTTagCompound tagCompound)
  {
    super.writeToNBT(tagCompound);
    tagCompound.setInteger("Progress", this.progress);
    tagCompound.setInteger("ProgressEfficiency", this.progressEfficiency);
  }
  
  public void updateEntity()
  {
    super.updateEntity();
    if ((!this.worldObj.isRemote) && 
      (random.nextInt(40) < countSurroundingWater()))
    {
      this.progress = ((int)(this.progress + this.progressEfficiency * Math.pow(Math.max(this.baseTier, 1.0D), 3.0D)));
      setSyncFlag();
      if (this.progress >= progressMax)
      {
        this.progress -= progressMax;
        this.progressEfficiency -= (random.nextInt(5) == 0 ? 1 : 0);
        emitEnergy();
      }
    }
  }
  
  public int getProgressIcon()
  {
    return this.progress * 10 / progressMax / 2 == 0 ? 0 : 1;
  }
  
  public double getProgress()
  {
    return this.progress / progressMax;
  }
  
  public void openInventory() {}
  
  public void closeInventory() {}
  
  public void emitEnergy()
  {
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
    {
      TileEntity te = UtilDirection.getTileEntity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, direction);
      if ((te != null) && ((te instanceof TileClayMachines)) && (UtilTier.acceptWaterWheel(((TileClayMachines)te).getTier()))) {
        if (((TileClayMachines)te).clayEnergy < 5.0D * Math.pow(Math.max(this.baseTier, 1.0D), 8.0D))
        {
          TileClayMachines tmp106_103 = ((TileClayMachines)te);tmp106_103.clayEnergy = ((tmp106_103.clayEnergy + Math.pow(Math.max(this.baseTier, 1.0D), 8.0D)));
          ((TileClayMachines)te).setSyncFlag();
        }
      }
    }
  }
  
  public int countSurroundingWater()
  {
    int meta = 0;
    int count = 0;
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        for (int z = -1; z <= 1; z++)
        {
          Block block = this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z);
          meta = this.worldObj.getBlockMetadata(this.xCoord + x, this.yCoord + y, this.zCoord + z);
          if ((block.getMaterial() == Material.water) && ((block instanceof BlockLiquid)) && (meta != 0)) {
            count++;
          }
        }
      }
    }
    return count;
  }
}
