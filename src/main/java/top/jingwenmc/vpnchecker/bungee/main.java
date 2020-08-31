package top.jingwenmc.vpnchecker.bungee;

import top.dsbbs2.bukkitcord.bungee.BungeePluginImpl;

public class main extends BungeePluginImpl {
    @Override
    public void onEnable() {
        getLogger().info("正在以 BungeeCord模式 加载插件");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
