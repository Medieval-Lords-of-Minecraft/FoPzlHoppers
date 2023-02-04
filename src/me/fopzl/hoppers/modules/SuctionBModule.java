package me.fopzl.hoppers.modules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.util.tuples.Pair;
import me.fopzl.hoppers.util.tuples.Triplet;

public class SuctionBModule extends HopperModule implements Listener {
	private static final int suckRange = 3; // todo: configurable
	
	private static final int suckRangeSquared = suckRange * suckRange;
	
	// key is chunk world and coords, value is set of hoppers in that chunk
	private static Map<Triplet<World, Integer, Integer>, Set<Hopper>> hopperMap;

	static {
		hopperMap = new HashMap<Triplet<World, Integer, Integer>, Set<Hopper>>();
	}

	public SuctionBModule(Hopper hopper) {
		super(hopper);

		addToMap(hopper);
	}

	public static void onItemSpawn(ItemSpawnEvent e) {
		Set<Hopper> hoppers = getHoppersInRange(e.getLocation());

		Item item = e.getEntity();

		for (Hopper hopper : hoppers) {
			int startAmt = item.getItemStack().getAmount();

			ItemStack leftovers = hopper.getInventory().addItem(item.getItemStack()).get(0);
			if (leftovers == null) {
				e.setCancelled(true);
				return;
			} else {
				int endAmt = leftovers.getAmount();
				if (endAmt != startAmt) {
					e.getEntity().setItemStack(leftovers);
				}
			}
		}
	}
	
	private static Set<Hopper> getHoppersInRange(Location loc) {
		Set<Hopper> set = new HashSet<Hopper>();

		int chunkRange = 1 + suckRange / 16;

		int chunkX = loc.getChunk().getX();
		int chunkZ = loc.getChunk().getZ();

		for (int x = chunkX - chunkRange; x <= chunkX + chunkRange; x++) {
			for (int z = chunkZ - chunkRange; z <= chunkZ + chunkRange; z++) {
				Set<Hopper> chunk = hopperMap.get(Triplet.with(loc.getWorld(), x, z));
				if (chunk != null) {
					for (Hopper hopper : chunk) {
						if (hopper.getLocation().distanceSquared(loc) < suckRangeSquared) {
							set.add(hopper);
						}
					}
				}
			}
		}

		return set;
	}

	private static void addToMap(Hopper hopper) {
		int chunkX = hopper.getLocation().getChunk().getX();
		int chunkZ = hopper.getLocation().getChunk().getZ();
		Triplet<World, Integer, Integer> triplet = Triplet.with(hopper.getLocation().getWorld(), chunkX, chunkZ);
		Set<Hopper> set = hopperMap.getOrDefault(triplet, new HashSet<Hopper>());
		set.add(hopper);
		hopperMap.putIfAbsent(triplet, set);
	}

	@Override
	public void tick() {
	}

	@Override
	public void remove() {
		int chunkX = hopper.getLocation().getChunk().getX();
		int chunkZ = hopper.getLocation().getChunk().getZ();
		Pair<Integer, Integer> chunk = Pair.with(chunkX, chunkZ);
		hopperMap.get(chunk).remove(hopper);
	}
}
