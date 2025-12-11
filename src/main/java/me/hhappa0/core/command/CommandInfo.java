package me.hhappa0.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a Core command and defines its metadata.
 *
 * <p>This annotation is retained at runtime and can be read using reflection.</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    /**
     * Defines the primary name of the command.
     *
     * @return The command name.
     */
    String name();

    /**
     * Defines the permission node required to execute the command.
     *
     * <p>An empty string means no permission is required.</p>
     *
     * @return The permission node, or an empty string if none is required.
     */
    String permission() default "";
}
