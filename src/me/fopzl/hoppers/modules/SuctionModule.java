package me.fopzl.hoppers.modules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;

public class SuctionModule extends HopperModule {
	// todo: auto-disable after extremely long period with no pickups

	// todo: all these configurable
	private static final int minTickPeriod = 10;
	private static final int maxTickPeriod = 200;
	private static final int tickPeriodDelta = 5; // period increase when no items picked up, or decrease when any are
	private static final int suckRange = 5;

	private static final int suckRangeSquared = suckRange * suckRange;
	
	private int ticks;
	private int tickPeriod;
	
	public SuctionModule(Hopper hopper) {
		super(hopper);
		
		tickPeriod = minTickPeriod;
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
			if (e instanceof Item item && item.getPickupDelay() == 0 && e.getLocation().distanceSquared(hopper.getLocation()) < suckRangeSquared) {
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
