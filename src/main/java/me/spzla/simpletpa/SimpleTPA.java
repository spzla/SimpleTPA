package me.spzla.simpletpa;

import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class SimpleTPA {
    public static final Logger LOGGER = LoggerFactory.getLogger("SimpleTPA");
    public static final SimpleTPA INSTANCE = new SimpleTPA();

    public ArrayList<TeleportRequest> requests = new ArrayList<>();

    public TeleportRequest createRequest(Player from, Player to) {
        return this.createRequest(from, to, TeleportRequest.RequestMode.TELEPORT_TO_PLAYER);
    }

    public TeleportRequest createRequest(Player from, Player to, TeleportRequest.RequestMode mode) {
        TeleportRequest request = new TeleportRequest(from, to, mode);
        requests.add(request);
        CompletableFuture.delayedExecutor(30, TimeUnit.SECONDS).execute(() -> {
            if (request.isActive()) {
                request.expire();
            }
            requests.remove(request);
            LOGGER.info("Removed expired request from {} to {}", from.getName(), to.getName());
        });

        return request;
    }

    private final Comparator<TeleportRequest> sortByTimeSentDesc = (TeleportRequest a, TeleportRequest b) -> {
        if (a.getSendTime() == b.getSendTime()) return 0;
        return a.getSendTime() > b.getSendTime() ? -1 : 1;
    };

    public TeleportRequest findForPlayer(Player player) {
        List<TeleportRequest> filtered = requests.stream().filter(
                req -> req.getReceiver().equals(player) || req.getSender().equals(player)).toList();

        List<TeleportRequest> filteredSorted = filtered.stream().sorted(sortByTimeSentDesc).toList();

        if (!filteredSorted.isEmpty()) {
            return filteredSorted.getFirst();
        } else {
            return null;
        }
    }

    public TeleportRequest findFrom(Player player) {
        List<TeleportRequest> filtered = requests.stream().filter(
                req -> req.getSender().equals(player)).toList();

        List<TeleportRequest> filteredSorted = filtered.stream().sorted(sortByTimeSentDesc).toList();

        if (!filteredSorted.isEmpty()) {
            return filteredSorted.getFirst();
        } else {
            return null;
        }
    }

    public TeleportRequest findTo(Player player) {
        List<TeleportRequest> filtered = requests.stream().filter(
                req -> req.getReceiver().equals(player)).toList();

        List<TeleportRequest> filteredSorted = filtered.stream().sorted(sortByTimeSentDesc).toList();

        if (!filteredSorted.isEmpty()) {
            return filteredSorted.getFirst();
        } else {
            return null;
        }
    }

    public TeleportRequest findFromTo(Player to, Player from) {
        if (to.equals(from)) {
            return null;
        }

        List<TeleportRequest> filtered = requests.stream().filter(req ->
                req.getSender().equals(from) && req.getReceiver().equals(to)).toList();

        if (filtered.size() == 1) {
            return filtered.getFirst();
        } else {
            List<TeleportRequest> filteredSorted = filtered.stream().sorted(sortByTimeSentDesc).toList();

            return filteredSorted.getFirst();
        }
    }

    public void acceptRequest(TeleportRequest request) {
        if (request != null && request.isActive()) {
            request.accept();
        }
        requests.remove(request);
    }

    public void cancelRequest(TeleportRequest request) {
        if (request != null && request.isActive()) {
            request.cancel();
        }
        requests.remove(request);
    }

    public void rejectRequest(TeleportRequest request) {
        if (request != null && request.isActive()) {
            request.reject();
        }
        requests.remove(request);
    }
}
