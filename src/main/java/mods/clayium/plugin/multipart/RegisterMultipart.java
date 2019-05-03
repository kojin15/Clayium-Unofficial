package mods.clayium.plugin.multipart;

import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;
import java.util.Arrays;
import mods.clayium.block.CBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class RegisterMultipart
  implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter
{
  public TMultiPart createPart(String name, boolean client)
  {
    if (name.equals(PANCablePart.type)) {
      return new PANCablePart();
    }
    return null;
  }
  
  public void init()
  {
    MultiPartRegistry.registerConverter(this);
    MultiPartRegistry.registerParts(this, new String[] { PANCablePart.type });
  }
  
  public Iterable<Block> blockTypes()
  {
    return Arrays.asList(new Block[] { CBlocks.blockPANCable });
  }
  
  public TMultiPart convert(World world, BlockCoord pos)
  {
    Block b = world.getBlock(pos.x, pos.y, pos.z);
    if (b == CBlocks.blockPANCable) {
      return new PANCablePart();
    }
    return null;
  }
}
