package me.fopzl.hoppers.listeners;

import java.util.UUID;

import org.bukkit.Chunk;
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
import me.fopzl.hoppers.configs.MainConfig;
import me.neoblade298.neocore.bukkit.util.Util;

public class BlockListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() != Material.HOPPER)
			return;

		int maxHoppers = MainConfig.getMaxHoppers();
		if (maxHoppers != -1) {
			if (countHoppersInChunk(e.getBlock().getChunk()) >= maxHoppers) {
				Util.msg(e.getPlayer(), "");
				return;
			}
		}
		
		NBTItem nbti = new NBTItem(e.getItemInHand());
		if (!nbti.getBoolean("fopzlhopper"))
			return;
		
		UUID uuid = e.getPlayer().getUniqueId();
		int level = nbti.getInteger("level");

		Hopper hopper = new Hopper(e.getBlock(), uuid, level);
		FoPzlHoppers.getHopperManager().addHopper(hopper);
		hopper.enable();
		hopper.updated();
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
		
		for (ItemStack item : hopper.getInventory().getContents()) {
			if (item == null || item.getType() == Material.AIR)
				continue;
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
		}
		
		e.getBlock().getWorld().dropItemNaturally(loc, itemToDrop);
		e.getBlock().setType(Material.AIR);
		
		e.setCancelled(true);
	}
	
	// this is stupid but apparently efficient enough!
	private int countHoppersInChunk(Chunk c) {
		int cnt = 0;

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = c.getWorld().getMinHeight(); y < c.getWorld().getMaxHeight(); y++) {
					if (c.getBlock(x, y, z).getType() == Material.HOPPER)
						cnt++;
				}
			}
		}

		return cnt;
	}
}
