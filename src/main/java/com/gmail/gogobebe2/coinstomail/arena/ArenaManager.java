package com.gmail.gogobebe2.coinstomail.arena;

import com.gmail.gogobebe2.coinstomail.Main;

import java.util.HashSet;
import java.util.Set;

public class ArenaManager {
    static ArenaManager instance;

    private Set<Arena> arenas = new HashSet<>();

    static {
        instance = new ArenaManager();
    }

    public static ArenaManager getInstance() {
        return instance;
    }

    private ArenaManager() {
        for (String arenaName : Main.getInstance().getConfig().getConfigurationSection("Arenas").getKeys(false)) {
            arenas.add(new Arena(arenaName));
        }
    }

    public Arena getNextFreeArena() {
        return null;
    }
}
