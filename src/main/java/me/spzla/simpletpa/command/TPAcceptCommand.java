package me.spzla.simpletpa.command;

import me.spzla.simpletpa.SimpleTPA;
import me.spzla.simpletpa.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command,
                             @NotNull String s,
                             @NotNull String[] strings) {
        if ( !(commandSender instanceof Player sender) ) return true;

        TeleportRequest request = null;

        if ( strings.length == 0 || strings[0].isEmpty() ) {
            request = SimpleTPA.INSTANCE.findForPlayer(sender);

            if ( request == null ) {
                sender.sendMessage("Nie masz oczekujacych prosb o teleportacje");
                return true;
            }
        } else {
            Player from = Bukkit.getPlayer(strings[0]);

            if (from == null) {
                sender.sendMessage("Nie znaleziono gracza o nicku %s", strings[0]);
                return true;
            }

            if (from.equals(sender)) {
                sender.sendMessage("nie mozesz tpa sam do siebie");
                return true;
            }

            request = SimpleTPA.INSTANCE.findFromTo(sender, from);

            if ( request == null ) {
                sender.sendMessage("Nie masz oczekujacych prosb o teleportacje od gracza %s", from.getName());
                return true;
            }
        }

        SimpleTPA.INSTANCE.acceptRequest(request);

        return true;
    }
}
