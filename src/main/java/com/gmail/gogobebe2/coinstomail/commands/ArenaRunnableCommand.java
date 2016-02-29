package com.gmail.gogobebe2.coinstomail.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaRunnableCommand extends RunnableCommand {
    public ArenaRunnableCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void run() {
        if (!getSender().hasPermission("cointstomail.admin")) {
            getSender().sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }
        else if (!(getSender() instanceof Player)) {
            getSender().sendMessage(ChatColor.RED + "Error! You need to be a player to use this command!");
            return;
        }
        Player player = (Player) getSender();

        if (getArgs().length == 0 || getArgs()[0].equalsIgnoreCase("help") || getArgs()[0].equalsIgnoreCase("h")) displayHelp(player);
        else {
            String arg1 = getArgs()[0];
            String arenaName = getArgs()[1];
            if (arg1.equalsIgnoreCase("define") || arg1.equalsIgnoreCase("create")) {
                // TODO: Create a new entry in config.
                player.sendMessage(ChatColor.AQUA + "Arena " + ChatColor.GREEN + arenaName + ChatColor.AQUA + " created.");
            }
            else if (arg1.equalsIgnoreCase("remove") || arg1.equalsIgnoreCase("delete")) {
                // TODO: Set the entry in config to null.
                player.sendMessage(ChatColor.AQUA + "Arena " + ChatColor.GREEN + arenaName + ChatColor.AQUA + " deleted.");
            }
            else if (arg1.equalsIgnoreCase("pos1") || arg1.equalsIgnoreCase("position1") || arg1.equalsIgnoreCase("spawn1")) {
                // TODO: Set the entry in config's pos1.
                player.sendMessage(ChatColor.AQUA + "Set spawn 1 for " + ChatColor.GREEN + arenaName + ChatColor.AQUA + ".");
            }
            else if (arg1.equalsIgnoreCase("pos2") || arg1.equalsIgnoreCase("position2") || arg1.equalsIgnoreCase("spawn2")) {
                // TODO: Set the entry in config's pos2.
                player.sendMessage(ChatColor.AQUA + "Set spawn 2 for " + ChatColor.GREEN + arenaName + ChatColor.AQUA + ".");
            }
        }
    }

    private void displayHelp(Player player) {
        // TODO: HELP MENU
    }
}
