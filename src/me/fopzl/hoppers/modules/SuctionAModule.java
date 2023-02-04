package me.fopzl.hoppers.modules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;

public class SuctionAModule extends HopperModule {
	// todo: all these configurable
	private static final int minTickPeriod = 20;
	private static final int maxTickPeriod = 200;
	private static final int tickPeriodDelta = 10; // period increase when no items picked up, or decrease when any are
	private static final int suckRange = 3;

	private static final int suckRangeSquared = suckRange * suckRange;
	
	private int ticks;
	private int tickPeriod;
	
	public SuctionAModule(Hopper hopper) {
		super(hopper);
		
		ticks = 0;
		tickPeriod = minTickPeriod;
	}
	
	@Override
	public void tick() {
		if (++ticks % tickPeriod != 0)
			return;
		
		Entity[] nearbyEntities = hopper.getLocation().getChunk().getEntities();
		if (nearbyEntities.length == 0)
			incTickPeriod();

		for (Entity e : nearbyEntities) {
			// instanceof is more performant than Entity.getType()
			if (e instanceof Item item && item.getPickupDelay() == 0 && e.getLocation().distanceSquared(hopper.getLocation()) < suckRangeSquared) {
				int startAmt = item.getItemStack().getAmount();

				ItemStack leftovers = hopper.getInventory().addItem(item.getItemStack()).get(0);
				if (leftovers == null) {
					item.remove();
					decTickPeriod();
				} else {
					item.setItemStack(leftovers);

					int endAmt = leftovers.getAmount();
					if (endAmt != startAmt)
						decTickPeriod();
					else
						incTickPeriod();
				}
			}
		}
	}

	// called when no items get picked up
	private void incTickPeriod() {
		tickPeriod += tickPeriodDelta;
		if (tickPeriod > maxTickPeriod)
			tickPeriod = maxTickPeriod;
	}

	// called when any items get picked up
	private void decTickPeriod() {
		tickPeriod -= tickPeriodDelta;
		if (tickPeriod < minTickPeriod)
			tickPeriod = minTickPeriod;
	}
}
