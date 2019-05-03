package com.kojin15.clayium.util

import net.minecraft.util.text.TextComponentTranslation

/**
 * @author kojin15.
 */
object Localizer {
    fun translateToLocal(key: String) = TextComponentTranslation(key).formattedText
}