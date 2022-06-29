package net.kazuha.bbh;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.util.List;
public class ShoutUtils {
    public static void sendHoverMessage(ProxiedPlayer player, String message, String command) {
        try {
            TextComponent component = new TextComponent(message);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            player.sendMessage(component);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getGroupName(String serverid) {
        Configuration sect = lkq2.config.getSection("server-groups");
        if(sect == null) {
            return serverid;
        }
        for(String name : sect.getKeys()){
            List<String> Server = sect.getStringList(name+".servers");
            if (Server.contains(serverid)){
                return sect.getString(name+".displayname");
            }
        }
        return serverid;
    }
    public static boolean badServer(String serverid) {
        List<String> arg = lkq2.config.getStringList("disabled-servers");
        return arg.contains(serverid);
    }
    public static String getTeleportServer(String serverid) {
        Configuration sect = lkq2.config.getSection("server-groups");
        if(sect == null)return serverid;
        for(String name : sect.getKeys()){
            List<String> Server = sect.getStringList(name+".servers");
            if (Server.contains(serverid)){
                if(sect.getString(name+".teleport-server").equalsIgnoreCase("none")){
                    return serverid;
                }
                return sect.getString(name+".teleport-server");
            }
        }
    return serverid;
    }
    public static boolean isServerleagal(String serverid){
        Configuration sect = lkq2.config.getSection("server-groups");

        for(String name : sect.getKeys()) {
            List<String> Server = sect.getStringList(name+".servers");
            if (Server.contains(serverid)){
                if(sect.getString(name+".teleport-server").equals(serverid)){
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
