package me.fopzl.hoppers.modules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.configs.ConfigManager;
import me.fopzl.hoppers.configs.modules.SuctionConfig;

public class SuctionModule extends HopperModule {
	private final static String name = "suction";
	
	// todo: auto-disable after extremely long period with no pickups
	
	private SuctionConfig config;

	private int ticks;
	private int tickPeriod;

	public SuctionModule(Hopper hopper) {
		super(hopper);

		config = (SuctionConfig) ConfigManager.getNewConfig(hopper.getLevel(), name);

		tickPeriod = config.getMinTickPeriod();
		ticks = tickPeriod;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected void onEnable() {
	}
	
	@Override
	protected void onDisable() {
	}

	@Override
	protected void onReload() {
		int range = config.getSuckRange();
		config = (SuctionConfig) ConfigManager.getNewConfig(hopper.getLevel(), name);
		config.setSuckRange(range);
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
		
		ticks = tickPeriod; // just using this var out of convenience
		
		if (pickedUp)
			decTickPeriod();
		else
			incTickPeriod();

		if (ticks != tickPeriod) {
			ticks = tickPeriod;
			hopper.updated();
		}
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

	@Override
	public String getSaveData() {
		return enabled + "," + tickPeriod + "," + config.getSuckRange();
	}
	
	@Override
	public void loadData(String data) {
		String[] parts = data.split(",");
		
		enabled = Boolean.parseBoolean(parts[0]);
		tickPeriod = Integer.parseInt(parts[1]);
		
		config.setSuckRange(Integer.parseInt(parts[2]));
	}
}
