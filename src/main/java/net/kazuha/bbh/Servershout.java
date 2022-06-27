package net.kazuha.bbh;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;

public class Servershout extends Command {
    public Map<CommandSender, Long> cd = new HashMap<>();
    Title title =  ProxyServer.getInstance().createTitle().title((new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',lkq2.config.getString("at.titlemsg")))).create());
    public Servershout(String name) {
        super(name);
    }
    @Override
    public void execute(CommandSender sender, String[] arg) {
        if (sender instanceof ProxiedPlayer) {
            if (arg.length == 0) {
                sender.sendMessage(ChatColor.RED + "用法: /" + lkq2.config.getString("command") + " <消息>");
                return;
            }
            if (!sender.hasPermission("bhorn.use")) {
                sender.sendMessage("§c你没有使用喊话的权限！");
                return;
            }
            if (ShoutUtils.badServer(((ProxiedPlayer) sender).getServer().getInfo().getName())) {
                sender.sendMessage(ChatColor.RED + "你不能在这使用这个指令!");
                return;
                //黑名单服务器，不处理
            }
            if(cd.containsKey(sender)){
                if(System.currentTimeMillis()-cd.get(sender) < lkq2.config.getInt("cooldown") * 1000L){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',lkq2.config.getString("cooldownmsg").replace("%cd%", String.valueOf(lkq2.config.getInt("cooldown") - (System.currentTimeMillis()-cd.get(sender))/1000L))));
                    return;
                }
            }
            cd.put(sender, System.currentTimeMillis());
            String message = lkq2.config.getString("format");
            //Placeholder替换
            message = message.replace("%server%", ShoutUtils.getGroupName(((ProxiedPlayer) sender).getServer().getInfo().getName()));
            message = message.replace("%player%", sender.getName());
            message = ChatColor.translateAlternateColorCodes('&', message);
            //替换颜色代码
            StringBuilder gomessage = new StringBuilder();
            for (String s : arg) {
                gomessage.append(s).append(" ");
                //加入空格
            }
            String rawmsg = gomessage.toString();
            if (rawmsg.contains("@all") || rawmsg.contains("@全体")) {
                if (!sender.hasPermission("bhorn.atall")) {
                    sender.sendMessage("§c你没有@全体的权限！");
                    return;
                }
                rawmsg = rawmsg.replace("@all", "§2@all§f");
                rawmsg = rawmsg.replace("@全体", "§2@全体§f");
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    p.sendTitle(title);
                }
            }
            //判断点击传送是否启用？
            if (lkq2.config.getBoolean("teleport")) {
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    for (ProxiedPlayer pc : ProxyServer.getInstance().getPlayers()) {
                        if (rawmsg.contains("@" + pc)) {
                            if (!sender.hasPermission("bhorn.at")) {
                                sender.sendMessage("§c你没有使用@的权限！");
                                return;
                            }
                            rawmsg = rawmsg.replace("@" + pc, "§2@" + pc + "§f");
                            pc.sendTitle(title);
                        }
                    }
                    message = message.replace("%message%", rawmsg);
                    ShoutUtils.sendHoverMessage(p, message, "/wacgo " + ShoutUtils.getTeleportServer(((ProxiedPlayer) sender).getServer().getInfo().getName()));
                }
            } else {
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    for (ProxiedPlayer pc : ProxyServer.getInstance().getPlayers()) {
                        if (rawmsg.contains("@" + pc)) {
                            if (!sender.hasPermission("bhorn.at")) {
                                sender.sendMessage("§c你没有使用@的权限！");
                                return;
                            }
                            rawmsg = rawmsg.replace("@" + pc, "§2@" + pc + "§f");
                            pc.sendTitle(title);
                        }
                    }
                    message = message.replace("%message%", rawmsg);
                    p.sendMessage(message);
                }
            }
        } else {
            sender.sendMessage("§c你妈");
        }
    }
}
