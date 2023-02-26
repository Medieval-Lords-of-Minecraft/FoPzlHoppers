package me.fopzl.hoppers;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.fopzl.hoppers.configs.HopperConfig;
import me.fopzl.hoppers.gui.HopperGUI;
import me.fopzl.hoppers.modules.HopperModule;

public class Hopper {
	private final Inventory inventory;
	
	private final Location loc;
	private final UUID owner; // player
	private final int level;

	private final Set<HopperModule> modules;
	
	private boolean markedForRemoval = false;
	
	public Hopper(Location loc, UUID owner, int level) {
		this(loc.getBlock(), owner, level);
	}
	
	public Hopper(Block block, UUID owner, int level) {
		this.loc = block.getLocation();
		this.owner = owner;
		this.level = level;

		this.inventory = ((org.bukkit.block.Hopper) block.getState()).getInventory();
		
		modules = HopperConfig.getNewModules(this);
	}

	public void reload() {
		for (HopperModule module : modules) {
			module.reload();
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

	public Set<HopperModule> getModules() {
		return modules;
	}

	public void remove() {
		for (HopperModule module : modules) {
			module.disable();
		}

		markedForRemoval = true;
		updated();
	}
	
	public void tick() {
		// this is probably faster than separating with events and handling in HopperManager
		if (!loc.isWorldLoaded() || !loc.getWorld().isChunkLoaded(loc.getChunk()))
			return;

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

	public void updated() {
		FoPzlHoppers.getHopperManager().hopperUpdated(this);
	}
	
	public void sqlSave(Statement insert, Statement delete) throws SQLException {
		String world = loc.getWorld().getName();
		int locX = loc.getBlockX();
		int locY = loc.getBlockY();
		int locZ = loc.getBlockZ();
		String ownerUUID = owner.toString();
		int level = this.level;

		if (markedForRemoval) {
			delete.addBatch(
					"delete from fopzlhoppers_hoppers where world = '" + world + "' and locX = " + locX + " and locY = " + locY + " and locZ = " + locZ + ";"
			);
		} else {
			// hacky but whatever, this needs to run before module inserts
			delete.addBatch(
					"insert into fopzlhoppers_hoppers (world, locX, locY, locZ, ownerUUID, level) values ('" + world + "', " + locX + ", " + locY + ", " + locZ
							+ ", '" + ownerUUID + "', " + level + ") on duplicate key update level = level;"
			);

			delete.addBatch(
					"delete from fopzlhoppers_modules where world = '" + world + "' and locX = " + locX + " and locY = " + locY + " and locZ = " + locZ + ";"
			);
			
			for (HopperModule module : modules) {
				String moduleName = module.getName();
				String data = module.getSaveData();
				if (data == null)
					continue;

				insert.addBatch(
						"insert into fopzlhoppers_modules (world, locX, locY, locZ, moduleName, data) values ('" + world + "', " + locX + ", " + locY + ", "
								+ locZ + ", '" + moduleName + "', '" + data + "');"
				);
			}
		}
	}

	public void sqlLoad(String moduleName, String data) {
		for (var module : modules) {
			if (module.getName().equals(moduleName)) {
				module.loadData(data);
			}
		}
	}

	public void openGUI(Player viewer) {
		new HopperGUI(viewer, Bukkit.createInventory(viewer, 27, "§eLevel " + level + " §7Hopper"), this);
	}

	public static ItemStack getItem(int level) {
		ItemStack item = new ItemStack(Material.HOPPER);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§eLevel " + level + " §7Hopper");
		meta.setLore(Arrays.asList("§8§oFoPzlHoppers"));
		item.setItemMeta(meta);
		
		NBTItem nbt = new NBTItem(item);
		nbt.setBoolean("fopzlhopper", true);
		nbt.setInteger("level", level);

		return nbt.getItem();
	}
}
