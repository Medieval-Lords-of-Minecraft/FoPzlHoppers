package me.fopzl.hoppers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.fopzl.hoppers.commands.MainCommand;
import me.fopzl.hoppers.configs.ConfigManager;
import me.fopzl.hoppers.configs.modules.SuctionConfig;
import me.fopzl.hoppers.listeners.BlockListener;
import me.fopzl.hoppers.modules.SuctionModule;
import me.fopzl.hoppers.modules.TestModule;

public class FoPzlHoppers extends JavaPlugin {
	private static FoPzlHoppers instance;
	private static HopperManager hopperManager;
	
	@Override
	public void onEnable() {
		super.onEnable();

		instance = this;

		registerModules();

		hopperManager = new HopperManager();
		
		ConfigManager.loadAllConfigs();

		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);

		this.getCommand("fopzlhoppers").setExecutor(new MainCommand());
		this.getCommand("fopzlhoppers").setTabCompleter(new MainCommand());

		Bukkit.getLogger().info("FoPzlHoppers Enabled");
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().info("FoPzlHoppers Disabled");

		super.onDisable();
	}

	public static FoPzlHoppers getInstance() {
		return instance;
	}
	
	public static HopperManager getHopperManager() {
		return hopperManager;
	}

	private static void registerModules() {
		ModuleRegistry.registerModule("suction", SuctionModule::new, SuctionConfig::new);
		ModuleRegistry.registerModule("test", TestModule::new, null);
	}
}
