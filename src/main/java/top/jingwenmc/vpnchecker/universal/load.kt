package top.jingwenmc.vpnchecker.universal

import top.jingwenmc.vpnchecker.universal.VPNCheck.cache
import top.jingwenmc.vpnchecker.universal.tasks.ResetCache
import java.util.*

object load {
    var timer = Timer()
    @JvmStatic
    fun enable() {
        timer.schedule(ResetCache(), 0, 6 * 60 * 60 * 1000.toLong())
    }

    @JvmStatic
    fun disable() {
        cache = HashMap()
        timer.cancel()
    }
}