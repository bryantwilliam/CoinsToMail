package com.gmail.gogobebe2.coinstomail.commands.duel;

import com.gmail.gogobebe2.coinstomail.Main;
import com.gmail.gogobebe2.coinstomail.commands.RunnableCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
            final String CONFIG_ARENA_PATH = "Arenas." + arenaName;
            final boolean ARENA_EXISTS = Main.getInstance().getConfig().contains(CONFIG_ARENA_PATH);
            String message = ChatColor.RED + "Arena" + arenaName + "does not exist!";
            switch (arg1) {
                case "define":
                case "create":
                    if (ARENA_EXISTS) {
                        createArena(CONFIG_ARENA_PATH);
                        message = ChatColor.AQUA + "Arena " + ChatColor.GREEN + arenaName + ChatColor.AQUA + " created.";
                    }
                    else message = ChatColor.RED + "Arena " + arenaName + " is already set!";
                    break;
                case "remove":
                case "delete":
                    if (ARENA_EXISTS) {
                        deleteArena(CONFIG_ARENA_PATH);
                        message = ChatColor.AQUA + "Arena " + ChatColor.GREEN + arenaName + ChatColor.AQUA + " deleted.";
                    }
                    break;
                case "pos1":
                case "position1":
                case "spawn1":
                    if (ARENA_EXISTS) {
                        setPosition(CONFIG_ARENA_PATH, PositionType.POS1, player.getLocation());
                        message = ChatColor.AQUA + "Set spawn 1 for " + ChatColor.GREEN + arenaName + ChatColor.AQUA + ".";
                    }
                    break;
                case "pos2":
                case "position2":
                case "spawn2":
                    if (ARENA_EXISTS) {
                        setPosition(CONFIG_ARENA_PATH, PositionType.POS2, player.getLocation());
                        message = ChatColor.AQUA + "Set spawn 2 for " + ChatColor.GREEN + arenaName + ChatColor.AQUA + ".";
                    }
                    break;
                default:
                    displayHelp(player);
                    return;
            }
            player.sendMessage(message);
        }
    }

    private void createArena(final String CONFIG_ARENA_PATH) {
        Main main = Main.getInstance();
        main.getConfig().set(CONFIG_ARENA_PATH + ".pos1", -1);
        main.getConfig().set(CONFIG_ARENA_PATH + ".pos2", -1);
        main.saveConfig();
    }

    private void deleteArena(final String CONFIG_ARENA_PATH) {
        Main main = Main.getInstance();
        main.getConfig().set(CONFIG_ARENA_PATH, null);
        main.saveConfig();
    }

    private void setPosition(final String CONFIG_ARENA_PATH, PositionType positionType, Location location) {
        Main main = Main.getInstance();
        String posPathPrefix = CONFIG_ARENA_PATH + "." + positionType.getConfigId() + ".";
        main.getConfig().set(posPathPrefix + "x", location.getX());
        main.getConfig().set(posPathPrefix + "y", location.getY());
        main.getConfig().set(posPathPrefix + "z", location.getZ());
        main.getConfig().set(posPathPrefix + "pitch", location.getPitch());
        main.getConfig().set(posPathPrefix + "yaw", location.getYaw());
        main.saveConfig();
    }

    private void displayHelp(Player player) {
        player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "CoinsToMail Admin Help Menu");
        String bulletPointSuffix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + " - " + ChatColor.BLUE + "/duel "
                + ChatColor.GOLD + "<";
        player.sendMessage(bulletPointSuffix + "define/create>");
        player.sendMessage(bulletPointSuffix + "remove/delete>");
        player.sendMessage(bulletPointSuffix + "pos1/position1/spawn1>");
        player.sendMessage(bulletPointSuffix + "pos2/position2/spawn2>");
    }
}
