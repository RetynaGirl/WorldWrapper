package com.teknoserval.worldwrapper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class TickHandler {

	private static JavaPlugin plugin;
	private static FileConfiguration config;

	public static void init(JavaPlugin plugin, FileConfiguration config) {

		TickHandler.plugin = plugin;
		TickHandler.config = config;

		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {

				checkTrackedEntities();

			}

		}, 0L, 5L);

	}

	public static void checkTrackedEntities() {

		for (Entity entity : EventListener.trackedEntities) {

			if (entity.isValid()) {

				if (needsWrapping(entity)) {

					Entity mount = entity.getVehicle();

					if (mount != null) {

						mount.teleport(EventListener.wrapper(mount.getLocation()));

						mount.addPassenger(entity);

					}

				}

			}

		}

	}

	public static boolean needsWrapping(Entity entity) {
		

		boolean needWrap = false;
		Location loc = entity.getLocation();

		if (loc.getX() > config.getInt("worldEdgeEast") || loc.getX() < config.getInt("worldEdgeWest")
				|| loc.getZ() > config.getInt("worldEdgeSouth") || loc.getZ() < config.getInt("worldEdgeNorth")) {

			needWrap = true;

		}

		
		return needWrap;

	}

}
