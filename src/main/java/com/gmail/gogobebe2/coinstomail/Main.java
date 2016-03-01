package com.gmail.gogobebe2.coinstomail;

import com.gmail.gogobebe2.coinstomail.commands.arena.ArenaRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.CashoutRunnableCommand;
import org.bukkit.command.Command;
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Runnable runnable = null;

        if (label.equalsIgnoreCase("cashout")) runnable = new CashoutRunnableCommand(sender, args);
        else if (label.equalsIgnoreCase("arena")) runnable = new ArenaRunnableCommand(sender, args);

        if (runnable != null) {
            runnable.run();
            return true;
        }
        return false;
    }

    public static Main getInstance() {
        return instance;
    }
}
