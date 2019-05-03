package com.kojin15.clayium

import net.minecraftforge.fml.relauncher.Side

/**
 * @author kojin15.
 */
abstract class CommonProxy {
    abstract val side: Side

    open fun preInit() = Unit
    open fun init() = Unit
    open fun postInit() = Unit
}