package me.spzla.simpletpa.command;

import me.spzla.simpletpa.SimpleTPA;
import me.spzla.simpletpa.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPCancelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command,
                             @NotNull String s,
                             @NotNull String[] strings) {
        if ( !(commandSender instanceof Player sender) ) return true;

        TeleportRequest request = null;

        if ( strings.length == 0 || strings[0].isEmpty() ) {
            request = SimpleTPA.INSTANCE.findFrom(sender);

            if ( request == null ) {
                sender.sendMessage("Nie masz wyslanych oczekujacych prosb o teleportacje");
                return true;
            }
        } else {
            Player from = Bukkit.getPlayer(strings[0]);

            if (from == null) {
                sender.sendMessage(String.format("Nie znaleziono gracza o nicku %s", strings[0]));
                return true;
            }

            if (from.equals(sender)) {
                sender.sendMessage("nie mozesz anulowac tpa sam do siebie");
                return true;
            }

            request = SimpleTPA.INSTANCE.findFromTo(sender, from);

            if ( request == null ) {
                sender.sendMessage(
                        String.format("Nie masz oczekujacych prosb o teleportacje od gracza %s", from.getName()));
                return true;
            }
        }

        SimpleTPA.INSTANCE.cancelRequest(request);

        return true;
    }
}