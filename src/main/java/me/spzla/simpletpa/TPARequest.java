package me.spzla.simpletpa;

import org.bukkit.entity.Player;

public class TPARequest {
    private Player from;
    private Player to;
    private long sendTime;

    public TPARequest(Player from, Player to) {
        this.from = from;
        this.to = to;
        this.sendTime = System.currentTimeMillis();
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void send() {
        from.sendMessage(String.format("Wyslano tpa request do %s", to.getName()));
        to.sendMessage(String.format("%s chce tp do cb. /tpaccept zeby zaakceptowac (wygasnie za 30s)", from.getName()));
    }

    public void accept() {
        from.sendMessage(String.format("%s zaakceptowal twoja prosbe", to.getName()));
        to.sendMessage(String.format("Zaakceptowano prosbe od %s", from.getName()));
        from.teleport(to);
    }
}