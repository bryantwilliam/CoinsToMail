package com.gmail.gogobebe2.coinstomail;

import com.gmail.gogobebe2.coinstomail.commands.ArenaCommand;
import com.gmail.gogobebe2.coinstomail.commands.CashoutCommand;
import com.gmail.gogobebe2.coinstomail.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting up " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Command command = null;

        if (label.equalsIgnoreCase("cashout")) command = new CashoutCommand(sender, args);
        else if (label.equalsIgnoreCase("arena")) command = new ArenaCommand(sender, args);

        if (command != null) {
            command.run();
            return true;
        }
        return false;
    }

    public static Main getInstance() {
        return instance;
    }
}
