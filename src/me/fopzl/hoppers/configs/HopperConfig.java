package me.fopzl.hoppers.configs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.configuration.file.YamlConfiguration;

import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.ModuleRegistry;
import me.fopzl.hoppers.modules.HopperModule;

public class HopperConfig {
	private static Map<Integer, Set<Function<Hopper, HopperModule>>> moduleConstructors;

	public static void loadConfig(YamlConfiguration config) {
		moduleConstructors = new HashMap<Integer, Set<Function<Hopper, HopperModule>>>();
		
		for (var lvlKey : config.getKeys(false)) {
			var set = new HashSet<Function<Hopper, HopperModule>>();
			
			var lvlSection = config.getConfigurationSection(lvlKey);
			int lvl = Integer.parseInt(lvlKey);
			
			for (var moduleKey : lvlSection.getKeys(false)) {
				set.add(ModuleRegistry.getModuleConstructor(moduleKey));
				ConfigManager.addLvlOverride(moduleKey, lvl, lvlSection.getConfigurationSection(moduleKey));
			}
			
			moduleConstructors.put(lvl, set);
		}
	}

	public static Set<HopperModule> getModules(Hopper hopper) {
		Set<HopperModule> modules = new HashSet<HopperModule>();
		
		for (var func : moduleConstructors.get(hopper.getLevel())) {
			modules.add(func.apply(hopper));
		}
		
		return modules;
	}
}
