package com.gmail.gogobebe2.coinstomail.duel.arena;

import com.gmail.gogobebe2.coinstomail.Main;
import com.gmail.gogobebe2.coinstomail.duel.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {

    private Location pos1;
    private Location pos2;
    private String name;
    private boolean busy = false;

    protected Arena(String name) {
        this.name = name;
        this.pos1 = getPosition(PositionType.POS1, name);
        this.pos2 = getPosition(PositionType.POS2, name);
    }

    private Location getPosition(PositionType positionType, String arenaName) {
        Main main = Main.getInstance();
        String path = "Arenas." + arenaName + "." + positionType.getConfigId() + ".";

        double x = main.getConfig().getDouble(path + "x");
        double y = main.getConfig().getDouble(path + "y");
        double z = main.getConfig().getDouble(path + "z");
        float pitch = (float) main.getConfig().getDouble(path + "pitch");
        float yaw = (float) main.getConfig().getDouble(path + "yaw");
        String worldName = main.getConfig().getString(path + "world");

        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    public Location getPos1() {
        return this.pos1.clone();
    }

    public Location getPos2() {
        return this.pos2.clone();
    }

    public String getName() {
        return this.name;
    }

    protected boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void joinArena(Player player, Player opponent, Arena arena) {
        busy = true;

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

        DuelManager.getInstance().startDuel(player, opponent, arena);
    }
}
