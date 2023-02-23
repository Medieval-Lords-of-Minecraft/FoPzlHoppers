package me.fopzl.hoppers.gui.modules;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.fopzl.hoppers.Hopper;
import me.neoblade298.neocore.bukkit.inventories.CoreInventory;

public abstract class ModuleGUI extends CoreInventory {
	protected final Hopper hopper;
	
	public ModuleGUI(Player viewer, Inventory inv, Hopper hopper) {
		super(viewer, inv);
		this.hopper = hopper;
	}
}
