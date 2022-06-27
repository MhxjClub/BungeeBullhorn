package net.kazuha.bbh;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;

/*
private:
        enabled: true
        format: "&f &l::&dPM &8[&a%sender% &f-> &e%reciever%&8] &8> &f%message%"
*/
public class pm extends Command {
    public Map<CommandSender,Long> cdt = new HashMap<>();
    public pm(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] arg) {
        if(!sender.hasPermission("bhorn.pm")){
            sender.sendMessage("§c你没有权限.");
            return;
        }
        if(cdt.containsKey(sender)){
            if((System.currentTimeMillis() - cdt.get(sender)) < (lkq2.config.getInt("private.cooldown")*1000L)){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',lkq2.config.getString("cooldownmsg").replace("%cd%", String.valueOf(lkq2.config.getInt("cooldown") - (System.currentTimeMillis()-cdt.get(sender))/1000L))));
                return;
            }
        }else{
            cdt.put(sender, System.currentTimeMillis());
        }
        cdt.put(sender, System.currentTimeMillis());
        if(arg.length < 1){
            sender.sendMessage("§c请提供目标");
            return;
        }
        if(arg.length < 2){
            sender.sendMessage("§c请提供消息");
            return;
        }
        if(arg[0].equals(sender.getName())){
            sender.sendMessage("§c你不可以给自己发送私聊");
            return;
        }

        if(ProxyServer.getInstance().getPlayer(arg[0]) == null){
            sender.sendMessage("§c该玩家不在线");
            return;
        }
        if(!ProxyServer.getInstance().getPlayer(arg[0]).isConnected()){
            sender.sendMessage("§c该玩家不在线");
            return;
        }

        StringBuilder sendmag = new StringBuilder();
        for(int c=1; c<arg.length; c++)sendmag.append(arg[c]).append(" ");
        String pmformat = lkq2.config.getString("private.format");
        pmformat = ChatColor.translateAlternateColorCodes('&',pmformat.replace("%sender%", String.valueOf(sender)).replace("%reciever%", arg[0]).replace("%message%", String.valueOf(sendmag)));
        ProxyServer.getInstance().getPlayer(arg[0]).sendMessage(pmformat);
        ProxyServer.getInstance().getConsole().sendMessage(pmformat);
        sender.sendMessage(pmformat);
    }
}
