package me.fopzl.hoppers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import me.fopzl.hoppers.modules.SuctionBModule;

public class ItemListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private static void onItemSpawn(ItemSpawnEvent e) {
		SuctionBModule.onItemSpawn(e);
	}
}
