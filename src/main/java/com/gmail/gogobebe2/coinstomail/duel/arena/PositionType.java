package com.gmail.gogobebe2.coinstomail.duel.arena;

public enum PositionType {
    POS1("pos1"),
    POS2("pos2");

    private String configId;

    PositionType(String configId) {
        this.configId = configId;
    }

    public String getConfigId() {
        return this.configId;
    }

    protected static PositionType getByConfigID(String configId) {
        for (PositionType positionType : PositionType.values()) if (positionType.configId.equals(configId)) return positionType;
        return null;
    }
}
