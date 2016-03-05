package com.gmail.gogobebe2.coinstomail.duel;

import com.gmail.gogobebe2.coinstomail.Main;
import com.gmail.gogobebe2.coinstomail.duel.arena.Arena;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Duel {
    private final long GAME_TIME;
    private Player player;
    private Player opponent;
    private Arena arena;
    private long startingTime;
    private UUID duelUUID = UUID.randomUUID();

    protected Duel(Player player, Player opponent, Arena arena) {
        this.GAME_TIME =  Main.getInstance().getConfig().getInt("Gametime");
        this.player = player;
        this.opponent = opponent;
        this.arena = arena;
        this.startingTime = System.currentTimeMillis();

        // Do duel stuff.
    }

    protected long getTimeLeft() {
        return GAME_TIME - (System.currentTimeMillis() - this.startingTime);
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

    public boolean equals(Object object) {
        return object instanceof Duel && this.duelUUID.equals(((Duel) object).duelUUID);
    }

}
