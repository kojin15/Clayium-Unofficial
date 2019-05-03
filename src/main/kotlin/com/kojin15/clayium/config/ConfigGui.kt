package com.kojin15.clayium.config

import com.kojin15.clayium.Clayium
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.resources.I18n
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.config.GuiConfig

/**
 * @author kojin15.
 */
class ConfigGui(parent: GuiScreen?) :
        GuiConfig(parent, ConfigLoader.getConfigElements(), Clayium.MOD_ID,
                false, false, I18n.format("${ConfigLoader.languageKey}title"))

class ConfigGuiFactory : IModGuiFactory {
    override fun initialize(minecraftInstance: Minecraft?) = Unit
    override fun createConfigGui(parentScreen: GuiScreen?): GuiScreen = ConfigGui(parentScreen)
    override fun runtimeGuiCategories(): MutableSet<IModGuiFactory.RuntimeOptionCategoryElement>? = null
    override fun hasConfigGui(): Boolean = true
}