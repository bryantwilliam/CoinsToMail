package com.gmail.gogobebe2.coinstomail.commands.duel;

import com.gmail.gogobebe2.coinstomail.arena.Arena;
import com.gmail.gogobebe2.coinstomail.arena.ArenaManager;
import com.gmail.gogobebe2.coinstomail.commands.RunnableCommand;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DuelRunnableCommand extends RunnableCommand {
    private static DuelListener listener = new DuelListener();

    public static DuelListener getDuelListener() {
        return listener;
    }

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

        joinArena(player, opponent, arena);
    }

    private void joinArena(Player player, Player opponent, Arena arena) {
        String joinMessage = ChatColor.DARK_GREEN + "Joining arena " + ChatColor.GREEN + arena.getName()
                + ChatColor.DARK_GREEN + "...";
        player.sendMessage(joinMessage);
        opponent.sendMessage(joinMessage);

        player.setGameMode(GameMode.ADVENTURE);
        opponent.setGameMode(GameMode.ADVENTURE);

        Location pos1 = arena.getPos1();
        Location pos2 = arena.getPos2();

        player.teleport(pos1);
        opponent.teleport(pos2);

        // TODO: give kits
    }

    private static class DuelListener implements Listener {
        @EventHandler
        private void onPlayerMove(PlayerMoveEvent event) {

        }

        @EventHandler
        private void onPlayerDeath(PlayerDeathEvent event) {

        }

        @EventHandler
        private void onPlayerRespawn(PlayerRespawnEvent event) {

        }
    }
}
