package com.gmail.gogobebe2.coinstomail.duel;

import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

public class DuelManager implements Listener {
    private static DuelManager instance;

    static {
        instance = new DuelManager();
    }

    public static DuelManager getInstance() {
        return instance;
    }

    private Set<Duel> duels = new HashSet<>();

    private DuelManager() {

    }

    public void startDuel(Player player, Player opponent, Arena arena, Location playerInitLocation, Location opponentInitLocation) {
        duels.add(new Duel(player, opponent, arena, playerInitLocation, opponentInitLocation));
    }

    private void endDuel(Duel duel, UUID loserUUID) {
        Player winner;
        Player loser;

        if (loserUUID.equals(duel.getPlayer().getUniqueId())) {
            loser = duel.getPlayer();
            winner = duel.getOpponent();
        } else {
            loser = duel.getOpponent();
            winner = duel.getPlayer();
        }

        duels.remove(duel);
        duel.getArena().setBusy(false);

        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + winner.getDisplayName() + " won a duel with "
                + loser.getDisplayName() + "!!");

        duel.leaveDuel(winner, true);
        duel.leaveDuel(loser, false);
    }



    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        // TODO: create countdown.
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerUUID = player.getUniqueId();
        for (Duel duel : duels) {
            if (playerUUID.equals(duel.getPlayer().getUniqueId()) || playerUUID.equals(duel.getOpponent().getUniqueId())) {
                player.spigot().respawn();
                endDuel(duel, playerUUID);
            }
        }

    }

}
