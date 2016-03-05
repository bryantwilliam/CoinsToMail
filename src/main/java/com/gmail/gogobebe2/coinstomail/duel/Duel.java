package com.gmail.gogobebe2.coinstomail.duel;

import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Duel {
    private Player player;
    private Player opponent;
    private Arena arena;
    private UUID duelUUID = UUID.randomUUID();

    protected Duel(Player player, Player opponent, Arena arena) {
        this.player = player;
        this.opponent = opponent;
        this.arena = arena;
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

}
