package com.bgsoftware.islandchestvalues;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class IslandChestValues extends PluginModule implements Listener {

    private JavaPlugin javaPlugin;

    public IslandChestValues() {
        super("IslandChestValues", "Ome_R");
    }

    @Override
    public void onEnable(SuperiorSkyblock plugin) {
        this.javaPlugin = (JavaPlugin) plugin;
    }

    @Override
    public void onReload(SuperiorSkyblock plugin) {

    }

    @Override
    public void onDisable(SuperiorSkyblock plugin) {

    }

    @Override
    public Listener[] getModuleListeners(SuperiorSkyblock superiorSkyblock) {
        return new Listener[]{new IslandChestListener(javaPlugin)};
    }

    @Override
    public SuperiorCommand[] getSuperiorCommands(SuperiorSkyblock superiorSkyblock) {
        return null;
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands(SuperiorSkyblock superiorSkyblock) {
        return null;
    }

}
