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
	protected void onEnable() {
		Bukkit.getPlayer(hopper.getOwner()).sendMessage("Enabling!");
	}

	@Override
	protected void onDisable() {
		Bukkit.getPlayer(hopper.getOwner()).sendMessage("Disabling!");
	}
	
	@Override
	protected void onReload() {
	}

	@Override
	protected void onTick() {
		if (++ticks % 20 != 0)
			return;
		
		if (!Bukkit.getOfflinePlayer(hopper.getOwner()).isOnline())
			return;
		
		Location loc = hopper.getLocation();
		Bukkit.getPlayer(hopper.getOwner()).sendMessage("Level " + hopper.getLevel() + " hopper at x" + loc.getX() + " y" + loc.getY() + " z" + loc.getZ());
	}
	
	@Override
	public String getName() {
		return "test";
	}
	
	@Override
	public String getSaveData() {
		return null;
	}

	@Override
	public void loadData(String data) {
	}
}
