package me.hhappa0.core.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.function.Consumer;

/**
 * Global listener to route inventory clicks to the appropriate AbstractMenu instance.
 */
public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MenuHolder holder) {
            event.setCancelled(true);

            AbstractMenu menu = holder.getMenu();

            if (event.getClickedInventory() == null || !event.getClickedInventory().equals(menu.getInventory())) {
                return;
            }

            int slot = event.getRawSlot();
            Consumer<InventoryClickEvent> action = holder.getAction(slot);

            if (action != null) action.accept(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof MenuHolder holder) {
            holder.getMenu().handleMenuClose();
        }
    }
}
