package me.fopzl.hoppers.configs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import me.fopzl.hoppers.FoPzlHoppers;
import me.fopzl.hoppers.configs.modules.ModuleConfig;
import me.fopzl.hoppers.configs.modules.SuctionConfig;

// TODO: dynamic module registration
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
		// TODO
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
		String fullFileName = "configs/modules/" + fileName;
		File file = new File(FoPzlHoppers.getInstance().getDataFolder(), fullFileName);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			FoPzlHoppers.getInstance().saveResource(fullFileName, false);
		}
		
		ConfigurationSection configSection;
		ModuleConfig config;
		try {
			configSection = YamlConfiguration.loadConfiguration(file);
			config = getConfigConstructorByName(moduleName).get();
			config.loadConfig(configSection);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		defaultConfigs.put(moduleName, config);
	}

	private static Supplier<ModuleConfig> getConfigConstructorByName(String moduleName) throws Exception {
		switch (moduleName.trim().toLowerCase()) {
		case "suction":
		case "suctionmodule":
			return SuctionConfig::new;
		case "test":
		case "testmodule":
			throw new Exception(); // not yet implemented
		default:
			throw new Exception("No module found with name \"" + moduleName + "\"");
		}
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
			Supplier<ModuleConfig> supplier;
			try {
				supplier = getConfigConstructorByName(moduleName);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			ModuleConfig config = supplier.get();
			config.loadConfig(configSection);
			
			var subMap = levelConfigs.getOrDefault(hopperLvl, new HashMap<String, ModuleConfig>());
			subMap.put(moduleName, config);
			levelConfigs.putIfAbsent(hopperLvl, subMap);
		}
	}

	public static ModuleConfig getConfig(int hopperLvl, String moduleName) {
		if (levelConfigs.containsKey(hopperLvl) && levelConfigs.get(hopperLvl).containsKey(moduleName)) {
			return levelConfigs.get(hopperLvl).get(moduleName);
		}

		return defaultConfigs.get(moduleName);
	}
}
