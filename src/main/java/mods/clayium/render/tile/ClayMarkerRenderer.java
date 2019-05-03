package mods.clayium.render.tile;

import mods.clayium.block.tile.TileClayMarker;
import mods.clayium.util.UtilRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ClayMarkerRenderer
  extends TileEntitySpecialRenderer
{
  static double[] ds1 = { 0.1875D, -0.1875D };
  static double[] ds2 = { 0.125D, -0.125D };
  
  public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float p_147500_8_)
  {
    if ((tile instanceof TileClayMarker))
    {
      UtilRender.setLightValue(15, 15);
      
      TileClayMarker tile1 = (TileClayMarker)tile;
      switch (tile1.state)
      {
      case 1: 
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        
        GL11.glDisable(2912);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDisable(2884);
        
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(3553);
        
        GL11.glLineWidth(3.0F);
        tessellator.startDrawing(1);
        tessellator.setColorRGBA_F(0.1F, 0.5F, 0.2F, 0.3F);
        double d2;
        for (d1 : ds1) {
          for (d2 : ds1)
          {
            tessellator.addVertex(x + 0.0D + TileClayMarker.maxRange, y + 0.5D + d1, z + 0.5D + d2);
            tessellator.addVertex(x + 1.0D - TileClayMarker.maxRange, y + 0.5D + d1, z + 0.5D + d2);
            tessellator.addVertex(x + 0.5D + d1, y + 0.0D + TileClayMarker.maxRange, z + 0.5D + d2);
            tessellator.addVertex(x + 0.5D + d1, y + 1.0D - TileClayMarker.maxRange, z + 0.5D + d2);
            tessellator.addVertex(x + 0.5D + d1, y + 0.5D + d2, z + 0.0D + TileClayMarker.maxRange);
            tessellator.addVertex(x + 0.5D + d1, y + 0.5D + d2, z + 1.0D - TileClayMarker.maxRange);
          }
        }
        tessellator.draw();
        
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2912);
        
        GL11.glLineWidth(2.0F);
        tessellator.startDrawing(1);
        tessellator.setColorRGBA_F(0.2F, 0.7F, 0.3F, 1.0F);
        double d = 0.02D;
        double[] arrayOfDouble2 = ds1;double d1 = arrayOfDouble2.length;
        for (double d1 = 0; d1 < d1; d1++)
        {
          double d1 = arrayOfDouble2[d1];
          double[] arrayOfDouble4 = ds1;d2 = arrayOfDouble4.length;
          for (double d2 = 0; d2 < d2; d2++)
          {
            double d2 = arrayOfDouble4[d2];
            tessellator.addVertex(x + 0.0D + TileClayMarker.maxRange, y + 0.5D + d1, z + 0.5D + d2);
            tessellator.addVertex(x + 1.0D - TileClayMarker.maxRange, y + 0.5D + d1, z + 0.5D + d2);
            tessellator.addVertex(x + 0.5D + d1, y + 0.0D + TileClayMarker.maxRange, z + 0.5D + d2);
            tessellator.addVertex(x + 0.5D + d1, y + 1.0D - TileClayMarker.maxRange, z + 0.5D + d2);
            tessellator.addVertex(x + 0.5D + d1, y + 0.5D + d2, z + 0.0D + TileClayMarker.maxRange);
            tessellator.addVertex(x + 0.5D + d1, y + 0.5D + d2, z + 1.0D - TileClayMarker.maxRange);
          }
        }
        tessellator.draw();
        
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glBlendFunc(770, 771);
        
        Block block = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
        UtilRender.renderBox(tile.xCoord + block.getBlockBoundsMinX(), tile.yCoord + block.getBlockBoundsMinY(), tile.zCoord + block.getBlockBoundsMinZ(), tile.xCoord + block
          .getBlockBoundsMaxX(), tile.yCoord + block.getBlockBoundsMaxY(), tile.zCoord + block.getBlockBoundsMaxZ(), 1, 1.0F, 0.0F, 0.0F);
        
        break;
      case 2: 
      case 3: 
      case 4: 
        UtilRender.renderAxisAlignedBB(tile1, 0.1F, 0.1F, 0.7F);
        Block block1 = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
        UtilRender.renderBox(tile.xCoord + block1.getBlockBoundsMinX(), tile.yCoord + block1.getBlockBoundsMinY(), tile.zCoord + block1.getBlockBoundsMinZ(), tile.xCoord + block1
          .getBlockBoundsMaxX(), tile.yCoord + block1.getBlockBoundsMaxY(), tile.zCoord + block1.getBlockBoundsMaxZ(), 
          Math.max(1, tile1.getBoxAppearance()), 1.0F, 0.0F, 0.0F);
        break;
      }
    }
  }
}
