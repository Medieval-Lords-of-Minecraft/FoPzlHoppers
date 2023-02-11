package me.fopzl.hoppers;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.fopzl.hoppers.configs.HopperConfig;
import me.fopzl.hoppers.modules.HopperModule;

public class Hopper {
	private final Inventory inventory;

	private final Location loc;
	private final UUID owner; // player
	private final int level;
	
	private final Set<HopperModule> modules;

	public Hopper(Block block, UUID owner, int level) {
		this.loc = block.getLocation();
		this.owner = owner;
		this.level = level;

		((org.bukkit.block.data.type.Hopper) block.getBlockData()).setEnabled(false); // <-- doesn't work (todo: fix)

		this.inventory = ((org.bukkit.block.Hopper) block.getState()).getInventory();

		modules = HopperConfig.getModules(this);

		enable();
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void remove() {
		for (HopperModule module : modules) {
			module.disable();
		}
	}

	public void tick() {
		for (HopperModule module : modules) {
			module.tick();
		}
	}
	
	public void enable() {
		for (HopperModule module : modules) {
			module.enable();
		}
	}
	
	public void disable() {
		for (HopperModule module : modules) {
			module.disable();
		}
	}
	
	public static ItemStack getItem(int level) {
		ItemStack item = new ItemStack(Material.HOPPER);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§eLevel " + level + " Hopper");
		meta.setLore(Arrays.asList("§6§oFoPzlHoppers"));
		item.setItemMeta(meta);

		NBTItem nbt = new NBTItem(item);
		nbt.setBoolean("fopzlhopper", true);
		nbt.setInteger("level", level);
		
		return nbt.getItem();
	}
}
