package me.fopzl.hoppers.configs.modules;

import org.bukkit.configuration.ConfigurationSection;

public abstract class ModuleConfig {
	public abstract void loadConfig(ConfigurationSection config);
	
	@Override
	public abstract ModuleConfig clone();
}
