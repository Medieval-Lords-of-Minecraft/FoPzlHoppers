package me.fopzl.hoppers.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.fopzl.hoppers.FoPzlHoppers;
import me.fopzl.hoppers.Hopper;
import me.fopzl.hoppers.configs.HopperConfig;

public class MainCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		
		if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
			FoPzlHoppers.reload();
			return true;
		}
		
		if (args.length >= 1 && args[0].equalsIgnoreCase("saveclean")) {
			FoPzlHoppers.saveClean();
			return true;
		}
		
		if (args.length >= 3 && args[0].equalsIgnoreCase("give")) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null)
				return false;

			int lvl;
			try {
				lvl = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return false;
			}
			
			p.getInventory().addItem(Hopper.getItem(lvl));
			return true;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> autos = new ArrayList<String>();

		if (args.length == 1) {
			autos.add("give");
			autos.add("reload");
			autos.add("saveclean");
		} else if (args.length == 2) {
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
		} else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
			for (var lvl : HopperConfig.getRegisteredLevels()) {
				autos.add(lvl.toString());
			}
		}

		return autos;
	}
}
