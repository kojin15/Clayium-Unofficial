package com.kojin15.clayium.config

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
        config.setCategoryPropertyOrder(category, propOrder)

        category = CATEGORY_WORLD
        propOrder = mutableListOf()



        config.setCategoryLanguageKey(category, "$languageKey$category")
        config.setCategoryPropertyOrder(category, propOrder)
    }

    fun getConfigElements(): List<IConfigElement> {
        return config.categoryNames.asSequence().map(config::getCategory).filter(ConfigCategory::showInGui).map(::ConfigElement).toList()
    }

    private fun Property.setComments(category: String) {
        this.languageKey = "$languageKey$category.${this.name}"
        this.comment = Localizer.translateToLocal("${this.languageKey}.tooltip")
        this.comment += " [default: ${this.default}]"
    }
}