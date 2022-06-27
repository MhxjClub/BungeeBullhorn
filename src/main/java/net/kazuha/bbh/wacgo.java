package net.kazuha.bbh;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class wacgo extends Command {
    public wacgo(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if (!ShoutUtils.badServer(((ProxiedPlayer) commandSender).getServer().getInfo().getName())) {
                if (strings.length == 0) {
                    return;
                }
                if (ShoutUtils.isServerleagal(strings[0])) {
                    ServerInfo target = ProxyServer.getInstance().getServerInfo(strings[0]);
                    player.connect(target);
                }else{
                    commandSender.sendMessage(ChatColor.DARK_RED + "非法请求，请重试。。");
                }
            }
        }
    }
}