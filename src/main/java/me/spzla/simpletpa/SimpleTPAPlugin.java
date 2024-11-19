package me.spzla.simpletpa;

import me.spzla.simpletpa.command.TPAcceptCommand;
import me.spzla.simpletpa.command.TPACommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTPAPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("tpa").setExecutor(new TPACommand());
        this.getCommand("tpaccept").setExecutor(new TPAcceptCommand());
    }
}
