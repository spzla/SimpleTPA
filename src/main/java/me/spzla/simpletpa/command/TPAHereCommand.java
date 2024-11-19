package me.spzla.simpletpa.command;

import me.spzla.simpletpa.SimpleTPA;
import me.spzla.simpletpa.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPAHereCommand  implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if ( !(commandSender instanceof Player sender) ) return true;

        if ( strings.length == 0 || strings[0].isEmpty() ) {
            sender.sendMessage("Podaj nick ziomka");
            return true;
        }

        Player to = Bukkit.getPlayer(strings[0]);

        if (to == null) {
            sender.sendMessage("taki ziomek nie istnieje");
            return true;
        }

        if (to.equals(sender)) {
            sender.sendMessage("nie mozesz tpahere sam do siebie");
            return true;
        }

        TeleportRequest request = SimpleTPA.INSTANCE.createRequest(
                sender, to, TeleportRequest.RequestMode.TELEPORT_PLAYER_HERE);

        request.send();

        return true;
    }
}