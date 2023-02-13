package me.fopzl.hoppers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class HopperManager {
	private Map<Location, Hopper> hoppers;
	
	public HopperManager() {
		hoppers = new HashMap<Location, Hopper>();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				tickAll();
			}
		}.runTaskTimer(FoPzlHoppers.getInstance(), 0, 1);
	}

	public void addHopper(Hopper hopper) {
		hoppers.put(hopper.getLocation(), hopper);
	}

	public Hopper removeHopper(Location location) {
		Hopper hopper = hoppers.remove(location);
		if (hopper != null)
			hopper.remove();
		return hopper;
	}
	
	public boolean isHopper(Location location) {
		return hoppers.containsKey(location);
	}

	public void reloadAll() {
		for (Hopper hopper : hoppers.values()) {
			hopper.reload();
		}
	}

	public void tickAll() {
		for (Hopper hopper : hoppers.values()) {
			hopper.tick();
		}
	}
}
