package me.fopzl.hoppers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.fopzl.hoppers.commands.MainCommand;
import me.fopzl.hoppers.configs.ConfigManager;
import me.fopzl.hoppers.configs.modules.SuctionConfig;
import me.fopzl.hoppers.gui.modules.SuctionGUI;
import me.fopzl.hoppers.listeners.BlockListener;
import me.fopzl.hoppers.listeners.HopperItemListener;
import me.fopzl.hoppers.listeners.PlayerListener;
import me.fopzl.hoppers.modules.SuctionModule;
import me.fopzl.hoppers.modules.TestModule;
import me.neoblade298.neocore.bukkit.NeoCore;

public class FoPzlHoppers extends JavaPlugin {
	private static FoPzlHoppers instance;
	private static HopperManager hopperManager;

	private HopperIO io;

	@Override
	public void onEnable() {
		super.onEnable();
		
		instance = this;
		
		registerModules();
		
		hopperManager = new HopperManager();

		ConfigManager.loadAllConfigs();
		
		HopperIO.loadData();
		
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new HopperItemListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

		NeoCore.registerIOComponent(this, io = new HopperIO(), "FoPzlHoppersIO");
		
		this.getCommand("fopzlhoppers").setExecutor(new MainCommand());
		this.getCommand("fopzlhoppers").setTabCompleter(new MainCommand());
		
		Bukkit.getLogger().info("[FHOP] FoPzlHoppers Enabled");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("[FHOP] FoPzlHoppers Disabled");
		
		io.tryCleanup();

		super.onDisable();
	}
	
	public static void reload() {
		ConfigManager.loadAllConfigs();
		hopperManager.reloadAll();
	}
	
	public static FoPzlHoppers getInstance() {
		return instance;
	}

	public static HopperManager getHopperManager() {
		return hopperManager;
	}
	
	private static void registerModules() {
		ModuleRegistry.registerModule("suction", SuctionModule::new, SuctionConfig::new, SuctionGUI::new);
		ModuleRegistry.registerModule("test", TestModule::new, null, null);
	}
}
