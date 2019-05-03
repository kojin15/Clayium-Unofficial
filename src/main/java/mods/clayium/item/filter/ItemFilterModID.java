package mods.clayium.item.filter;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import mods.clayium.core.ClayiumCore;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;

public class ItemFilterModID
  extends ItemFilterString
{
  public boolean filterStringMatch(String filterString, ItemStack itemstack)
  {
    String filter = filterString;
    if ((itemstack == null) || (itemstack.getItem() == null)) {
      return false;
    }
    String name;
    String name;
    if ((itemstack.getItem() instanceof ItemBlock)) {
      name = GameRegistry.findUniqueIdentifierFor(((ItemBlock)itemstack.getItem()).field_150939_a).modId;
    } else {
      name = GameRegistry.findUniqueIdentifierFor(itemstack.getItem()).modId;
    }
    try
    {
      Pattern pattern = Pattern.compile(filter);
      Matcher matcher = pattern.matcher(name);
      return matcher.find();
    }
    catch (PatternSyntaxException e)
    {
      ClayiumCore.logger.error("Illegal Pattern! \n" + e.getMessage());
    }
    return false;
  }
}
