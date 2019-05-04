package com.kojin15.clayium

import com.kojin15.clayium.Clayium.CONFIG_GUI_FACTORY
import com.kojin15.clayium.Clayium.DEPENDENCIES
import com.kojin15.clayium.Clayium.KOTLIN_ADAPTER
import com.kojin15.clayium.Clayium.MOD_ID
import com.kojin15.clayium.Clayium.MOD_NAME
import com.kojin15.clayium.Clayium.MOD_VERSION
import com.kojin15.clayium.config.ConfigLoader
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.*
import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.toliner.korgelin.Korgelin
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * @author kojin15.
 */
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, acceptedMinecraftVersions = "[1.12]",
        modLanguage = "kotlin", modLanguageAdapter = KOTLIN_ADAPTER, guiFactory = CONFIG_GUI_FACTORY, dependencies = DEPENDENCIES, useMetadata = true)
object Clayium {
    const val MOD_ID = "clayium"
    const val MOD_NAME = "Clayium"
    const val MOD_VERSION = "0.1.0"

    const val KOTLIN_ADAPTER = "net.toliner.korgelin.KotlinAdapter"
    const val CONFIG_GUI_FACTORY = "com.kojin15.clayium.config.ConfigGuiFactory"
    const val DEPENDENCIES = "required-after:${Korgelin.MOD_ID};"

    @Mod.Metadata
    private lateinit var METADATA: ModMetadata

    @SidedProxy(clientSide = "com.kojin15.clayium.proxy.ClientProxy",
            serverSide = "com.kojin15.clayium.proxy.ServerProxy")
    lateinit var PROXY: CommonProxy

    val tabClayium = object : CreativeTabs(MOD_ID) {
        override fun getTabIconItem(): ItemStack = ItemStack(Items.CLAY_BALL)
    }

    @Mod.EventHandler
    fun construct(event: FMLConstructionEvent) {
        METADATA.modId = MOD_ID
        METADATA.name = MOD_NAME
        METADATA.description = "Clay Industry, for CpS (Clay blocks per Seconds) Challengers."

        METADATA.version = MOD_VERSION
        METADATA.authorList.add("kojin15")
        METADATA.credits = "deb_rk(Original author)"

        METADATA.autogenerated = false
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        ConfigLoader.init(event.modConfigurationDirectory)
        PROXY.preInit()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        PROXY.init()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        PROXY.postInit()
    }

    object ConfigProperty {
        //general

        //world
        var clayOreVeinNumber = 8
        var clayOreVeinSize = 24
        var clayOreVeinMinY = 24
        var clayOreVeinMaxY = 88

        var generateDenseClayOreVein = true
        var denseClayOreVeinSize = 10

        var largeDenseClayOreVeinNumber = 2
        var largeDenseClayOreVeinSize = 6
        var largeDenseClayOreVeinMinY = 10
        var largeDenseClayOreVeinMaxY = 16

    }
}

class KotlinAdapter : ILanguageAdapter {
    override fun supportsStatics(): Boolean = false

    override fun setProxy(target: Field, proxyTarget: Class<*>, proxy: Any) {
        target.set(proxyTarget.kotlin.objectInstance, proxy)
    }

    override fun getNewInstance(container: FMLModContainer, objectClass: Class<*>, classLoader: ClassLoader, factoryMarkedAnnotation: Method?): Any? {
        return objectClass.kotlin.objectInstance ?: objectClass.newInstance()
    }

    override fun setInternalProxies(mod: ModContainer?, side: Side?, loader: ClassLoader?) = Unit
}