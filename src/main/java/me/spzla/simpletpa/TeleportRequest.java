package me.spzla.simpletpa;

import org.bukkit.entity.Player;

public class TeleportRequest {
    private final Player sender;
    private final Player receiver;
    private final long sendTime;
    private final RequestMode mode;
    private boolean isActive;

    public TeleportRequest(Player sender, Player to) {
        this(sender, to, RequestMode.TELEPORT_TO_PLAYER);
    }

    public TeleportRequest(Player sender, Player to, RequestMode mode) {
        this.sender = sender;
        this.receiver = to;
        this.mode = mode;
        this.sendTime = System.currentTimeMillis();
        this.isActive = true;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public boolean isActive() { return isActive; }

    public long getSendTime() {
        return sendTime;
    }

    public void send() {
        if (mode == RequestMode.TELEPORT_TO_PLAYER) {
            sender.sendMessage(String.format("Wysłano prośbę o teleportację do %s", receiver.getName()));
            receiver.sendMessage(String.format(
                    "%s chce się przeteleportować do ciebie.\nWpisz /tpaccept żeby zaakceptować (wygaśnie za 30s)",
                    sender.getName()));
        } else if (mode == RequestMode.TELEPORT_PLAYER_HERE) {
            sender.sendMessage(String.format("Wysłano prośbę o teleportację do %s", receiver.getName()));
            receiver.sendMessage(String.format(
                    "%s chce zebyś się do niego przeteleportował.\nWpisz /tpaccept żeby zaakceptować (wygaśnie za 30s)",
                    sender.getName()));
        }
    }

    public void accept() {
        sender.sendMessage(String.format("%s zaakceptował twoja prośbę", receiver.getName()));
        receiver.sendMessage(String.format("Zaakceptowano prośbę od %s", sender.getName()));
        if (mode == RequestMode.TELEPORT_TO_PLAYER) {
            sender.teleport(receiver);
        } else if (mode == RequestMode.TELEPORT_PLAYER_HERE) {
            receiver.teleport(sender);
        }

        isActive = false;
    }

    public void cancel() {
        sender.sendMessage(String.format("Anulowales prosbe o teleportacje do %s", receiver.getName()));
        receiver.sendMessage(String.format("%s anulowal prosbe o teleportacje", sender.getName()));
        isActive = false;
    }

    public void reject() {
        sender.sendMessage(String.format("%s odrzucil twoja prosbe o teleportacje", receiver.getName()));
        receiver.sendMessage(String.format("Odrzuciles prosbe o teleportacje od %s", sender.getName()));
        isActive = false;
    }

    public void expire() {
        sender.sendMessage(String.format("Prosba o teleportacje do %s wygasla", receiver.getName()));
        receiver.sendMessage(String.format("Prosba o teleportacje od %s wygasla", sender.getName()));
        isActive = false;
    }

    public enum RequestMode {
        TELEPORT_TO_PLAYER,
        TELEPORT_PLAYER_HERE
    }
}