package me.fopzl.hoppers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import me.fopzl.hoppers.configs.modules.ModuleConfig;
import me.fopzl.hoppers.modules.HopperModule;

public class ModuleRegistry {
	private static Map<String, ModuleRegistration> registeredModules = new HashMap<String, ModuleRegistration>();

	public static void registerModule(String name, Function<Hopper, HopperModule> moduleConstructor, Supplier<ModuleConfig> configConstructor) {
		registerModule(new ModuleRegistration(name, moduleConstructor, configConstructor));
	}

	public static void registerModule(ModuleRegistration reg) {
		registeredModules.put(reg.getName(), reg);
	}
	
	public static boolean isRegistered(String name) {
		return registeredModules.containsKey(name);
	}

	public static boolean hasConfig(String name) {
		return registeredModules.get(name).hasConfig();
	}

	public static ModuleRegistration getRegistration(String name) {
		return registeredModules.get(name);
	}
	
	public static Function<Hopper, HopperModule> getModuleConstructor(String moduleName) {
		return registeredModules.get(moduleName).getModuleConstructor();
	}
	
	public static Supplier<ModuleConfig> getConfigConstructor(String moduleName) {
		return registeredModules.get(moduleName).getConfigConstructor();
	}
}

class ModuleRegistration {
	private final String name;
	private final Function<Hopper, HopperModule> moduleConstructor;
	private final Supplier<ModuleConfig> configConstructor;

	public ModuleRegistration(String name, Function<Hopper, HopperModule> moduleConstructor, Supplier<ModuleConfig> configConstructor) {
		this.name = name;
		this.moduleConstructor = moduleConstructor;
		this.configConstructor = configConstructor;
	}

	public String getName() {
		return name;
	}
	
	public boolean hasConfig() {
		return configConstructor != null;
	}

	public Function<Hopper, HopperModule> getModuleConstructor() {
		return moduleConstructor;
	}
	
	public Supplier<ModuleConfig> getConfigConstructor() {
		return configConstructor;
	}
}
