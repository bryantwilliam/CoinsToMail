package com.gmail.gogobebe2.coinstomail.commands;

import com.gmail.gogobebe2.coinstomail.duel.DuelManager;
import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import com.gmail.gogobebe2.coinstomail.duel.arena.ArenaManager;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DuelRunnableCommand extends RunnableCommand {

    public DuelRunnableCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void run() {
        Player player;
        if (getSender() instanceof Player) player = (Player) getSender();
        else {
            getSender().sendMessage(ChatColor.RED + "Error! You need to be a player to use this command!");
            return;
        }

        String usage = ChatColor.AQUA + "/duel|1v1 <opponent>/accept/all|anyone/help";
        if (getArgs().length == 0) {
            player.sendMessage(ChatColor.RED + "Error! Incorrect command usage, use it like: " + usage);
            return;
        }

        String arg = getArgs()[0];

        if (arg.equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.AQUA + "Duel usage: " + usage);
            return;
        }

        DuelManager duelManager = DuelManager.getInstance();

        if (arg.equalsIgnoreCase("accept") || arg.equalsIgnoreCase("all")) {
            player.sendMessage(ChatColor.GREEN + "Accepting duel...");

            Player opponent = duelManager.onAccept(player.getUniqueId());
            if (opponent != null) {
                player.sendMessage(ChatColor.GREEN + "Duel accepted successfully.");
                opponent.sendMessage(ChatColor.GREEN + "Your duel request was accepted by " + player.getName());
            }
            else {
                player.sendMessage(ChatColor.RED + "Error! You don't have any duel requests pending.");
                return;
            }

            Arena arena = ArenaManager.getInstance().getNextFreeArena();

            if (arena == null) {
                String message = ChatColor.RED + "There are no arenas available. Please try again later.";
                player.sendMessage(message);
                opponent.sendMessage(message);
                return;
            }

            arena.joinArena(player, opponent, arena);
            return;
        }

        UUID[] playerUUIDS;

        if (arg.equalsIgnoreCase("anyone")) {
            List<? extends Player> onlinePlayers = Lists.newArrayList(Bukkit.getOnlinePlayers());
            if (onlinePlayers.size() != 0) {
                playerUUIDS = new UUID[onlinePlayers.size()];
                for (int i = 0; i < onlinePlayers.size(); i++) {
                    Player onlinePlayer = onlinePlayers.get(i);
                    playerUUIDS[i] = onlinePlayer.getUniqueId();
                }
                player.sendMessage(ChatColor.GREEN + "Duel Request sent to everyone.");
            }
            else {
                player.sendMessage(ChatColor.RED + "Error! There's no one online!");
                return;
            }
        } else {
            OfflinePlayer potentialOpponenet = Bukkit.getOfflinePlayer(arg);
            if (potentialOpponenet.isOnline()) {
                playerUUIDS = new UUID[]{potentialOpponenet.getUniqueId()};
                player.sendMessage(ChatColor.GREEN + "Duel Request sent to " + potentialOpponenet.getName());
            }
            else {
                player.sendMessage(ChatColor.RED + "Error! " + potentialOpponenet.getName() + " is not online!");
                return;
            }
        }

        for (UUID playerUUID : playerUUIDS) {
            Bukkit.getPlayer(playerUUID).sendMessage(ChatColor.AQUA + "Duel request received from " + player.getName()
                    + ", type " + ChatColor.BLUE + "/duel accept " + ChatColor.AQUA + "to accept.");
        }

        duelManager.onRequest(player.getUniqueId(), playerUUIDS);
    }
}
