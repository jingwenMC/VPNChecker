package top.jingwenmc.vpnchecker.bungee;

import cn.dreamlessrealm.dreamlessrealmban.utils.types.Bans;
import com.google.common.collect.Iterables;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import top.dsbbs2.bukkitcord.api.PlatformManager;
import top.dsbbs2.bukkitcord.bungee.BungeePluginImpl;
import top.jingwenmc.vpnchecker.universal.IPType;
import top.jingwenmc.vpnchecker.universal.VPNCheck;
import top.jingwenmc.vpnchecker.universal.load;

import java.util.*;

public class main extends BungeePluginImpl implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("正在以 BungeeCord模式 加载插件");
        getProxy().getPluginManager().registerListener(this,this);
        load.enable();
    }

    @Override
    public void onDisable() {
        getLogger().info("正在禁用插件");
        load.disable();
    }
    @EventHandler
    public void onConn(PostLoginEvent event)
    {
        Map<IPType, UUID> map = VPNCheck.INSTANCE.checkIp(event.getPlayer().getAddress().getAddress().getHostAddress());
        UUID uuid = Iterables.getOnlyElement(map.values());
        if(map.containsKey(IPType.BAD))
        {
            Bans.COMPROMISED_ACCOUNT.actionsForever(PlatformManager.INSTANCE.getPlatform().getConSoleSender()
                    , new String[]{event.getPlayer().getName()});
        }
        if(map.containsKey(IPType.GOOD))return;
        event.getPlayer().disconnect(ChatColor.RED+"发生错误,请反馈服务器管理员,错误编号:"+uuid.toString());
    }
}
