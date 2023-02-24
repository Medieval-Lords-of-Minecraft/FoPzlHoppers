package me.fopzl.hoppers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.bukkit.entity.Player;

import me.fopzl.hoppers.configs.modules.ModuleConfig;
import me.fopzl.hoppers.gui.modules.ModuleGUI;
import me.fopzl.hoppers.modules.HopperModule;

public class ModuleRegistry {
	private static Map<String, ModuleRegistration> registeredModules = new HashMap<String, ModuleRegistration>();

	public static void registerModule(
			String name, Function<Hopper, HopperModule> moduleConstructor, Supplier<ModuleConfig> configConstructor,
			BiFunction<Player, Hopper, ModuleGUI> guiOpener
	) {
		registerModule(new ModuleRegistration(name, moduleConstructor, configConstructor, guiOpener));
	}

	public static void registerModule(ModuleRegistration reg) {
		registeredModules.put(reg.getName(), reg);
	}
	
	public static boolean isRegistered(String moduleName) {
		return registeredModules.containsKey(moduleName);
	}

	public static boolean hasConfig(String moduleName) {
		return registeredModules.get(moduleName).hasConfig();
	}

	public static boolean hasGUI(String moduleName) {
		return registeredModules.get(moduleName).hasGUI();
	}

	public static BiFunction<Player, Hopper, ModuleGUI> getGUIOpener(String moduleName) {
		return registeredModules.get(moduleName).getGUIOpener();
	}

	public static ModuleRegistration getRegistration(String moduleName) {
		return registeredModules.get(moduleName);
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
	private final BiFunction<Player, Hopper, ModuleGUI> guiOpener;

	public ModuleRegistration(
			String name, Function<Hopper, HopperModule> moduleConstructor, Supplier<ModuleConfig> configConstructor,
			BiFunction<Player, Hopper, ModuleGUI> guiOpener
	) {
		this.name = name;
		this.moduleConstructor = moduleConstructor;
		this.configConstructor = configConstructor;
		this.guiOpener = guiOpener;
	}

	public String getName() {
		return name;
	}
	
	public boolean hasConfig() {
		return configConstructor != null;
	}
	
	public boolean hasGUI() {
		return guiOpener != null;
	}

	public Function<Hopper, HopperModule> getModuleConstructor() {
		return moduleConstructor;
	}
	
	public Supplier<ModuleConfig> getConfigConstructor() {
		return configConstructor;
	}

	public BiFunction<Player, Hopper, ModuleGUI> getGUIOpener() {
		return guiOpener;
	}
}
