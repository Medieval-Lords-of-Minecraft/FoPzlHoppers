package me.fopzl.hoppers.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.fopzl.hoppers.FoPzlHoppers;
import me.fopzl.hoppers.Hopper;

// temp for dev+debug for a while
public class MainCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		
		if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
			FoPzlHoppers.reload();
			return true;
		}
		
		if (args.length >= 2 && args[0].equalsIgnoreCase("give")) {
			int lvl;
			try {
				lvl = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return false;
			}
			
			((Player) sender).getInventory().addItem(Hopper.getItem(lvl));
			return true;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
