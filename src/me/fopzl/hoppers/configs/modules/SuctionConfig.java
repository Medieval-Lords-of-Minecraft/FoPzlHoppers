package me.fopzl.hoppers.configs.modules;

import org.bukkit.configuration.ConfigurationSection;

public class SuctionConfig extends ModuleConfig {
	private int minTickPeriod;
	private int maxTickPeriod;
	private int tickPeriodDelta; // period increase when no items picked up, or decrease when any are
	private int maxSuckRange;
	
	@Override
	public void loadConfig(ConfigurationSection config) {
		minTickPeriod = config.getInt("minTickPeriod", minTickPeriod);
		maxTickPeriod = config.getInt("maxTickPeriod", maxTickPeriod);
		tickPeriodDelta = config.getInt("tickPeriodDelta", tickPeriodDelta);
		maxSuckRange = config.getInt("maxSuckRange", maxSuckRange);
	}

	@Override
	public SuctionConfig clone() {
		SuctionConfig cfg = new SuctionConfig();
		cfg.minTickPeriod = this.minTickPeriod;
		cfg.maxTickPeriod = this.maxTickPeriod;
		cfg.tickPeriodDelta = this.tickPeriodDelta;
		cfg.maxSuckRange = this.maxSuckRange;

		return cfg;
	}

	public int getMinTickPeriod() {
		return minTickPeriod;
	}

	public int getMaxTickPeriod() {
		return maxTickPeriod;
	}

	public int getTickPeriodDelta() {
		return tickPeriodDelta;
	}

	public int getMaxSuckRange() {
		return maxSuckRange;
	}
}
