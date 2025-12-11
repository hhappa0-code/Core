package me.hhappa0.core.menu;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A builder for creating and modifying {@link org.bukkit.inventory.ItemStack} instances.
 *
 * <p>This builder clones the provided {@link org.bukkit.inventory.ItemStack} to prevent
 * accidental modification of the original object or creates a new {@link org.bukkit.inventory.ItemStack} with a given {@link org.bukkit.Material}. </p>
 *
 * <p>Display names and lore are deserialized using {@link net.kyori.adventure.text.minimessage.MiniMessage}.</p>
 */
public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    /**
     * Creates a new builder for the given material.
     *
     * @param material The material to use for the item.
     */
    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    /**
     * Creates a new builder from an existing item by cloning it.
     *
     * @param clone The {@link org.bukkit.inventory.ItemStack} to clone.
     */
    public ItemBuilder(ItemStack clone) {
        item = clone.clone();
        meta = item.getItemMeta();
    }

    /**
     * Sets the stack size of the item.
     *
     * @param amount The new stack size.
     */
    public ItemBuilder amount(int amount) {
        if (item != null) item.setAmount(amount);

        return this;
    }

    /**
     * Sets the display name using {@link net.kyori.adventure.text.minimessage.MiniMessage} formatting.
     *
     * @param name The {@link net.kyori.adventure.text.minimessage.MiniMessage}-formatted name.
     */
    public ItemBuilder name(String name) {
        if (meta != null) meta.displayName(MiniMessage.miniMessage().deserialize(name));

        return this;
    }

    /**
     * Sets the lore using {@link net.kyori.adventure.text.minimessage.MiniMessage} formatting.
     *
     * @param lore The {@link net.kyori.adventure.text.minimessage.MiniMessage}-formatted lore lines list.
     */
    public ItemBuilder lore(List<String> lore) {
        if (meta != null) meta.lore(lore.stream().map(MiniMessage.miniMessage()::deserialize).collect(Collectors.toList()));

        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment to add.
     * @param level The level of the .
     */
    public ItemBuilder enchant(Enchantment enchantment, int level) {
        if (meta != null) meta.addEnchant(enchantment, level, true);

        return this;
    }

    /**
     * Sets the damage value of the item if its meta supports durability.
     *
     * @param damage The damage value.
     */
    public ItemBuilder damage(int damage) {
        if (meta instanceof Damageable damageable) damageable.setDamage(damage);

        return this;
    }

    /**
     * Marks the item as unbreakable.
     */
    public ItemBuilder unbreakable() {
        if (meta != null) meta.setUnbreakable(true);

        return this;
    }

    /**
     * Adds one or more {@link org.bukkit.inventory.ItemFlag} values to the item.
     *
     * @param flags The item flags to add.
     */
    public ItemBuilder flags(ItemFlag... flags) {
        if (meta != null) meta.addItemFlags(flags);

        return this;
    }

    /**
     * Allows direct modification of the {@link org.bukkit.inventory.meta.ItemMeta}.
     *
     * @param consumer The consumer that mutates the item meta.
     */
    public ItemBuilder editMeta(Consumer<ItemMeta> consumer) {
        if (meta != null) consumer.accept(meta);

        return this;
    }

    /**
     * Builds the final {@link org.bukkit.inventory.ItemStack}.
     *
     * @return The final {@link org.bukkit.inventory.ItemStack}.
     */
    public ItemStack build() {
        if (meta != null) item.setItemMeta(meta);

        return item;
    }
}
