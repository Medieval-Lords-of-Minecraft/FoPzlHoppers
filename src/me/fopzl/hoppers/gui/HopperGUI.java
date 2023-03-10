package me.fopzl.hoppers.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.modules.HopperModule;
import me.neoblade298.neocore.bukkit.inventories.CoreInventory;

public class HopperGUI extends CoreInventory {
	private final Hopper hopper;
	
	private Map<Integer, Consumer<Player>> guiOpeners;
	
	public HopperGUI(Player viewer, Inventory inv, Hopper hopper) {
		super(viewer, inv);
		this.hopper = hopper;
		
		setupButtons();
	}
	
	private void setupButtons() {
		List<HopperModule> moduleButtons = new ArrayList<HopperModule>();
		for (var mod : hopper.getModules()) {
			if (mod.hasGUI()) {
				moduleButtons.add(mod);
			}
		}
		moduleButtons.sort(Comparator.comparing(HopperModule::getName));
		
		ItemStack[] items = inv.getContents();
		
		guiOpeners = new HashMap<Integer, Consumer<Player>>();
		int addedCnt = 0;
		for (int slot : getButtonSlots(moduleButtons.size())) {
			var mod = moduleButtons.get(addedCnt++);
			guiOpeners.put(slot, p -> mod.openGUI(p));
			items[slot] = mod.getGUIIcon();
		}
		
		inv.setContents(items);
	}
	
	@Override
	public void handleInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == inv)
			e.setCancelled(true);
		
		int slot = e.getRawSlot();
		
		if (guiOpeners.containsKey(slot)) {
			guiOpeners.get(slot).accept(p);
		}
	}
	
	@Override
	public void handleInventoryClose(InventoryCloseEvent e) {
	}
	
	@Override
	public void handleInventoryDrag(InventoryDragEvent e) {
		if (e.getRawSlots().parallelStream().anyMatch(x -> x < 27))
			e.setCancelled(true);
	}
	
	// utility
	// assumes 3 rows of 9 total
	private static List<Integer> getButtonSlots(int moduleCount) {
		List<Integer> slots = new ArrayList<Integer>();

		if (moduleCount > 27) // no
			return null;
		
		// invert for count > half of total capacity
		if (moduleCount > 13) {
			slots = IntStream.range(0, 27).boxed().collect(Collectors.toList());
			slots.removeAll(getButtonSlots(27 - moduleCount));
			return slots;
		}

		int row1Cnt, row2Cnt, row3Cnt;
		// first determine # of slots per row
		if (moduleCount < 5) {
			row3Cnt = 0;
			row2Cnt = moduleCount;
			row1Cnt = 0;
		} else if (moduleCount < 10) {
			row3Cnt = moduleCount / 2;
			row2Cnt = 0;
			row1Cnt = moduleCount - row3Cnt;
		} else {
			row3Cnt = moduleCount / 3;
			row2Cnt = (moduleCount - row3Cnt) / 2;
			row1Cnt = moduleCount - row3Cnt - row2Cnt;
		}

		// then determine which slots within each row and convert to actual slot #s
		int rowStart = 5 - row1Cnt;
		for (int i = 0; i < row1Cnt; i++) {
			slots.add(rowStart + 2 * i);
		}
		
		rowStart = 5 - row2Cnt;
		for (int i = 0; i < row2Cnt; i++) {
			slots.add(rowStart + 2 * i + 9);
		}
		
		rowStart = 5 - row3Cnt;
		for (int i = 0; i < row3Cnt; i++) {
			slots.add(rowStart + 2 * i + 18);
		}

		return slots;
	}
}
