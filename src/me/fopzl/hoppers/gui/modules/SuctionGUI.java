package me.fopzl.hoppers.gui.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import me.fopzl.hoppers.Hopper;

public class SuctionGUI extends ModuleGUI {
	public static final Material iconMat = Material.CAULDRON;

	public SuctionGUI(Player viewer, Hopper hopper) {
		super(viewer, Bukkit.createInventory(viewer, 9), hopper);
	}
	
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
