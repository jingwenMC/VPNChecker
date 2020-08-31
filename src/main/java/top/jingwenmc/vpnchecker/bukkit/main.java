package top.jingwenmc.vpnchecker.bukkit;

import top.dsbbs2.bukkitcord.bukkit.BukkitPluginImpl;

public final class main extends BukkitPluginImpl {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("正在以 Bukkit模式 加载插件");
    }

    @Override
    public void onDisable() {
        getLogger().info("正在禁用插件");
        // Plugin shutdown logic
    }
}
