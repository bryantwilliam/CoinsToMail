package com.gmail.gogobebe2.coinstomail.arena;

import com.gmail.gogobebe2.coinstomail.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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

    protected void setBusy(boolean busy) {
        this.busy = busy;
    }
}
