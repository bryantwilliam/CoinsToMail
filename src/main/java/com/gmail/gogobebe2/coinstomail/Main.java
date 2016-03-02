package com.gmail.gogobebe2.coinstomail;

import com.gmail.gogobebe2.coinstomail.commands.CashoutRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.CreateKitRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.duel.ArenaRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.duel.DuelRunnableCommand;
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
        else if (label.equalsIgnoreCase("duel")) runnable = new ArenaRunnableCommand(sender, args);
        else if (label.equalsIgnoreCase("duel") || label.equalsIgnoreCase("1v1")) runnable = new DuelRunnableCommand(sender, args);
        else if (label.equalsIgnoreCase("createkit") || label.equalsIgnoreCase("kit")) runnable = new CreateKitRunnableCommand(sender, args);

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
