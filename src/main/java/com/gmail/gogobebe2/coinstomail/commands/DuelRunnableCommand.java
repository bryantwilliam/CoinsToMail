package com.gmail.gogobebe2.coinstomail.commands;

import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import com.gmail.gogobebe2.coinstomail.duel.arena.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        Player opponent;

        if (getArgs().length == 0) {
            player.sendMessage(ChatColor.RED + "Error! Incorrect command usage, use it like: "
                    + ChatColor.AQUA + "/<duel|1v1> <opponent>");
            return;
        }

        String opponentName = getArgs()[0];
        OfflinePlayer potentialOpponenet = Bukkit.getOfflinePlayer(opponentName);
        if (potentialOpponenet.isOnline()) opponent = potentialOpponenet.getPlayer();
        else {
            player.sendMessage(ChatColor.RED + "Error! " + opponentName + " is not online!");
            return;
        }

        Arena arena = ArenaManager.getInstance().getNextFreeArena();
        if (arena == null) {
            player.sendMessage(ChatColor.RED + "There are no arenas available. Please try again later.");
            return;
        }

        arena.joinArena(player, opponent, arena);
    }
}
