package me.fopzl.hoppers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.fopzl.hoppers.modules.HopperModule;
import me.fopzl.hoppers.modules.SuctionAModule;
import me.fopzl.hoppers.modules.SuctionBModule;
import me.fopzl.hoppers.modules.TestModule;

public class Hopper {
	private final Inventory inventory;

	private final Location loc;
	private final UUID owner; // player
	private final int level;
	
	private final List<HopperModule> modules;

	public Hopper(Block block, UUID owner, int level) {
		this.loc = block.getLocation();
		this.owner = owner;
		this.level = level;

		((org.bukkit.block.data.type.Hopper) block.getBlockData()).setEnabled(false);
		this.inventory = ((org.bukkit.block.Hopper) block.getState()).getInventory();

		modules = new ArrayList<HopperModule>();

		switch (level) {
		case 1:
			modules.add(new TestModule(this));
			break;
		case 2:
			modules.add(new SuctionAModule(this));
			break;
		case 3:
			modules.add(new SuctionBModule(this));
			break;
		}
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

	public void tick() {
		for (HopperModule module : modules) {
			module.tick();
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
