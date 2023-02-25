package me.fopzl.hoppers.gui.modules;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.fopzl.hoppers.modules.HopperModule;
import me.neoblade298.neocore.bukkit.inventories.CoreInventory;

public abstract class ModuleGUI extends CoreInventory {
	public static final String BACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ5YzkzZjhiOWYyZjhmOTFhYTQzNzc1NTFjMjczODAwMmE3ODgxNmQ2MTJmMzlmMTQyZmM5MWEzZDcxM2FkIn19fQ==";
	public static final String UP_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ3ZDFkZDRhN2RhZmYyYWFmMjhlNmExMmEwMWY0MmQ3ZTUxNTkzZWYzZGVhNzYyZWY4MTg0N2IxZDRjNTUzOCJ9fX0=";
	public static final String DOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UxZjNjYzYzYzczYTZhMWRkZTcyZmUwOWM2YWM1NTY5Mzc2ZDdiNjEyMzFiYjc0MDc2NDM2ODc4OGNiZjFmYSJ9fX0=";

	protected final HopperModule module;

	public ModuleGUI(Player viewer, Inventory inv, HopperModule module) {
		super(viewer, inv);
		this.module = module;
	}
}
