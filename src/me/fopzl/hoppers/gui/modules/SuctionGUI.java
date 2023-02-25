package me.fopzl.hoppers.gui.modules;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.modules.SuctionModule;
import me.neoblade298.neocore.bukkit.inventories.CoreInventory;

public class SuctionGUI extends ModuleGUI {
	public static final Material iconMat = Material.CAULDRON;
	
	private SuctionModule module;
	
	public SuctionGUI(Player viewer, Inventory inv, SuctionModule module) {
		super(viewer, inv, module);
		
		this.module = module;
		
		setupButtons();
	}
	
	private void setupButtons() {
		ItemStack[] items = inv.getContents();

		items[0] = CoreInventory.createButton(BACK_HEAD, "&9Back");
		items[3] = CoreInventory.createButton(UP_HEAD, "&9Increase Range");
		items[4] = CoreInventory.createButton(Material.CAULDRON, "&7Range = &f" + module.getSuckRange());
		items[5] = CoreInventory.createButton(DOWN_HEAD, "&9Decrease Range");

		if (module.isEnabled()) {
			items[7] = CoreInventory.createButton(Material.GLOW_ITEM_FRAME, "&9Click to Disable");
		} else {
			items[7] = CoreInventory.createButton(Material.ITEM_FRAME, "&9Click to Enable");
		}

		inv.setContents(items);
	}
	
	@Override
	public void handleInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == inv)
			e.setCancelled(true);

		int slot = e.getRawSlot();

		switch (slot) {
		case 0:
			module.getHopper().openGUI(p);
			break;
		case 3:
			module.setSuckRange(module.getSuckRange() + 1);
			break;
		case 5:
			module.setSuckRange(module.getSuckRange() - 1);
			break;
		case 7:
			if (module.isEnabled()) {
				module.disable();
			} else {
				module.enable();
			}
			break;
		}

		setupButtons();
	}
	
	@Override
	public void handleInventoryClose(InventoryCloseEvent e) {
	}
	
	@Override
	public void handleInventoryDrag(InventoryDragEvent e) {
		if (e.getRawSlots().parallelStream().anyMatch(x -> x < 9))
			e.setCancelled(true);
	}
}
