package me.fopzl.hoppers.modules;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;

public abstract class HopperModule {
	protected final Hopper hopper;

	protected boolean enabled;

	public HopperModule(Hopper hopper) {
		this.hopper = hopper;
		this.enabled = false;
	}

	public final void enable() {
		enabled = true;
		onEnable();
		hopper.updated();
	}

	public final void disable() {
		enabled = false;
		onDisable();
		hopper.updated();
	}
	
	public final void reload() {
		disable();
		onReload();
		enable();
	}

	public final void tick() {
		if (enabled) {
			onTick();
		}
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public abstract String getName();

	public abstract ItemStack getGUIIcon(); // specified here so lore can be applied with hopper-specific data
	
	protected abstract void onEnable();
	
	protected abstract void onDisable();
	
	protected abstract void onReload();
	
	protected abstract void onTick();

	public abstract String getSaveData();
	
	public abstract void loadData(String data);
	
	public abstract boolean hasGUI();
	
	public abstract void openGUI(Player viewer);
}
