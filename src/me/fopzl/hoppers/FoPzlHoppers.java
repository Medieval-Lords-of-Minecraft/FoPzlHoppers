package me.fopzl.hoppers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FoPzlHoppers extends JavaPlugin {
	@Override
	public void onEnable() {
		super.onEnable();
		
		Bukkit.getLogger().info("FoPzlHoppers Enabled");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("FoPzlHoppers Disabled");
		
		super.onDisable();
	}
}
