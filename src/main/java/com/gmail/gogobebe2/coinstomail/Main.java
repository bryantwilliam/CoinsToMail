package com.gmail.gogobebe2.coinstomail;

import com.gmail.gogobebe2.coinstomail.commands.CashoutRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.CreateKitRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.duel.ArenaRunnableCommand;
import com.gmail.gogobebe2.coinstomail.commands.duel.DuelRunnableCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        Bukkit.getPluginManager().registerEvents(DuelRunnableCommand.getDuelListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        Runnable runnable = null;

        if (label.equalsIgnoreCase("coinstomail")) runnable = new Runnable() {
            @Override
            public void run() {
                String pointPrefix = ChatColor.BLACK + " - " + ChatColor.YELLOW;
                sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Coins To Mail Help Menu");
                sender.sendMessage(pointPrefix + "cashout - Command that sends email to owner about paypal stuff.");
                sender.sendMessage(pointPrefix + "arena - Admin arena setup command.");
                sender.sendMessage(pointPrefix + "duel|1v1 - Command used by general users for dueling.");
                sender.sendMessage(pointPrefix + "createkit|kit - Command to create a kit for the duel.");
                sender.sendMessage(pointPrefix + "coinstomail - This help menu.");
            }
        };
        else if (label.equalsIgnoreCase("cashout")) runnable = new CashoutRunnableCommand(sender, args);
        else if (label.equalsIgnoreCase("arena")) runnable = new ArenaRunnableCommand(sender, args);
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
