package com.gmail.gogobebe2.coinstomail.duel;

import com.gmail.gogobebe2.coinstomail.commands.CashoutRunnableCommand;
import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class Duel {
    private Player player;
    private Player opponent;
    private Arena arena;
    private UUID duelUUID = UUID.randomUUID();
    private Location playerInitLocation;
    private Location opponentInitLocation;

    protected Duel(Player player, Player opponent, Arena arena, Location playerInitLocation, Location opponentInitLocation) {
        this.player = player;
        this.opponent = opponent;
        this.arena = arena;
        this.playerInitLocation = playerInitLocation;
        this.opponentInitLocation = opponentInitLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Duel && this.duelUUID.equals(((Duel) object).duelUUID);
    }

    public void leaveDuel(Player player, boolean won) {
        UUID playerUUID = player.getUniqueId();

        Location initialLocation;

        if (playerUUID.equals(this.player.getUniqueId())) initialLocation = playerInitLocation;
        else initialLocation = opponentInitLocation;

        player.sendMessage(ChatColor.GREEN + "Teleporting to initial location...");
        player.teleport(initialLocation);

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
    }

}
