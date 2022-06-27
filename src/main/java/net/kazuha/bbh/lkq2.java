package net.kazuha.bbh;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

public class lkq2 extends Plugin {
    public static Configuration config;
    public static Plugin instance;
    @Override
    public void onEnable() {
        instance = this;
        saveConfigFile();
        getLogger().info("============================================");
        getLogger().info(ChatColor.DARK_AQUA + "Bungee" + ChatColor.YELLOW + "Bullhorn" + ChatColor.GRAY + " By " + ChatColor.AQUA + "Kazuha" + ChatColor.GOLD + "Ayato");
        getLogger().info("插件版本" + ChatColor.DARK_GREEN + getDescription().getVersion());
        getLogger().info("============================================");
        getLogger().info("加载配置文件..");
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getProxy().getPluginManager().registerCommand(this, new Servershout(config.getString("command")));
        getProxy().getPluginManager().registerCommand(this, new wacgo("wacgo"));
        getProxy().getPluginManager().registerCommand(this, new bbh("bhorn"));
        getProxy().getPluginManager().registerCommand(this, new pm("pm"));
        getLogger().info("加载完成！");
        }
    @Override
    public void onDisable(){
    }
    public void saveConfigFile() {
        File dir = getDataFolder();
        if (!dir.exists()) dir.mkdir();
        File file = new File(dir, "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}