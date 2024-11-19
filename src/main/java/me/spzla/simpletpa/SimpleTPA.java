package me.spzla.simpletpa;

import me.spzla.simpletpa.command.TpAcceptCommand;
import me.spzla.simpletpa.command.TpaCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class SimpleTPA {
    public static final Logger LOGGER = LoggerFactory.getLogger("SimpleTPA");
    public static final SimpleTPA INSTANCE = new SimpleTPA();

    public ArrayList<TpaRequest> requests = new ArrayList<>();

    public TpaRequest createRequest(Player from, Player to) {
        TpaRequest request = new TpaRequest(from, to);
        requests.add(request);
        CompletableFuture.delayedExecutor(30, TimeUnit.SECONDS).execute(() -> {
            LOGGER.info("Removed expired request from {} to {}", from.getName(), to.getName());
            requests.remove(request);
        });

        return request;
    }

    public TpaRequest findLatest(Player to) {
        List<TpaRequest> filtered = requests.stream().filter(req -> req.getTo().equals(to)).toList();

        List<TpaRequest> filteredSorted = filtered.stream().sorted((a, b) -> {
            if (a.getSendTime() == b.getSendTime()) return 0;
            return a.getSendTime() > b.getSendTime() ? -1 : 1;
        }).toList();

        if (!filteredSorted.isEmpty()) {
            return filteredSorted.getFirst();
        } else {
            return null;
        }
    }

    public TpaRequest findFrom(Player to, Player from) {
        if (to.equals(from)) {
            return null;
        }

        List<TpaRequest> filtered = requests.stream().filter(req ->
                req.getFrom().equals(from) && req.getTo().equals(to)).toList();

        if (filtered.size() == 1) {
            return filtered.getFirst();
        } else {
            List<TpaRequest> filteredSorted = filtered.stream().sorted((a, b) -> {
                if (a.getSendTime() == b.getSendTime()) return 0;
                return a.getSendTime() > b.getSendTime() ? -1 : 1;
            }).toList();

            return filteredSorted.getFirst();
        }
    }

    public void acceptRequest(TpaRequest request) {
        request.accept();
        requests.remove(request);
    }
}
