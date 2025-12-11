package me.hhappa0.core.command;

import me.hhappa0.core.bootstrap.Core;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Base class for commands using the Core command framework.
 *
 * <p>Command metadata is read from the {@link CommandInfo} annotation on the implementing class.</p>
 *
 * <p>This class automatically handles permission checks and MiniMessage-based no-permission responses before delegating execution.</p>
 */
public abstract class BaseCommand implements TabExecutor {
    /** Metadata defined by {@link CommandInfo}. */
    private final @NotNull CommandInfo commandInfo;

    /**
     * Constructs the command and validates the presence of {@link CommandInfo}.
     *
     * @throws IllegalStateException if the annotation is missing.
     */
    public BaseCommand() {
        this.commandInfo = this.getClass().getDeclaredAnnotation(CommandInfo.class);

        if (this.commandInfo == null) throw new IllegalStateException("Missing annotation @CoreCommandInfo on " + this.getClass().getName());
    }

    /**
     * Returns the metadata for this command.
     *
     * @return The command metadata.
     */
    public @NotNull CommandInfo getInfo() {
        return commandInfo;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!commandInfo.permission().isEmpty() && !commandSender.hasPermission(commandInfo.permission())) {
            if (Core.noPermissionMessage != null) {
                commandSender.sendMessage(MiniMessage.miniMessage().deserialize(Core.noPermissionMessage));
            }

            return false;
        }

        execute(commandSender, args);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args) {
        if (!commandInfo.permission().isEmpty() && !commandSender.hasPermission(commandInfo.permission())) {
            return List.of();
        }

        return tab(commandSender, args);
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> tab(CommandSender sender, String[] args);
}
