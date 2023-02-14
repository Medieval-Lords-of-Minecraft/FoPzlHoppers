package me.fopzl.hoppers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.neoblade298.neocore.bukkit.NeoCore;
import me.neoblade298.neocore.bukkit.io.IOComponent;

public class HopperIO implements IOComponent {

	@Override
	public void cleanup(Statement insert, Statement delete) {
		autosave(insert, delete);
	}
	
	@Override
	public void autosave(Statement insert, Statement delete) {
		try {
			FoPzlHoppers.getHopperManager().sqlSave(insert, delete);
		} catch (SQLException e) {
			Bukkit.getLogger().warning("[FHOP] Failed to save to SQL:");
			e.printStackTrace();
		}
	}

	@Override
	public void loadPlayer(Player player, Statement stmt) {
	}

	@Override
	public void preloadPlayer(OfflinePlayer player, Statement stmt) {
	}

	@Override
	public void savePlayer(Player player, Statement insert, Statement delete) {
	}
	
	public static void loadData() {
		try (Connection con = NeoCore.getConnection("FoPzlHoppersIOLoad")) {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select world, locX, locY, locZ, ownerUUID, level from fopzlhoppers_hoppers");
			while (rs.next()) {
				World world = Bukkit.getWorld(rs.getString("world"));
				int locX = rs.getInt("locX");
				int locY = rs.getInt("locY");
				int locZ = rs.getInt("locZ");
				
				Location loc = new Location(world, locX, locY, locZ);
				UUID ownerUUID = UUID.fromString(rs.getString("ownerUUID"));
				int level = rs.getInt("level");

				Hopper hopper = new Hopper(loc, ownerUUID, level);
				FoPzlHoppers.getHopperManager().addHopper(hopper);
			}
			rs.close();
			
			rs = stmt.executeQuery("select world, locX, locY, locZ, moduleName, data from fopzlhoppers_modules");
			while (rs.next()) {
				World world = Bukkit.getWorld(rs.getString("world"));
				int locX = rs.getInt("locX");
				int locY = rs.getInt("locY");
				int locZ = rs.getInt("locZ");
				
				Location loc = new Location(world, locX, locY, locZ);
				String moduleName = rs.getString("moduleName");
				String data = rs.getString("data");
				
				FoPzlHoppers.getHopperManager().getHopper(loc).sqlLoad(moduleName, data);
			}
			rs.close();

			stmt.close();
			con.close();
		} catch (SQLException e) {
			Bukkit.getLogger().warning("[FHOP] WARNING!!! Failed to load data from sql!!!");
			e.printStackTrace();
			FoPzlHoppers.getInstance().onDisable();
		}
	}
}
