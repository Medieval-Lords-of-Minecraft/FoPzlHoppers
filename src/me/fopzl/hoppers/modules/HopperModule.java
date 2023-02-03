package me.fopzl.hoppers.modules;

import me.fopzl.hoppers.Hopper;

public abstract class HopperModule {
	protected final Hopper hopper;

	public HopperModule(Hopper hopper) {
		this.hopper = hopper;
	}
	
	public abstract void tick();
}
