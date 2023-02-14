package me.fopzl.hoppers;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class HopperManager {
	private Map<Location, Hopper> hoppers;

	private Set<Hopper> updatedHoppers; // need to save these

	public HopperManager() {
		hoppers = new HashMap<Location, Hopper>();
		updatedHoppers = new HashSet<Hopper>();

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
	
	public Hopper getHopper(Location location) {
		return hoppers.get(location);
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

	public void hopperUpdated(Hopper hopper) {
		updatedHoppers.add(hopper);
	}

	public void sqlSave(Statement insert, Statement delete) throws SQLException {
		for (Hopper hopper : updatedHoppers) {
			hopper.sqlSave(insert, delete);
		}
		updatedHoppers.clear();
	}
}
