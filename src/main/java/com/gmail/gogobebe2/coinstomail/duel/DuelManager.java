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
import org.bukkit.event.player.PlayerRespawnEvent;

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
    private Map<UUID, Location> initialLocations = new HashMap<>(); // <playerUUID, initialLocation>

    private DuelManager() {

    }

    public void startDuel(Player player, Player opponent, Arena arena) {
        duels.add(new Duel(player, opponent, arena));
    }

    private void endDuel(Duel duel, UUID loserUUID) {
        Player winner;
        Player loser;

        if (loserUUID.equals(duel.getPlayer().getUniqueId())) {
            loser = duel.getPlayer();
            winner = duel.getOpponent();
        }
        else {
            loser = duel.getOpponent();
            winner = duel.getPlayer();
        }

        duels.remove(duel);
        duel.getArena().setBusy(false);

        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + winner.getDisplayName() + " won a duel with "
                + loser.getDisplayName() + "!!");
        // TODO: -1 coin from loser, +1 coin to winner.
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
                endDuel(duel, playerUUID);
            }
        }
    }

    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (initialLocations.containsKey(playerUUID)) {
            player.teleport(initialLocations.get(playerUUID));
            player.sendMessage(ChatColor.GREEN + "Teleporting to initial location...");
            initialLocations.remove(playerUUID);
        }
    }
}
