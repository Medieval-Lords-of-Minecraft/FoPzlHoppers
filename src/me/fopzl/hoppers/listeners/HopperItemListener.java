package me.fopzl.hoppers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

import me.fopzl.hoppers.FoPzlHoppers;

public class HopperItemListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onItemPickup(InventoryPickupItemEvent e) {
		if (FoPzlHoppers.getHopperManager().isHopper(e.getInventory().getLocation()))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onItemMove(InventoryMoveItemEvent e) {
		// TODO: reconcile with potential for piping+suction modules disabled
		if (FoPzlHoppers.getHopperManager().isHopper(e.getSource().getLocation()) || FoPzlHoppers.getHopperManager().isHopper(e.getDestination().getLocation()))
			e.setCancelled(true);
	}
}
