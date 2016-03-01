package com.gmail.gogobebe2.coinstomail.commands.arena;

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
            final String configArenaPath = "Arenas." + arenaName;
            switch (arg1) {
                case "define":
                case "create":
                    if (tryCreateArena(configArenaPath)) player.sendMessage(ChatColor.AQUA + "Arena "
                            + ChatColor.GREEN + arenaName + ChatColor.AQUA + " created.");
                    else player.sendMessage(ChatColor.RED + "Arena " + arenaName + " is already set!");
                    break;
                case "remove":
                case "delete":
                    if (tryDeleteArena(configArenaPath)) player.sendMessage(ChatColor.AQUA
                            + "Arena " + ChatColor.GREEN + arenaName + ChatColor.AQUA + " deleted.");
                    else player.sendMessage(ChatColor.RED + "Arena " + arenaName + " does not exist!");
                    break;
                case "pos1":
                case "position1":
                case "spawn1":
                    if (trySetPosition(configArenaPath, PositionType.POS1, player.getLocation())) {
                        player.sendMessage(ChatColor.AQUA + "Set spawn 1 for " + ChatColor.GREEN + arenaName + ChatColor.AQUA + ".");
                    }
                    else player.sendMessage(ChatColor.RED + "Arena" + arenaName + "does not exist!");
                    break;
                case "pos2":
                case "position2":
                case "spawn2":
                    if (trySetPosition(configArenaPath, PositionType.POS2, player.getLocation())) {
                        player.sendMessage(ChatColor.AQUA + "Set spawn 2 for " + ChatColor.GREEN + arenaName + ChatColor.AQUA + ".");
                    }
                    else player.sendMessage(ChatColor.RED + "Arena" + arenaName + "does not exist!");
                    break;
                default:
                    displayHelp(player);
            }
        }
    }

    private boolean arenaExists(String configArenaPath) {
        return Main.getInstance().getConfig().contains(configArenaPath);
    }

    /**
     * @return If the arena wanting to be created exists.
     */
    private boolean tryCreateArena(String configArenaPath) {
        if (arenaExists(configArenaPath)) return false;
        Main main = Main.getInstance();
        main.getConfig().set(configArenaPath + ".pos1", -1);
        main.getConfig().set(configArenaPath + ".pos2", -1);
        main.saveConfig();
        return true;
    }

    /**
     * @return If the arena wanting to be deleted exists.
     */
    private boolean tryDeleteArena(String configArenaPath) {
        if (!arenaExists(configArenaPath)) return false;
        Main main = Main.getInstance();
        main.getConfig().set(configArenaPath, null);
        main.saveConfig();
        return true;
    }

    /**
     * @return If the arena wanting to set position exists.
     */
    private boolean trySetPosition(String configArenaPath, PositionType positionType, Location location) {
        if (!arenaExists(configArenaPath)) return false;
        Main main = Main.getInstance();
        String posPathPrefix = configArenaPath + "." + positionType.getConfigId() + ".";
        main.getConfig().set(posPathPrefix + "x", location.getX());
        main.getConfig().set(posPathPrefix + "y", location.getY());
        main.getConfig().set(posPathPrefix + "z", location.getZ());
        main.getConfig().set(posPathPrefix + "pitch", location.getPitch());
        main.getConfig().set(posPathPrefix + "yaw", location.getYaw());
        main.saveConfig();
        return true;
    }

    private void displayHelp(Player player) {
        player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "CoinsToMail Admin Help Menu");
        String bulletPointSuffix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + " - " + ChatColor.BLUE + "/arena "
                + ChatColor.GOLD + "<";
        player.sendMessage(bulletPointSuffix + "define/create>");
        player.sendMessage(bulletPointSuffix + "remove/delete>");
        player.sendMessage(bulletPointSuffix + "pos1/position1/spawn1>");
        player.sendMessage(bulletPointSuffix + "pos2/position2/spawn2>");
    }
}
