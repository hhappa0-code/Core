package me.hhappa0.core.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MenuHolder implements InventoryHolder {
    private final AbstractMenu menu;
    private final Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> actions;

    public MenuHolder(AbstractMenu menu, Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
        this.actions = new HashMap<>();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public AbstractMenu getMenu() {
        return menu;
    }

    public void setAction(int slot, Consumer<InventoryClickEvent> action) {
        this.actions.put(slot, action);
    }

    public Consumer<InventoryClickEvent> getAction(int slot) {
        return this.actions.get(slot);
    }
}
