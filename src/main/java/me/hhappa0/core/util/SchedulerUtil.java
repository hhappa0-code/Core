package me.hhappa0.core.util;

import org.bukkit.plugin.Plugin;

/**
 * A utility class for easily scheduling synchronous and asynchronous tasks.
 */
public class SchedulerUtil {
    private final Plugin plugin;

    /**
     * Creates an instance of the Scheduler Utility.
     * @param plugin The instance of your main plugin class.
     */
    public SchedulerUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs a task synchronously (on the main server thread) immediately.
     *
     * @param task The Runnable task to execute.
     */
    public void runTaskSync(Runnable task) {
        if (this.plugin.getServer().isPrimaryThread()) task.run();
        else this.plugin.getServer().getScheduler().runTask(plugin, task);
    }

    /**
     * Runs a task asynchronously (off the main server thread) immediately.
     *
     * @param task The Runnable task to execute.
     */
    public void runTaskAsync(Runnable task) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task);
    }

    /**
     * Runs a task synchronously after a specified delay.
     *
     * @param task The Runnable task to execute.
     * @param delayTicks The delay in server ticks (20 ticks = 1 second).
     */
    public void runTaskLaterSync(Runnable task, long delayTicks) {
        this.plugin.getServer().getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    /**
     * Runs a task asynchronously after a specified delay.
     *
     * @param task The Runnable task to execute.
     * @param delayTicks The delay in server ticks (20 ticks = 1 second).
     */
    public void runTaskLaterAsync(Runnable task, long delayTicks) {
        this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
    }

    /**
     * Schedules a task to run synchronously (repeating) at regular intervals.
     *
     * @param task The Runnable task to execute repeatedly.
     * @param delayTicks The initial delay before the first execution in server ticks (20 ticks = 1 second).
     * @param periodTicks The interval between executions in server ticks (20 ticks = 1 second).
     */
    public void runTaskTimerSync(Runnable task, long delayTicks, long periodTicks) {
        this.plugin.getServer().getScheduler().runTaskTimer(plugin, task, delayTicks, periodTicks);
    }

    /**
     * Schedules a task to run asynchronously (repeating) at regular intervals.
     *
     * @param task The Runnable task to execute repeatedly.
     * @param delayTicks The initial delay before the first execution in server ticks (20 ticks = 1 second).
     * @param periodTicks The interval between executions in server ticks (20 ticks = 1 second).
     */
    public void runTaskTimerAsync(Runnable task, long delayTicks, long periodTicks) {
        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, task, delayTicks, periodTicks);
    }
}
