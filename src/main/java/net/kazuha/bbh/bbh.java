package net.kazuha.bbh;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class bbh extends Command {
    public bbh(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] arg) {
        if(arg.length==0){
            sender.sendMessage("§9Bungee§eBullhorn §7By §bKazuha§6Ayato");
            sender.sendMessage("§7§o君问归期未有期,巴山夜雨涨秋池");
            sender.sendMessage("§f可用命令列表：");
            if(sender.hasPermission("bhorn.use")){
                sender.sendMessage("§2/"+ lkq2.config.getString("command") + " <内容> §f喊话");
                sender.sendMessage("§f能否@玩家：" + trueorfalse(sender.hasPermission("bhorn.at")));
                sender.sendMessage("§f能否@全体：" + trueorfalse(sender.hasPermission("bhorn.atall")));
            }
            if(sender.hasPermission("bhorn.pm")){
                sender.sendMessage("§2/pm <目标> <消息> §f发送私聊");
            }
             if(sender.hasPermission("bhorn.op")){
                sender.sendMessage("§2/bhorn reload §f重载配置");
                sender.sendMessage("§f插件版本:§2" + lkq2.instance.getDescription().getVersion());
            }
        }
        if(arg.length < 1) {
            return;
        }
            if(arg[0].equals("reload")){
                try {
                    lkq2.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(lkq2.instance.getDataFolder(), "config.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage("§9Bungee§eBullhorn §f已经重新载入。插件版本§2" + lkq2.instance.getDescription().getVersion());
            }
        }

    public String trueorfalse(Boolean wtf){
        if(wtf){
            return "§2可";
        }else{
            return "§4不可";
        }
    }
}
