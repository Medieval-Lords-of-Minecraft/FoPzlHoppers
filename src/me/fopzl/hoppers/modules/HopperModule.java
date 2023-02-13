package me.fopzl.hoppers.modules;

import me.fopzl.hoppers.Hopper;

public abstract class HopperModule {
	protected final Hopper hopper;

	private boolean enabled;
	
	public HopperModule(Hopper hopper) {
		this.hopper = hopper;
		this.enabled = false;
	}
	
	public final void enable() {
		enabled = true;
		onEnable();
	}
	
	public final void disable() {
		enabled = false;
		onDisable();
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

	protected abstract void onEnable();

	protected abstract void onDisable();

	protected abstract void onReload();

	protected abstract void onTick();
}
