package me.fopzl.hoppers.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.fopzl.hoppers.Hopper;

public class TestModule extends HopperModule {
	private int ticks;

	public TestModule(Hopper hopper) {
		super(hopper);

		ticks = 0;
	}

	@Override
	public void tick() {
		if (++ticks % 20 != 0)
			return;
		
		if (!Bukkit.getOfflinePlayer(hopper.getOwner()).isOnline())
			return;
		
		Location loc = hopper.getLocation();
		Bukkit.getPlayer(hopper.getOwner()).sendMessage("Level " + hopper.getLevel() + " hopper at x" + loc.getX() + " y" + loc.getY() + " z" + loc.getZ());
	}

	@Override
	public void remove() {
		Bukkit.getPlayer(hopper.getOwner()).sendMessage("Removing!");
	}
}
