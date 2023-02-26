package me.fopzl.hoppers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

import me.fopzl.hoppers.FoPzlHoppers;

public class PickupListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onItemPickup(InventoryPickupItemEvent e) {
		if (FoPzlHoppers.getHopperManager().isHopper(e.getInventory().getLocation()))
			e.setCancelled(true);
	}
}
