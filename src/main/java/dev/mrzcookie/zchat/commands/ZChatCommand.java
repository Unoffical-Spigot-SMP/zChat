package dev.mrzcookie.zchat.commands;

import dev.mrzcookie.zchat.ConfigManager;
import dev.mrzcookie.zchat.ZChatPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ZChatCommand implements CommandExecutor, TabCompleter {
    private final ZChatPlugin plugin;

    public ZChatCommand(ZChatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration config = this.plugin.getConfigManager().getConfig("config");

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "version":
                    this.plugin.getMessageManager().send(sender, "zChat <gray>v" + this.plugin.getDescription().getVersion() + "</gray><newline>Created by <color:#3399ff>MrZCookie</color>");
                    break;
                case "reload":
                    ConfigManager configManager = this.plugin.getConfigManager();

                    configManager.reloadConfig("config");
                    configManager.copyConfigDefaults("config");
                    configManager.getConfig("config").options().copyDefaults(true);
                    configManager.saveConfig("config");

                    configManager.reloadConfig("phrases");
                    configManager.copyConfigDefaults("phrases");
                    configManager.getConfig("phrases").options().copyDefaults(true);
                    configManager.saveConfig("phrases");

                    this.plugin.getMessageManager().send(sender, config.getString("commands.zchat.messages.plugin-reloaded"));
                    break;
                default:
                    this.plugin.getMessageManager().send(sender, config.getString("messages.error.usage").replace("{usage}", "/" + label + " <version/reload>"));
                    break;
            }
        } else {
            this.plugin.getMessageManager().send(sender, config.getString("messages.error.usage").replace("{usage}", "/" + label + " <version/reload>"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();

        if (args.length == 1) {
            arguments.add("version");
            arguments.add("reload");
        }

        return arguments;
    }
}