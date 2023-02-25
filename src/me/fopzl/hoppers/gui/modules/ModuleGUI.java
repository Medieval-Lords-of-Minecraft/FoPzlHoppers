package me.fopzl.hoppers.gui.modules;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.fopzl.hoppers.modules.HopperModule;
import me.neoblade298.neocore.bukkit.inventories.CoreInventory;

public abstract class ModuleGUI extends CoreInventory {
	protected final HopperModule module;
	
	public ModuleGUI(Player viewer, Inventory inv, HopperModule module) {
		super(viewer, inv);
		this.module = module;
	}
}
