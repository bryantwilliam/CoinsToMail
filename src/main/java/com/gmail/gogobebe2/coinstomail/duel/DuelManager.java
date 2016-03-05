package com.gmail.gogobebe2.coinstomail.duel;

import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashSet;
import java.util.Set;

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

    public void startDuel(Player player, Player opponent, Arena arena) {
        duels.add(new Duel(player, opponent, arena));
    }

    private void endDuel(Duel duel) {

    }

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
