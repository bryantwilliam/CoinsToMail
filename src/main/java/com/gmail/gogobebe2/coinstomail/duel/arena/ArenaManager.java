package com.gmail.gogobebe2.coinstomail.duel.arena;

import com.gmail.gogobebe2.coinstomail.Main;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;

public class ArenaManager {
    private static ArenaManager instance;

    private Set<Arena> arenas = new HashSet<>();

    static {
        instance = new ArenaManager();
    }

    public static ArenaManager getInstance() {
        return instance;
    }

    private ArenaManager() {
        Main main = Main.getInstance();
        if (main.getConfig().isSet("Arenas")) {
            for (String arenaName : main.getConfig().getConfigurationSection("Arenas").getKeys(false)) {
                arenas.add(new Arena(arenaName));
            }
        }
        else for (int i = 0; i < 10; i++) Bukkit.getLogger().severe("Warning! No arenas are set!");
    }

    public Arena getNextFreeArena() {
        if (!arenas.isEmpty()) for (Arena arena : arenas) if (!arena.isBusy()) return arena;
        return null;
    }
}
