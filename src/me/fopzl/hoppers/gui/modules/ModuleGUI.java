package me.fopzl.hoppers.gui.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.fopzl.hoppers.Hopper;
import me.neoblade298.neocore.bukkit.inventories.CoreInventory;

public abstract class ModuleGUI extends CoreInventory {
	protected final Hopper hopper;
	
	// default constructor
	// but modules should be specifying their own gui inv setups
	public ModuleGUI(Player viewer, Hopper hopper) {
		super(viewer, Bukkit.createInventory(viewer, 54));
		this.hopper = hopper;
	}
	
	public ModuleGUI(Player viewer, Inventory inv, Hopper hopper) {
		super(viewer, inv);
		this.hopper = hopper;
	}
}
