package me.spzla.simpletpa;

import me.spzla.simpletpa.command.TpAcceptCommand;
import me.spzla.simpletpa.command.TpaCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTPAPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("tpa").setExecutor(new TpaCommand());
        this.getCommand("tpaccept").setExecutor(new TpAcceptCommand());
    }
}
