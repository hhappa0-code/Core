package me.hhappa0.core.bootstrap;

import me.hhappa0.core.command.BaseCommand;
import me.hhappa0.core.menu.MenuListener;
import me.hhappa0.core.util.SchedulerUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * The main class for the Core library.
 */
public class Core {
    public static JavaPlugin plugin;
    public static String noPermissionMessage;

    /**
     * Initializes the Core library for a given plugin.
     * @param plugin The plugin instance the Core library is registered to.
     * @param noPermissionMessage The message displayed, when a player executes a command without the required permission, formatted using {@link net.kyori.adventure.text.minimessage.MiniMessage}.
     * @return The initialized Core library instance.
     */
    public Core initialize(JavaPlugin plugin, String noPermissionMessage) {
        Core.plugin = plugin;
        Core.noPermissionMessage = noPermissionMessage;

        plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);

        return this;
    }

    /**
     * Registers all {@link BaseCommand}s in the given package.
     * @param packageName The package where the {@link BaseCommand}s are located.
     */
    public void registerCommandsIn(String packageName) {
        for (Class<? extends BaseCommand> clazz : new Reflections(packageName).getSubTypesOf(BaseCommand.class)) {
            try {
                BaseCommand baseCommand = clazz.getDeclaredConstructor().newInstance();

                Objects.requireNonNull(Core.plugin.getCommand(baseCommand.getInfo().name())).setExecutor(baseCommand);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                plugin.getLogger().severe("Could not register listeners in package '" + packageName + "'!");
            }
        }
    }

    /**
     * Registers all {@link org.bukkit.event.Listener}s in the given package.
     * @param packageName The package where the {@link org.bukkit.event.Listener}s are located.
     */
    public void registerListenersIn(String packageName) {
        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();

                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                plugin.getLogger().severe("Could not register listeners in package '" + packageName + "'!");
            }
        }
    }

    /**
     * Returns the initialized {@link SchedulerUtil} instance.
     * @return The initialized {@link SchedulerUtil} instance.
     */
    public SchedulerUtil getScheduler() {
        return new SchedulerUtil(plugin);
    }
}
