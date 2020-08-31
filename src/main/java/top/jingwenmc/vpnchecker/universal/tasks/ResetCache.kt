package top.jingwenmc.vpnchecker.universal.tasks

import top.jingwenmc.vpnchecker.universal.VPNCheck.cache
import java.util.*

class ResetCache : TimerTask() {
    override fun run() {
        cache = HashMap()
    }
}