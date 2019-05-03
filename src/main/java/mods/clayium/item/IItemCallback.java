package mods.clayium.item;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IItemCallback
{
  public abstract void onUpdate(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean);
  
  public abstract int getItemStackLimit(ItemStack paramItemStack);
  
  public abstract boolean onItemUseFirst(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract boolean onItemUse(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract boolean doesSneakBypassUse(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer);
  
  public abstract boolean itemInteractionForEntity(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, EntityLivingBase paramEntityLivingBase);
  
  public abstract ItemStack onItemRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer);
  
  public abstract boolean canHarvestBlock(Block paramBlock, ItemStack paramItemStack);
  
  public abstract float getDigSpeed(ItemStack paramItemStack, Block paramBlock, int paramInt);
  
  public abstract boolean onBlockStartBreak(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer);
  
  public abstract boolean onBlockDestroyed(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase);
  
  public abstract boolean onLeftClickEntity(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, Entity paramEntity);
  
  public abstract boolean hitEntity(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase1, EntityLivingBase paramEntityLivingBase2);
  
  public abstract boolean onEntitySwing(EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack);
  
  public abstract EnumAction getItemUseAction(ItemStack paramItemStack);
  
  public abstract int getMaxItemUseDuration(ItemStack paramItemStack);
  
  public abstract void onUsingTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt);
  
  public abstract void onPlayerStoppedUsing(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, int paramInt);
  
  public abstract ItemStack onEaten(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer);
  
  public abstract Set<String> getToolClasses(ItemStack paramItemStack);
  
  public abstract int getHarvestLevel(ItemStack paramItemStack, String paramString);
  
  public abstract Multimap getAttributeModifiers(ItemStack paramItemStack);
  
  public abstract boolean isItemTool(ItemStack paramItemStack);
  
  public abstract boolean getIsRepairable(ItemStack paramItemStack1, ItemStack paramItemStack2);
  
  public abstract int getItemEnchantability(ItemStack paramItemStack);
  
  public abstract boolean showDurabilityBar(ItemStack paramItemStack);
  
  public abstract int getMaxDamage(ItemStack paramItemStack);
  
  public abstract int getDamage(ItemStack paramItemStack);
  
  public abstract boolean isDamaged(ItemStack paramItemStack);
  
  public abstract void setDamage(ItemStack paramItemStack, int paramInt);
  
  public abstract boolean doesContainerItemLeaveCraftingGrid(ItemStack paramItemStack);
  
  public abstract ItemStack getContainerItem(ItemStack paramItemStack);
  
  public abstract boolean hasContainerItem(ItemStack paramItemStack);
  
  public abstract void onCreated(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer);
  
  public abstract boolean onDroppedByPlayer(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  public abstract int getEntityLifespan(ItemStack paramItemStack, World paramWorld);
  
  public abstract boolean hasCustomEntity(ItemStack paramItemStack);
  
  public abstract Entity createEntity(World paramWorld, Entity paramEntity, ItemStack paramItemStack);
  
  public abstract boolean onEntityItemUpdate(EntityItem paramEntityItem);
  
  public abstract boolean isValidArmor(ItemStack paramItemStack, int paramInt, Entity paramEntity);
  
  public abstract void onArmorTick(World paramWorld, EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
  
  public abstract String getArmorTexture(ItemStack paramItemStack, Entity paramEntity, int paramInt, String paramString);
  
  @SideOnly(Side.CLIENT)
  public abstract ModelBiped getArmorModel(EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack, int paramInt);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderHelmetOverlay(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, ScaledResolution paramScaledResolution, float paramFloat, boolean paramBoolean, int paramInt1, int paramInt2);
  
  public abstract String getPotionEffect(ItemStack paramItemStack);
  
  public abstract boolean isPotionIngredient(ItemStack paramItemStack);
  
  public abstract float getSmeltingExperience(ItemStack paramItemStack);
  
  public abstract boolean isBookEnchantable(ItemStack paramItemStack1, ItemStack paramItemStack2);
  
  public abstract boolean isBeaconPayment(ItemStack paramItemStack);
  
  @SideOnly(Side.CLIENT)
  public abstract FontRenderer getFontRenderer(ItemStack paramItemStack);
}
