package net.kazuha.bbh;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
        for(int i=0; i <= 256; i++){
            List<String> Server = lkq2.config.getStringList("server-groups.group"+i+".servers");
            if (Server.contains(serverid)){
                return lkq2.config.getString("server-groups.group"+i+".displayname");
            }

        }
        return serverid;
    }
    public static boolean badServer(String serverid) {
        List<String> arg = lkq2.config.getStringList("disabled-servers");
        return arg.contains(serverid);
    }
    public static String getTeleportServer(String serverid) {
        for(int i=0; i <= 256; i++){
            List<String> Server = lkq2.config.getStringList("server-groups.group"+i+".servers");
            if(lkq2.config.getString("server-groups.group" + i + ".teleport-server").equals("-1" )){
                return serverid;
            }
            if (Server.contains(serverid) && !lkq2.config.getString("server-groups.group" + i + ".teleport-server").equals("-1")){
                return lkq2.config.getString("server-groups.group"+i+".teleport-server");
            }

        }
    return serverid;
    }
    public static boolean isServerleagal(String serverid){
        for(int i=0; i <= 256; i++){
            List<String> Server = lkq2.config.getStringList("server-groups.group"+i+".servers");
            if (Server.contains(serverid)){
                if(lkq2.config.getString("server-groups.group" + i + ".teleport-server").equals(serverid)){
                    return true;
                }
                return false;
            }

        }
        return true;
    }
}
