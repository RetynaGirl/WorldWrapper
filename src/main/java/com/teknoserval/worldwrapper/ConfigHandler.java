package com.teknoserval.worldwrapper;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

public class ConfigHandler {

	private static FileConfiguration config;

	public static void init(FileConfiguration config) {

		ConfigHandler.config = config;

		config.addDefault("worldEdgeNorth", -30000000);
		config.addDefault("worldEdgeSouth", 30000000);
		config.addDefault("worldEdgeEast", 30000000);
		config.addDefault("worldEdgeWest", -30000000);

		config.addDefault("wrapNorthSouth", false);
		config.addDefault("wrapEastWest", true);

		config.addDefault("trackedEntities", new ArrayList<Entity>());

		config.options().copyDefaults(true);

	}

}
