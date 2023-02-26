package me.fopzl.hoppers.configs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import me.fopzl.hoppers.FoPzlHoppers;
import me.fopzl.hoppers.ModuleRegistry;
import me.fopzl.hoppers.configs.modules.ModuleConfig;

public class ConfigManager {
	private static Map<String, ModuleConfig> defaultConfigs; // key is modulename
	private static Map<Integer, Map<String, ModuleConfig>> levelConfigs; // key is level, value's key is modulename
	
	public static void loadAllConfigs() {
		defaultConfigs = new HashMap<String, ModuleConfig>();
		levelConfigs = new HashMap<Integer, Map<String, ModuleConfig>>();
		
		loadMainConfig();

		loadModuleConfig("suction", "suction.yml");

		loadHopperConfig();
	}

	private static void loadMainConfig() {
		File file = new File(FoPzlHoppers.getInstance().getDataFolder(), "configs/config.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			FoPzlHoppers.getInstance().saveResource("configs/config.yml", false);
		}
		
		MainConfig.loadConfig(YamlConfiguration.loadConfiguration(file));
	}

	private static void loadHopperConfig() {
		File file = new File(FoPzlHoppers.getInstance().getDataFolder(), "configs/hoppers.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			FoPzlHoppers.getInstance().saveResource("configs/hoppers.yml", false);
		}
		
		HopperConfig.loadConfig(YamlConfiguration.loadConfiguration(file));
	}

	private static void loadModuleConfig(String moduleName, String fileName) {
		if (!ModuleRegistry.hasConfig(moduleName)) {
			Bukkit.getLogger().info("[FHOP] Tried to load config for module \"" + moduleName + "\" but module has no config registered.");
			return;
		}
		
		String fullFileName = "configs/modules/" + fileName;
		File file = new File(FoPzlHoppers.getInstance().getDataFolder(), fullFileName);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			FoPzlHoppers.getInstance().saveResource(fullFileName, false);
		}
		
		ConfigurationSection configSection = YamlConfiguration.loadConfiguration(file);
		ModuleConfig config = ModuleRegistry.getConfigConstructor(moduleName).get();
		config.loadConfig(configSection);
		
		defaultConfigs.put(moduleName, config);
	}

	public static void addLvlOverride(String moduleName, int hopperLvl, ConfigurationSection configSection) {
		if (levelConfigs.containsKey(hopperLvl) && levelConfigs.get(hopperLvl).containsKey(moduleName)) {
			levelConfigs.get(hopperLvl).get(moduleName).loadConfig(configSection);
		} else if (defaultConfigs.containsKey(moduleName)) {
			ModuleConfig config = defaultConfigs.get(moduleName).clone();
			config.loadConfig(configSection);
			
			var subMap = levelConfigs.getOrDefault(hopperLvl, new HashMap<String, ModuleConfig>());
			subMap.put(moduleName, config);
			levelConfigs.putIfAbsent(hopperLvl, subMap);
		} else {
			if (!ModuleRegistry.hasConfig(moduleName))
				return;

			ModuleConfig config = ModuleRegistry.getConfigConstructor(moduleName).get();
			config.loadConfig(configSection);
			
			var subMap = levelConfigs.getOrDefault(hopperLvl, new HashMap<String, ModuleConfig>());
			subMap.put(moduleName, config);
			levelConfigs.putIfAbsent(hopperLvl, subMap);
		}
	}

	public static ModuleConfig getNewConfig(int hopperLvl, String moduleName) {
		if (levelConfigs.containsKey(hopperLvl) && levelConfigs.get(hopperLvl).containsKey(moduleName)) {
			return levelConfigs.get(hopperLvl).get(moduleName).clone();
		}

		return defaultConfigs.get(moduleName).clone();
	}
}
