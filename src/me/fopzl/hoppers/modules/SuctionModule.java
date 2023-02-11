package me.fopzl.hoppers.modules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.configs.ConfigManager;
import me.fopzl.hoppers.configs.modules.SuctionConfig;

public class SuctionModule extends HopperModule {
	// todo: auto-disable after extremely long period with no pickups
	
	private SuctionConfig config;

	private int ticks;
	private int tickPeriod;

	public SuctionModule(Hopper hopper) {
		super(hopper);

		config = (SuctionConfig) ConfigManager.getConfig(hopper.getLevel(), "suction");

		tickPeriod = config.getMinTickPeriod();
		ticks = tickPeriod;
	}
	
	@Override
	protected void onEnable() {
	}
	
	@Override
	protected void onDisable() {
	}

	@Override
	protected void onTick() {
		if (--ticks > 0)
			return;

		boolean pickedUp = false;
		
		for (Entity e : hopper.getLocation().getChunk().getEntities()) {
			// instanceof is more performant than Entity.getType()
			if (e instanceof Item item && item.getPickupDelay() == 0 && e.getLocation().distanceSquared(hopper.getLocation()) < config.getSuckRangeSquared()) {
				int startAmt = item.getItemStack().getAmount();
				
				ItemStack leftovers = hopper.getInventory().addItem(item.getItemStack()).get(0);
				if (leftovers == null) {
					item.remove();
					pickedUp = true;
				} else {
					item.setItemStack(leftovers);
					
					int endAmt = leftovers.getAmount();
					if (endAmt != startAmt)
						pickedUp = true;
				}
			}
		}
		
		if (pickedUp)
			decTickPeriod();
		else
			incTickPeriod();

		ticks = tickPeriod;
	}
	
	// called when no items get picked up
	private void incTickPeriod() {
		tickPeriod += config.getTickPeriodDelta();
		if (tickPeriod > config.getMaxTickPeriod())
			tickPeriod = config.getMaxTickPeriod();
	}
	
	// called when any items get picked up
	private void decTickPeriod() {
		tickPeriod -= config.getTickPeriodDelta();
		if (tickPeriod < config.getMinTickPeriod())
			tickPeriod = config.getMinTickPeriod();
	}
}
