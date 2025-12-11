package me.hhappa0.core.bootstrap;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The helper class for initializing {@link me.hhappa0.core.bootstrap.Core}
 */
public class CoreInitializer {
    private static Core core;

    /**
     * Provides static access to {@link me.hhappa0.core.bootstrap.Core#initialize(org.bukkit.plugin.java.JavaPlugin, java.lang.String)}.
     * @param plugin The plugin instance the Core library is registered to.
     * @param noPermissionMessage The message displayed, when a player executes a command without the required permission, formatted using {@link net.kyori.adventure.text.minimessage.MiniMessage}.
     * @return The initialized Core library instance.
     */
    public static Core initialize(JavaPlugin plugin, String noPermissionMessage) {
        CoreInitializer.core = new Core();

        return core.initialize(plugin, noPermissionMessage);
    }
}
