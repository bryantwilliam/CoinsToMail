package com.gmail.gogobebe2.coinstomail.duel;

import com.gmail.gogobebe2.coinstomail.commands.CashoutRunnableCommand;
import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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

    public void addInitialLocation(UUID playerUUID, Location initialLocation) {
        initialLocations.put(playerUUID, initialLocation);
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
        } else {
            loser = duel.getOpponent();
            winner = duel.getPlayer();
        }

        duels.remove(duel);
        duel.getArena().setBusy(false);

        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + winner.getDisplayName() + " won a duel with "
                + loser.getDisplayName() + "!!");

        if (initialLocations.containsKey(winner.getUniqueId())) leaveDuel(winner, true);
    }

    private void leaveDuel(Player player, boolean won) {
        UUID playerUUID = player.getUniqueId();

        player.sendMessage(ChatColor.GREEN + "Teleporting to initial location...");
        player.teleport(initialLocations.get(playerUUID));

        // TODO: reset old inventory.

        PlayerInventory inventory = player.getInventory();
        if (won) {
            int firstEmeraldSlot = inventory.first(Material.EMERALD);
            if (firstEmeraldSlot != -1) {
                ItemStack firstEmerald = inventory.getItem(firstEmeraldSlot);
                firstEmerald.setAmount(firstEmerald.getAmount() + 1);
            }
            else inventory.addItem(new ItemStack(Material.EMERALD, 1));
            player.sendMessage(ChatColor.LIGHT_PURPLE + "1 coin has been removed from your inventory for losing.");
        }
        else {
            CashoutRunnableCommand.removeCoins(1, inventory);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "1 coin has been removed from your inventory for losing.");
        }


        initialLocations.remove(playerUUID);
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
                player.spigot().respawn();
                if (initialLocations.containsKey(playerUUID)) leaveDuel(player, false);
            }
        }

    }

}
