package me.hhappa0.core.plugin;

import me.hhappa0.core.bootstrap.Core;
import me.hhappa0.core.bootstrap.CoreInitializer;
import me.hhappa0.core.config.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CorePlugin extends JavaPlugin {
    private final String commandsPackageName;
    private final String listenersPackageName;
    private final String noPermissionMessage;
    private Core core;

    public CorePlugin(String commandsPackageName, String listenersPackageName, String noPermissionMessage) {
        this.commandsPackageName = commandsPackageName;
        this.listenersPackageName = listenersPackageName;
        this.noPermissionMessage = noPermissionMessage;
    }

    public abstract void startup();

    public abstract void shutdown();

    @Override
    public void onEnable() {
        this.core = CoreInitializer.initialize(this, this.noPermissionMessage);
        if (this.commandsPackageName != null) this.core.registerCommandsIn(this.commandsPackageName);
        if (this.listenersPackageName != null) this.core.registerListenersIn(this.listenersPackageName);

        startup();
    }

    @Override
    public void onDisable() {
        shutdown();
    }

    public Core getCore() {
        return core;
    }
}
