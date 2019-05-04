package com.kojin15.clayium.config

import com.kojin15.clayium.Clayium
import com.kojin15.clayium.config.ConfigLoader.setComments
import com.kojin15.clayium.util.Localizer
import net.minecraftforge.common.config.ConfigCategory
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.client.config.IConfigElement
import java.io.File

/**
 * @author kojin15.
 */
object ConfigLoader {
    private lateinit var config: Configuration

    const val languageKey = "config.clayium."
    private const val CATEGORY_WORLD = "world"

    fun init(file: File) {
        val cfgFile = File(file, "kojin15/clayium.cfg")
        config = Configuration(cfgFile)

        try {
            config.load()
            load()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            config.save()
        }
    }

    private fun load() {
        var category = Configuration.CATEGORY_GENERAL
        var prop: Property
        var propOrder = mutableListOf<String>()



        config.setCategoryLanguageKey(category, "$languageKey$category")
        config.setCategoryComment(category, Localizer.translateToLocal("${this.languageKey}$category.tooltip"))
        config.setCategoryPropertyOrder(category, propOrder)



        category = CATEGORY_WORLD
        propOrder = mutableListOf()

        prop = config[category, "clayOreVeinNumber", 8]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.clayOreVeinNumber = prop.int

        prop = config[category, "clayOreVeinSize", 24]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.clayOreVeinSize = prop.int

        prop = config[category, "clayOreVeinMinY", 24]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.clayOreVeinMinY = prop.int

        prop = config[category, "clayOreVeinMaxY", 88]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.clayOreVeinMaxY = prop.int


        prop = config[category, "generateDenseClayOreVein", true]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.generateDenseClayOreVein = prop.boolean

        prop = config[category, "denseClayOreVeinSize", 10]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.denseClayOreVeinSize = prop.int


        prop = config[category, "largeDenseClayOreVeinNumber", 2]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.largeDenseClayOreVeinNumber = prop.int

        prop = config[category, "largeDenseClayOreVeinSize", 6]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.largeDenseClayOreVeinSize = prop.int

        prop = config[category, "largeDenseClayOreVeinMinY", 10]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.largeDenseClayOreVeinMinY = prop.int

        prop = config[category, "largeDenseClayOreVeinMaxY", 16]
        prop.setComments(category)
        propOrder.add(prop.name)
        Clayium.ConfigProperty.largeDenseClayOreVeinMaxY = prop.int

        config.setCategoryLanguageKey(category, "$languageKey$category")
        config.setCategoryComment(category, Localizer.translateToLocal("${this.languageKey}$category.tooltip"))
        config.setCategoryPropertyOrder(category, propOrder)
    }

    fun getConfigElements(): List<IConfigElement> {
        return config.categoryNames.asSequence().map(config::getCategory).filter(ConfigCategory::showInGui).map(::ConfigElement).toList()
    }

    private fun Property.setComments(category: String) {
        this.languageKey = "${this@ConfigLoader.languageKey}$category.${this.name}"
        this.comment = Localizer.translateToLocal("${this.languageKey}.tooltip")
        this.comment += " [default: ${this.default}]"
    }
}