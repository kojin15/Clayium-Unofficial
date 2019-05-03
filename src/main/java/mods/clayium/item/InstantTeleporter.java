package mods.clayium.item;

import mods.clayium.entity.EntityTeleportBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InstantTeleporter
  extends ClayShooter
{
  public InstantTeleporter(int maxDamage, String unlocalizedName, String textureName, int bulletLifespan, float bulletInitialVelocity, float bulletDiffusion, int bulletDamage, float bulletShootingRate, int bulletCooldownTime, int chargeTime)
  {
    super(maxDamage, unlocalizedName, textureName, bulletLifespan, bulletInitialVelocity, bulletDiffusion, bulletDamage, bulletShootingRate, bulletCooldownTime, chargeTime);
  }
  
  public InstantTeleporter(int maxDamage, String unlocalizedName, String textureName, int bulletLifespan, float bulletInitialVelocity, float bulletDiffusion, int bulletDamage, int bulletShootingFrame, int chargeTime)
  {
    super(maxDamage, unlocalizedName, textureName, bulletLifespan, bulletInitialVelocity, bulletDiffusion, bulletDamage, bulletShootingFrame, chargeTime);
  }
  
  public void spawnEntity(ItemStack stack, EntityPlayer player, float per, boolean critical)
  {
    if (!player.worldObj.isRemote) {
      player.worldObj.spawnEntityInWorld(new EntityTeleportBall(player.worldObj, player, 
        getLifespan(stack, player), getInitialVelocity(stack, player) * per, getDiffusion(stack, player), (int)(getDamage(stack, player) * per), 1, critical));
    }
  }
}
