package me.fopzl.hoppers.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.fopzl.hoppers.FoPzlHoppers;
import me.fopzl.hoppers.Hopper;

public class BlockListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() != Material.HOPPER)
			return;

		// TODO: handle max hoppers per chunk here
		
		NBTItem nbti = new NBTItem(e.getItemInHand());
		if (!nbti.getBoolean("fopzlhopper"))
			return;
		
		UUID uuid = e.getPlayer().getUniqueId();
		int level = nbti.getInteger("level");

		FoPzlHoppers.getHopperManager().addHopper(new Hopper(e.getBlock(), uuid, level));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() != Material.HOPPER)
			return;
		
		Location loc = e.getBlock().getLocation();
		if (!FoPzlHoppers.getHopperManager().isHopper(loc))
			return;

		Hopper hopper = FoPzlHoppers.getHopperManager().removeHopper(loc);
		ItemStack itemToDrop = Hopper.getItem(hopper.getLevel());
		
		e.getBlock().getWorld().dropItemNaturally(loc, itemToDrop);
		e.getBlock().setType(Material.AIR);

		e.setCancelled(true);
	}
}
