package com.teknoserval.worldwrapper;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PassengerTeleportRunnable implements Runnable {

	private List<Entity> passengers;
	private Entity vehicle;
	private JavaPlugin plugin;
	private Location newLoc;

	public PassengerTeleportRunnable(List<Entity> passengersIn, Entity vehicleIn, JavaPlugin pluginIn,
			Location newLocIn) {

		passengers = passengersIn;
		vehicle = vehicleIn;
		plugin = pluginIn;
		newLoc = newLocIn;

	}

	@Override
	public void run() {

		for (Entity passenger : passengers) {

			Vector facing = passenger.getLocation().getDirection();

			passenger.teleport(newLoc.setDirection(facing));

		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new PassengerInsertionRunnable(passengers, vehicle), 2L);

	}

}
