package com.gmail.gogobebe2.coinstomail.commands;

import org.bukkit.command.CommandSender;

public abstract class Command {
    private CommandSender sender;
    private String args[];

    public Command(CommandSender sender, String args[]) {
        this.sender = sender;
        this.args = args;
    }

    public abstract void run();

    protected CommandSender getSender() {
        return sender;
    }

    protected String[] getArgs() {
        return args;
    }
}
