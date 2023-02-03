package me.fopzl.hoppers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class HopperManager {
	private Map<Location, Hopper> hoppers;
	
	public HopperManager() {
		hoppers = new HashMap<Location, Hopper>();
	}

	public void addHopper(Hopper hopper) {
		hoppers.put(hopper.getLocation(), hopper);
	}

	public Hopper removeHopper(Location location) {
		return hoppers.remove(location);
	}
	
	public boolean isHopper(Location location) {
		return hoppers.containsKey(location);
	}

	public void tickAll() {
		for (Hopper hopper : hoppers.values()) {
			hopper.tick();
		}
	}
}
