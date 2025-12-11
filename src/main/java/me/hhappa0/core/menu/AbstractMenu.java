package me.hhappa0.core.menu;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Base class for all custom core menus. Developers extend this to define specific GUI logic.
 */
public abstract class AbstractMenu {
    protected final Player player;
    protected Inventory inventory;

    public AbstractMenu(Player player) {
        this.player = player;
    }

    /**
     * Defines the title of the menu.
     * @return The title string, which will get deserialized by MiniMessage.
     */
    public abstract String getMenuTitle();

    /**
     * Defines the rows of the menu.
     * @return The rows of the inventory.
     */
    public abstract int getRows();

    /**
     * Sets up all the items in the inventory.
     * <p>Developers should use the {@link ItemBuilder} here to place items in specific slots.</p>
     */
    public abstract void setMenuItems();

    /**
     * Handles logic when the menu is closed.
     * This is called automatically by the MenuListener when the player closes the inventory.
     */
    public abstract void handleMenuClose();

    /**
     * Creates and opens the inventory for the player.
     * This uses the custom MenuHolder to attach the menu logic.
     */
    public void open() {
        if (getRows() > 6) throw new IllegalArgumentException("The inventory size can not be larger than six rows.");

        this.inventory = Bukkit.getServer().createInventory(new MenuHolder(this, null), getRows() * 9, MiniMessage.miniMessage().deserialize(getMenuTitle()));

        this.setMenuItems();

        this.player.openInventory(this.inventory);
    }

    /**
     * Helper function to get the inventory.
     * @return The menu inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets an item in a specific slot. This version is for items that are 'just there' (display only).
     * @param item The ItemStack to set.
     * @param slot The inventory slot (0-indexed).
     */
    public void setItem(ItemStack item, int slot) {
        this.setItem(item, slot, null);
    }

    /**
     * Sets an item in a specific slot, registering an action to execute on click.
     * @param item The ItemStack to set.
     * @param slot The inventory slot (0-indexed).
     * @param action The Consumer function to run when the item is clicked. Can be null for display items.
     */
    public void setItem(ItemStack item, int slot, Consumer<InventoryClickEvent> action) {
        if (inventory != null) {
            inventory.setItem(slot, item);
        }

        if (action != null && inventory != null) {
            InventoryHolder holder = inventory.getHolder();
            if (holder instanceof MenuHolder menuHolder) {
                menuHolder.setAction(slot, action);
            }
        }
    }
}
