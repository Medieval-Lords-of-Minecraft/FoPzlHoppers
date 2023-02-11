package me.fopzl.hoppers.configs.modules;

import org.bukkit.configuration.ConfigurationSection;

public class SuctionConfig extends ModuleConfig {
	private int minTickPeriod;
	private int maxTickPeriod;
	private int tickPeriodDelta; // period increase when no items picked up, or decrease when any are
	private int suckRange;
	
	private int suckRangeSquared;

	@Override
	public void loadConfig(ConfigurationSection config) {
		minTickPeriod = config.getInt("minTickPeriod", minTickPeriod);
		maxTickPeriod = config.getInt("maxTickPeriod", maxTickPeriod);
		tickPeriodDelta = config.getInt("tickPeriodDelta", tickPeriodDelta);
		suckRange = config.getInt("suckRange", suckRange);

		suckRangeSquared = suckRange * suckRange;
	}
	
	@Override
	public SuctionConfig clone() {
		SuctionConfig cfg = new SuctionConfig();
		cfg.minTickPeriod = this.minTickPeriod;
		cfg.maxTickPeriod = this.maxTickPeriod;
		cfg.tickPeriodDelta = this.tickPeriodDelta;
		cfg.suckRange = this.suckRange;

		cfg.suckRangeSquared = this.suckRangeSquared;
		
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
	
	public int getSuckRange() {
		return suckRange;
	}

	public int getSuckRangeSquared() {
		return suckRangeSquared;
	}
}
