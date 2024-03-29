package top.jingwenmc.vpnchecker.bukkit;

import cn.dreamlessrealm.dreamlessrealmban.utils.types.*;
import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import top.dsbbs2.bukkitcord.api.PlatformManager;
import top.dsbbs2.bukkitcord.bukkit.BukkitPluginImpl;
import top.jingwenmc.vpnchecker.universal.IPType;
import top.jingwenmc.vpnchecker.universal.VPNCheck;
import top.jingwenmc.vpnchecker.universal.load;

import java.util.Map;
import java.util.UUID;

public final class main extends BukkitPluginImpl implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("正在以 Bukkit模式 加载插件");
        Bukkit.getPluginManager().registerEvents(this,this);
        load.enable();
    }

    @Override
    public void onDisable() {
        getLogger().info("正在禁用插件");
        load.disable();
    }

    @EventHandler
    public void onConn(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPermission("vpncheck.bypass"))
        {
            System.out.println("[VPNChecker]玩家拥有权限节点,故绕过检测.");
            return;
        }
        new BukkitRunnable()
        {
            @Override
            public void run() {
                Map<IPType, UUID> map = VPNCheck.INSTANCE.checkIp(player.getAddress().getAddress().getHostAddress());
                UUID uuid = Iterables.getOnlyElement(map.values());
                if(map.containsKey(IPType.BAD))
                {
                    //Bans.COMPROMISED_ACCOUNT.actionsForever(PlatformManager.INSTANCE.getPlatform().getConSoleSender()
                    //        , new String[]{player.getName()});
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"ban "+player.getName()+" [Southplex账号安全]不安全的账号");
                    return;
                }
                if(map.containsKey(IPType.GOOD))return;
                player.kickPlayer(ChatColor.RED+"发生错误,请反馈服务器管理员,错误编号:"+uuid.toString());
            }
        }.runTaskAsynchronously(this);
    }
}
