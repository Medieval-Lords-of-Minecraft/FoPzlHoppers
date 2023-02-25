package me.fopzl.hoppers.gui.modules;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import me.fopzl.hoppers.modules.SuctionModule;

public class SuctionGUI extends ModuleGUI {
	public SuctionGUI(Player viewer, Inventory inv, SuctionModule module) {
		super(viewer, inv, module);
	}
	
	public static final Material iconMat = Material.CAULDRON;
	
	@Override
	public void handleInventoryClick(InventoryClickEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void handleInventoryClose(InventoryCloseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void handleInventoryDrag(InventoryDragEvent arg0) {
		// TODO Auto-generated method stub

	}
}
