package me.fopzl.hoppers.configs;

import org.bukkit.configuration.file.YamlConfiguration;

public class MainConfig {
	private static int maxHoppers;

	public static void loadConfig(YamlConfiguration config) {
		maxHoppers = config.getInt("maxHoppers", maxHoppers);
	}
	
	public static int getMaxHoppers() {
		return maxHoppers;
	}
}
