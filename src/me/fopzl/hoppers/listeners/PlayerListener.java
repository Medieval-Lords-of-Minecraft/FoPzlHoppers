package me.fopzl.hoppers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.fopzl.hoppers.FoPzlHoppers;
import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.gui.HopperGUI;

public class PlayerListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.getPlayer().isSneaking() || e.isBlockInHand())
			return;
		
		if (!FoPzlHoppers.getHopperManager().isHopper(e.getClickedBlock()))
			return;

		Hopper hopper = FoPzlHoppers.getHopperManager().getHopper(e.getClickedBlock().getLocation());
		
		/*
		 * TODO: shared permissions (allow other players to edit your hopper)
		 * don't want to restrict access at all (beyond defaults + other plugins)
		 * until that's implemented though
		 *
		 * if (e.getPlayer().getUniqueId() != hopper.getOwner())
		 * return;
		 */
		
		e.setCancelled(true); // cancel opening the hopper's vanilla inventory
		new HopperGUI(e.getPlayer(), Bukkit.createInventory(e.getPlayer(), 27, "§eLevel " + hopper.getLevel() + " §7Hopper"), hopper);
	}
}
