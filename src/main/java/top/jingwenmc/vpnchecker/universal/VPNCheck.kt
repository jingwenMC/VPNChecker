package top.jingwenmc.vpnchecker.universal

import com.alibaba.fastjson.JSON
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.util.*

open class VPNCheck {
    var cache: MutableMap<String, IPType> = HashMap()
    var uuid: UUID? = null
    fun checkIp(ip: String): Map<IPType, UUID?> {
        val map: MutableMap<IPType, UUID?> = HashMap()
        uuid = UUID.randomUUID()
        if (cache.containsKey(ip)) {
            val ipType = cache[ip]
            val isBad = ipType != IPType.GOOD
            println("[VPNChecker]从缓存中读取:")
            println("UUID:" + uuid.toString())
            println("Cache Details: IsGood:$isBad using IP:$ip")
            if (isBad) {
                map[IPType.BAD] = uuid
                cache[ip] = IPType.BAD
            } else {
                map[IPType.GOOD] = uuid
                cache[ip] = IPType.GOOD
            }
            return map
        }
        val message = getFromUrl("https://api.iplegit.com/info?ip=$ip")
        val `object` = JSON.parseObject(message)
        return if (`object`.containsKey("bad")) {
            val bad = `object`.getBoolean("bad")
            if (bad) {
                map[IPType.BAD] = uuid
                cache[ip] = IPType.BAD
            } else {
                map[IPType.GOOD] = uuid
                cache[ip] = IPType.GOOD
            }
            println("[VPNChecker]检测成功:")
            println("UUID:" + uuid.toString())
            println("Details:$message using IP:$ip")
            map
        } else if (`object`.containsKey("msg")) {
            println("[VPNChecker]发生异常,错误记录:")
            println("Error UUID:" + uuid.toString())
            println("Error Details:$message using IP:$ip")
            map[IPType.ERROR] = uuid
            map
        } else {
            println("[VPNChecker]发生意外的异常,错误记录:")
            println("Error UUID:" + uuid.toString())
            println("Error Details:$message using IP:$ip")
            map[IPType.ERROR] = uuid
            map
        }
    }

    private fun getFromUrl(url: String): String? {
        try {
            val connection = URL(url).openConnection()
            connection.connectTimeout = 5000
            connection.readTimeout = 20000
            val stream = connection.getInputStream()
            return streamToString(stream)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun streamToString(source: InputStream): String {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        try {
            while (source.read(buffer).also { length = it } != -1) {
                result.write(buffer, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result.toString()
    }
}