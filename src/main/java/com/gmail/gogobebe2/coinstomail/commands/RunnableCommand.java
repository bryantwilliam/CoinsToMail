package com.gmail.gogobebe2.coinstomail.commands;

import org.bukkit.command.CommandSender;

public abstract class RunnableCommand implements Runnable {
    private CommandSender sender;
    private String args[];

    public RunnableCommand(CommandSender sender, String args[]) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public abstract void run();

    protected CommandSender getSender() {
        return sender;
    }

    protected String[] getArgs() {
        return args;
    }
}
