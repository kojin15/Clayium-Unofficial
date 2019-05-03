package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import mods.clayium.block.tile.TileMetalChest;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class MetalChest
  extends ClayContainer
  implements ISpecialUnlocalizedName
{
  protected static HashMap<CMaterial, HashMap<String, Integer>> metalChestMaterials = new HashMap();
  
  /* Error */
  public static void registerMaterials()
  {
    // Byte code:
    //   0: getstatic 30	mods/clayium/item/CMaterials:SILICON	Lmods/clayium/item/CMaterial;
    //   3: bipush 9
    //   5: iconst_5
    //   6: iconst_1
    //   7: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   10: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   13: getstatic 43	mods/clayium/item/CMaterials:ALUMINIUM	Lmods/clayium/item/CMaterial;
    //   16: bipush 9
    //   18: bipush 6
    //   20: iconst_1
    //   21: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   24: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   27: getstatic 46	mods/clayium/item/CMaterials:CLAY_STEEL	Lmods/clayium/item/CMaterial;
    //   30: bipush 9
    //   32: bipush 8
    //   34: iconst_1
    //   35: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   38: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   41: getstatic 49	mods/clayium/item/CMaterials:CLAYIUM	Lmods/clayium/item/CMaterial;
    //   44: bipush 13
    //   46: bipush 8
    //   48: iconst_1
    //   49: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   52: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   55: getstatic 52	mods/clayium/item/CMaterials:ULTIMATE_ALLOY	Lmods/clayium/item/CMaterial;
    //   58: bipush 13
    //   60: bipush 8
    //   62: iconst_3
    //   63: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   66: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   69: getstatic 55	mods/clayium/item/CMaterials:MAGNESIUM	Lmods/clayium/item/CMaterial;
    //   72: bipush 9
    //   74: bipush 6
    //   76: iconst_1
    //   77: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   80: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   83: getstatic 58	mods/clayium/item/CMaterials:SODIUM	Lmods/clayium/item/CMaterial;
    //   86: bipush 9
    //   88: bipush 6
    //   90: iconst_1
    //   91: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   94: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   97: getstatic 61	mods/clayium/item/CMaterials:LITHIUM	Lmods/clayium/item/CMaterial;
    //   100: bipush 9
    //   102: bipush 7
    //   104: iconst_1
    //   105: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   108: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   111: getstatic 64	mods/clayium/item/CMaterials:ZIRCONIUM	Lmods/clayium/item/CMaterial;
    //   114: bipush 9
    //   116: bipush 7
    //   118: iconst_1
    //   119: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   122: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   125: getstatic 67	mods/clayium/item/CMaterials:ZINC	Lmods/clayium/item/CMaterial;
    //   128: bipush 9
    //   130: bipush 6
    //   132: iconst_1
    //   133: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   136: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   139: getstatic 70	mods/clayium/item/CMaterials:MANGANESE	Lmods/clayium/item/CMaterial;
    //   142: bipush 9
    //   144: bipush 6
    //   146: iconst_1
    //   147: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   150: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   153: getstatic 73	mods/clayium/item/CMaterials:CALCIUM	Lmods/clayium/item/CMaterial;
    //   156: bipush 9
    //   158: bipush 6
    //   160: iconst_1
    //   161: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   164: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   167: getstatic 76	mods/clayium/item/CMaterials:POTASSIUM	Lmods/clayium/item/CMaterial;
    //   170: bipush 9
    //   172: bipush 6
    //   174: iconst_1
    //   175: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   178: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   181: getstatic 79	mods/clayium/item/CMaterials:NICKEL	Lmods/clayium/item/CMaterial;
    //   184: bipush 9
    //   186: bipush 6
    //   188: iconst_1
    //   189: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   192: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   195: getstatic 82	mods/clayium/item/CMaterials:BERYLLIUM	Lmods/clayium/item/CMaterial;
    //   198: bipush 10
    //   200: bipush 8
    //   202: iconst_1
    //   203: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   206: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   209: getstatic 85	mods/clayium/item/CMaterials:LEAD	Lmods/clayium/item/CMaterial;
    //   212: bipush 9
    //   214: bipush 6
    //   216: iconst_1
    //   217: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   220: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   223: getstatic 88	mods/clayium/item/CMaterials:HAFNIUM	Lmods/clayium/item/CMaterial;
    //   226: bipush 9
    //   228: bipush 8
    //   230: iconst_1
    //   231: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   234: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   237: getstatic 91	mods/clayium/item/CMaterials:CHROME	Lmods/clayium/item/CMaterial;
    //   240: bipush 13
    //   242: bipush 8
    //   244: iconst_1
    //   245: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   248: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   251: getstatic 94	mods/clayium/item/CMaterials:TITANIUM	Lmods/clayium/item/CMaterial;
    //   254: bipush 13
    //   256: bipush 8
    //   258: iconst_1
    //   259: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   262: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   265: getstatic 97	mods/clayium/item/CMaterials:STRONTIUM	Lmods/clayium/item/CMaterial;
    //   268: bipush 10
    //   270: bipush 8
    //   272: iconst_1
    //   273: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   276: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   279: getstatic 100	mods/clayium/item/CMaterials:BARIUM	Lmods/clayium/item/CMaterial;
    //   282: bipush 10
    //   284: bipush 8
    //   286: iconst_1
    //   287: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   290: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   293: getstatic 103	mods/clayium/item/CMaterials:AZ91D_ALLOY	Lmods/clayium/item/CMaterial;
    //   296: bipush 13
    //   298: bipush 8
    //   300: iconst_1
    //   301: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   304: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   307: getstatic 106	mods/clayium/item/CMaterials:ZK60A_ALLOY	Lmods/clayium/item/CMaterial;
    //   310: bipush 13
    //   312: bipush 8
    //   314: iconst_1
    //   315: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   318: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   321: getstatic 109	mods/clayium/item/CMaterials:RUBIDIUM	Lmods/clayium/item/CMaterial;
    //   324: bipush 13
    //   326: iconst_3
    //   327: iconst_1
    //   328: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   331: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   334: getstatic 112	mods/clayium/item/CMaterials:CAESIUM	Lmods/clayium/item/CMaterial;
    //   337: bipush 13
    //   339: iconst_3
    //   340: iconst_1
    //   341: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   344: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   347: getstatic 115	mods/clayium/item/CMaterials:FRANCIUM	Lmods/clayium/item/CMaterial;
    //   350: bipush 13
    //   352: iconst_4
    //   353: iconst_1
    //   354: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   357: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   360: getstatic 118	mods/clayium/item/CMaterials:RADIUM	Lmods/clayium/item/CMaterial;
    //   363: bipush 13
    //   365: iconst_4
    //   366: iconst_1
    //   367: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   370: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   373: getstatic 121	mods/clayium/item/CMaterials:ACTINIUM	Lmods/clayium/item/CMaterial;
    //   376: bipush 13
    //   378: iconst_5
    //   379: iconst_1
    //   380: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   383: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   386: getstatic 124	mods/clayium/item/CMaterials:THORIUM	Lmods/clayium/item/CMaterial;
    //   389: bipush 13
    //   391: iconst_5
    //   392: iconst_1
    //   393: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   396: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   399: getstatic 127	mods/clayium/item/CMaterials:PROTACTINIUM	Lmods/clayium/item/CMaterial;
    //   402: bipush 13
    //   404: bipush 6
    //   406: iconst_1
    //   407: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   410: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   413: getstatic 130	mods/clayium/item/CMaterials:URANIUM	Lmods/clayium/item/CMaterial;
    //   416: bipush 13
    //   418: bipush 6
    //   420: iconst_2
    //   421: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   424: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   427: getstatic 133	mods/clayium/item/CMaterials:NEPTUNIUM	Lmods/clayium/item/CMaterial;
    //   430: bipush 13
    //   432: bipush 6
    //   434: iconst_3
    //   435: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   438: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   441: getstatic 136	mods/clayium/item/CMaterials:PLUTONIUM	Lmods/clayium/item/CMaterial;
    //   444: bipush 13
    //   446: bipush 6
    //   448: iconst_4
    //   449: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   452: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   455: getstatic 139	mods/clayium/item/CMaterials:AMERICIUM	Lmods/clayium/item/CMaterial;
    //   458: bipush 13
    //   460: bipush 6
    //   462: iconst_5
    //   463: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   466: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   469: getstatic 142	mods/clayium/item/CMaterials:CURIUM	Lmods/clayium/item/CMaterial;
    //   472: bipush 13
    //   474: bipush 6
    //   476: bipush 6
    //   478: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   481: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   484: getstatic 145	mods/clayium/item/CMaterials:LANTHANUM	Lmods/clayium/item/CMaterial;
    //   487: bipush 13
    //   489: iconst_2
    //   490: iconst_2
    //   491: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   494: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   497: getstatic 148	mods/clayium/item/CMaterials:CERIUM	Lmods/clayium/item/CMaterial;
    //   500: bipush 13
    //   502: iconst_2
    //   503: iconst_4
    //   504: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   507: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   510: getstatic 151	mods/clayium/item/CMaterials:PRASEODYMIUM	Lmods/clayium/item/CMaterial;
    //   513: bipush 13
    //   515: iconst_2
    //   516: bipush 6
    //   518: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   521: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   524: getstatic 154	mods/clayium/item/CMaterials:NEODYMIUM	Lmods/clayium/item/CMaterial;
    //   527: bipush 13
    //   529: iconst_2
    //   530: bipush 8
    //   532: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   535: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   538: getstatic 157	mods/clayium/item/CMaterials:PROMETHIUM	Lmods/clayium/item/CMaterial;
    //   541: bipush 13
    //   543: iconst_4
    //   544: bipush 8
    //   546: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   549: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   552: getstatic 160	mods/clayium/item/CMaterials:SAMARIUM	Lmods/clayium/item/CMaterial;
    //   555: bipush 13
    //   557: bipush 6
    //   559: bipush 8
    //   561: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   564: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   567: getstatic 163	mods/clayium/item/CMaterials:EUROPIUM	Lmods/clayium/item/CMaterial;
    //   570: bipush 13
    //   572: bipush 8
    //   574: bipush 8
    //   576: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   579: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   582: getstatic 166	mods/clayium/item/CMaterials:VANADIUM	Lmods/clayium/item/CMaterial;
    //   585: iconst_4
    //   586: bipush 8
    //   588: iconst_1
    //   589: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   592: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   595: getstatic 169	mods/clayium/item/CMaterials:COBALT	Lmods/clayium/item/CMaterial;
    //   598: bipush 11
    //   600: bipush 6
    //   602: iconst_1
    //   603: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   606: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   609: getstatic 172	mods/clayium/item/CMaterials:PALLADIUM	Lmods/clayium/item/CMaterial;
    //   612: bipush 11
    //   614: bipush 8
    //   616: iconst_1
    //   617: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   620: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   623: getstatic 175	mods/clayium/item/CMaterials:PLATINUM	Lmods/clayium/item/CMaterial;
    //   626: bipush 13
    //   628: bipush 8
    //   630: iconst_2
    //   631: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   634: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   637: getstatic 178	mods/clayium/item/CMaterials:IRIDIUM	Lmods/clayium/item/CMaterial;
    //   640: bipush 13
    //   642: bipush 8
    //   644: iconst_3
    //   645: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   648: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   651: getstatic 181	mods/clayium/item/CMaterials:OSMIUM	Lmods/clayium/item/CMaterial;
    //   654: bipush 13
    //   656: bipush 8
    //   658: iconst_4
    //   659: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   662: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   665: getstatic 184	mods/clayium/item/CMaterials:RHENIUM	Lmods/clayium/item/CMaterial;
    //   668: bipush 13
    //   670: bipush 8
    //   672: iconst_5
    //   673: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   676: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   679: getstatic 187	mods/clayium/item/CMaterials:TANTALUM	Lmods/clayium/item/CMaterial;
    //   682: bipush 10
    //   684: bipush 8
    //   686: iconst_1
    //   687: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   690: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   693: getstatic 190	mods/clayium/item/CMaterials:TUNGSTEN	Lmods/clayium/item/CMaterial;
    //   696: bipush 13
    //   698: bipush 8
    //   700: iconst_1
    //   701: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   704: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   707: getstatic 193	mods/clayium/item/CMaterials:MOLYBDENUM	Lmods/clayium/item/CMaterial;
    //   710: bipush 13
    //   712: bipush 8
    //   714: iconst_2
    //   715: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   718: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   721: getstatic 196	mods/clayium/item/CMaterials:ANTIMONY	Lmods/clayium/item/CMaterial;
    //   724: bipush 9
    //   726: bipush 6
    //   728: iconst_1
    //   729: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   732: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   735: getstatic 199	mods/clayium/item/CMaterials:BISMUTH	Lmods/clayium/item/CMaterial;
    //   738: bipush 9
    //   740: bipush 6
    //   742: iconst_1
    //   743: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   746: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   749: getstatic 202	mods/clayium/item/CMaterials:BRASS	Lmods/clayium/item/CMaterial;
    //   752: bipush 9
    //   754: bipush 6
    //   756: iconst_1
    //   757: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   760: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   763: getstatic 205	mods/clayium/item/CMaterials:ELECTRUM	Lmods/clayium/item/CMaterial;
    //   766: bipush 13
    //   768: bipush 8
    //   770: iconst_1
    //   771: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   774: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   777: getstatic 208	mods/clayium/item/CMaterials:INVAR	Lmods/clayium/item/CMaterial;
    //   780: bipush 9
    //   782: bipush 8
    //   784: iconst_1
    //   785: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   788: invokestatic 40	mods/clayium/block/MetalChest:addChestMaterial	(Lmods/clayium/item/CMaterial;IIILnet/minecraftforge/common/config/Configuration;)V
    //   791: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   794: invokevirtual 213	net/minecraftforge/common/config/Configuration:save	()V
    //   797: goto +12 -> 809
    //   800: astore_0
    //   801: getstatic 36	mods/clayium/core/ClayiumCore:configrationDefault	Lnet/minecraftforge/common/config/Configuration;
    //   804: invokevirtual 213	net/minecraftforge/common/config/Configuration:save	()V
    //   807: aload_0
    //   808: athrow
    //   809: return
    // Line number table:
    //   Java source line #41	-> byte code offset #0
    //   Java source line #42	-> byte code offset #13
    //   Java source line #43	-> byte code offset #27
    //   Java source line #44	-> byte code offset #41
    //   Java source line #45	-> byte code offset #55
    //   Java source line #47	-> byte code offset #69
    //   Java source line #48	-> byte code offset #83
    //   Java source line #49	-> byte code offset #97
    //   Java source line #50	-> byte code offset #111
    //   Java source line #51	-> byte code offset #125
    //   Java source line #53	-> byte code offset #139
    //   Java source line #54	-> byte code offset #153
    //   Java source line #55	-> byte code offset #167
    //   Java source line #56	-> byte code offset #181
    //   Java source line #57	-> byte code offset #195
    //   Java source line #58	-> byte code offset #209
    //   Java source line #59	-> byte code offset #223
    //   Java source line #60	-> byte code offset #237
    //   Java source line #61	-> byte code offset #251
    //   Java source line #62	-> byte code offset #265
    //   Java source line #63	-> byte code offset #279
    //   Java source line #65	-> byte code offset #293
    //   Java source line #66	-> byte code offset #307
    //   Java source line #68	-> byte code offset #321
    //   Java source line #69	-> byte code offset #334
    //   Java source line #70	-> byte code offset #347
    //   Java source line #71	-> byte code offset #360
    //   Java source line #72	-> byte code offset #373
    //   Java source line #73	-> byte code offset #386
    //   Java source line #74	-> byte code offset #399
    //   Java source line #75	-> byte code offset #413
    //   Java source line #76	-> byte code offset #427
    //   Java source line #77	-> byte code offset #441
    //   Java source line #78	-> byte code offset #455
    //   Java source line #79	-> byte code offset #469
    //   Java source line #81	-> byte code offset #484
    //   Java source line #82	-> byte code offset #497
    //   Java source line #83	-> byte code offset #510
    //   Java source line #84	-> byte code offset #524
    //   Java source line #85	-> byte code offset #538
    //   Java source line #86	-> byte code offset #552
    //   Java source line #87	-> byte code offset #567
    //   Java source line #89	-> byte code offset #582
    //   Java source line #91	-> byte code offset #595
    //   Java source line #92	-> byte code offset #609
    //   Java source line #94	-> byte code offset #623
    //   Java source line #95	-> byte code offset #637
    //   Java source line #96	-> byte code offset #651
    //   Java source line #97	-> byte code offset #665
    //   Java source line #99	-> byte code offset #679
    //   Java source line #100	-> byte code offset #693
    //   Java source line #101	-> byte code offset #707
    //   Java source line #103	-> byte code offset #721
    //   Java source line #104	-> byte code offset #735
    //   Java source line #106	-> byte code offset #749
    //   Java source line #107	-> byte code offset #763
    //   Java source line #108	-> byte code offset #777
    //   Java source line #111	-> byte code offset #791
    //   Java source line #112	-> byte code offset #797
    //   Java source line #111	-> byte code offset #800
    //   Java source line #113	-> byte code offset #809
    // Local variable table:
    //   start	length	slot	name	signature
    //   800	8	0	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	791	800	finally
  }
  
  public MetalChest()
  {
    super(Material.iron, TileMetalChest.class, null, 11, 1);
    setHardness(2.0F);
    setResistance(2.0F);
    setStepSound(soundTypeMetal);
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
  {
    Iterator<Map.Entry<CMaterial, HashMap<String, Integer>>> iterator = metalChestMaterials.entrySet().iterator();
    while (iterator.hasNext())
    {
      CMaterial material = (CMaterial)((Map.Entry)iterator.next()).getKey();
      list.add(new ItemStack(item, 1, material.meta));
      Collections.sort(list, new Comparator()
      {
        public int compare(Object o1, Object o2)
        {
          if (((o1 instanceof ItemStack)) && ((o2 instanceof ItemStack)))
          {
            if (((ItemStack)o1).getItemDamage() < ((ItemStack)o2).getItemDamage()) {
              return -1;
            }
            if (((ItemStack)o1).getItemDamage() > ((ItemStack)o2).getItemDamage()) {
              return 1;
            }
          }
          return 0;
        }
      });
    }
  }
  
  public int getDamageValue(World world, int x, int y, int z)
  {
    TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
    if ((te instanceof TileMetalChest)) {
      return ((TileMetalChest)te).getMaterialId();
    }
    return super.getDamageValue(world, x, y, z);
  }
  
  public int getRenderType()
  {
    return ClayiumCore.metalChestRenderId;
  }
  
  public boolean renderAsNormalBlock()
  {
    return false;
  }
  
  public boolean isOpaqueCube()
  {
    return false;
  }
  
  public boolean canChangeRenderType()
  {
    return false;
  }
  
  public void setInitialBlockBounds()
  {
    setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
  }
  
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int p_149673_1_, int p_149673_2_)
  {
    return Blocks.iron_block.getIcon(0, 0);
  }
  
  @SideOnly(Side.CLIENT)
  public int getRenderColor(int meta)
  {
    CMaterial material = CMaterials.getMaterialFromId(meta);
    return material == null ? super.getRenderColor(meta) : (material.colors[0][0] << 16) + (material.colors[0][1] << 8) + material.colors[0][2];
  }
  
  @SideOnly(Side.CLIENT)
  public int colorMultiplier(IBlockAccess world, int x, int y, int z)
  {
    TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
    if ((te instanceof TileMetalChest)) {
      return getRenderColor(((TileMetalChest)te).getMaterialId());
    }
    return super.colorMultiplier(world, x, y, z);
  }
  
  public String getUnlocalizedName(ItemStack itemStack)
  {
    CMaterial material = CMaterials.getMaterialFromId(itemStack.getItemDamage());
    return getUnlocalizedMetalChestName(this, material);
  }
  
  public static String getUnlocalizedMetalChestName(Block block, CMaterial material)
  {
    return block.getUnlocalizedName() + (material == null ? "" : new StringBuilder().append(".").append(material.name).toString());
  }
  
  public List getTooltip(ItemStack itemStack)
  {
    List res = new ArrayList();
    CMaterial material = CMaterials.getMaterialFromId(itemStack.getItemDamage());
    if (material != null)
    {
      if ((getContainerP(material) <= 1) && (UtilLocale.canLocalize("tooltip.MetalChest.capacity1"))) {
        res.add(UtilLocale.localizeAndFormat("tooltip.MetalChest.capacity1", new Object[] { Integer.valueOf(getContainerX(material)), Integer.valueOf(getContainerY(material)), Integer.valueOf(getContainerX(material) * getContainerY(material)) }));
      }
      if ((getContainerP(material) >= 2) && (UtilLocale.canLocalize("tooltip.MetalChest.capacity2"))) {
        res.add(UtilLocale.localizeAndFormat("tooltip.MetalChest.capacity2", new Object[] { Integer.valueOf(getContainerX(material)), Integer.valueOf(getContainerY(material)), Integer.valueOf(getContainerP(material)), Integer.valueOf(getContainerX(material) * getContainerY(material) * getContainerP(material)) }));
      }
    }
    return res;
  }
  
  public static void addChestMaterial(CMaterial material, int containerX, int containerY, int containerP, Configuration cfg)
  {
    containerX = cfg.getInt(material.name + "ChestWidth", "metalchest", containerX, 1, 13, "");
    containerY = cfg.getInt(material.name + "ChestHeight", "metalchest", containerY, 1, 8, "");
    containerP = cfg.getInt(material.name + "ChestNumberOfPages", "metalchest", containerP, 1, 8, "");
    addChestMaterial(material, containerX, containerY, containerP);
  }
  
  public static void addChestMaterial(CMaterial material, int containerX, int containerY, int containerP)
  {
    HashMap<String, Integer> value = new HashMap();
    value.put("x", Integer.valueOf(containerX));value.put("y", Integer.valueOf(containerY));value.put("p", Integer.valueOf(containerP));
    metalChestMaterials.put(material, value);
  }
  
  public static boolean containsKey(CMaterial material)
  {
    return metalChestMaterials.containsKey(material);
  }
  
  public static int getContainerX(CMaterial material)
  {
    return containsKey(material) ? ((Integer)((HashMap)metalChestMaterials.get(material)).get("x")).intValue() : 1;
  }
  
  public static int getContainerY(CMaterial material)
  {
    return containsKey(material) ? ((Integer)((HashMap)metalChestMaterials.get(material)).get("y")).intValue() : 1;
  }
  
  public static int getContainerP(CMaterial material)
  {
    return containsKey(material) ? ((Integer)((HashMap)metalChestMaterials.get(material)).get("p")).intValue() : 1;
  }
  
  public static HashMap<CMaterial, HashMap<String, Integer>> getChestMaterialMap()
  {
    return metalChestMaterials;
  }
}
