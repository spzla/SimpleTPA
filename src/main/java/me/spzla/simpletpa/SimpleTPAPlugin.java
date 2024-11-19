package me.spzla.simpletpa;

import me.spzla.simpletpa.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTPAPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.registerCommands();
    }

    private void registerCommands() {
        this.getCommand("tpa").setExecutor(new TPACommand());
        this.getCommand("tpaccept").setExecutor(new TPAcceptCommand());
        this.getCommand("tpahere").setExecutor(new TPAHereCommand());
        this.getCommand("tpcancel").setExecutor(new TPCancelCommand());
        this.getCommand("tpreject").setExecutor(new TPRejectCommand());
    }
}
