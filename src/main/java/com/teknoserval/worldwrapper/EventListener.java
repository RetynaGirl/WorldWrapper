package com.teknoserval.worldwrapper;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.entity.EntityMountEvent;

public class EventListener implements Listener {

	private static FileConfiguration config;
	private static JavaPlugin plugin;

	public static ArrayList<Entity> trackedEntities = new ArrayList<Entity>();

	public static void init(FileConfiguration myConfig, JavaPlugin myPlugin) {

		config = myConfig;
		plugin = myPlugin;

		trackedEntities = (ArrayList<Entity>) config.get("trackedEntities");

		for (Entity entity : trackedEntities) {

			if (!entity.isValid()) {

				trackedEntities.remove(entity);

			}

		}

	}

	public static void shutdown() {

		config.set("trackedEntities", trackedEntities);

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		Player player = event.getPlayer();

		if (!TickHandler.needsWrapping(player)) {

			return;

		}

		Location loc = event.getTo();

		Entity mount = player.getVehicle();

		if (mount != null) {

			if (mount.getType() == EntityType.HORSE || mount.getType() == EntityType.PIG) {

				Location toLocation = mount.getLocation().subtract(loc);

				VehicleMoveEvent newEvent = new VehicleMoveEvent((Vehicle) mount, mount.getLocation(),
						wrapper(player.getLocation().add(toLocation)));

				Bukkit.getPluginManager().callEvent(newEvent);

			}

			return;

		}

		event.setTo(wrapper(loc));

	}

	@EventHandler
	public void onVehicleMove(VehicleMoveEvent event) {

		Location loc = event.getTo();
		Entity vehicle = event.getVehicle();

		if (TickHandler.needsWrapping(vehicle)) {

			List<Entity> passengers = new ArrayList<Entity>();

			if (!vehicle.isEmpty()) {

				passengers = vehicle.getPassengers();

			}

			for (Entity passenger : passengers) {

				vehicle.removePassenger(passenger);

			}

			Location newLoc = wrapper(loc);

			newLoc.getChunk().load();

			vehicle.teleport(newLoc);

			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
					new PassengerTeleportRunnable(passengers, vehicle, plugin, newLoc), 2L);

		}

	}

	@EventHandler
	public void onEntityMount(EntityMountEvent event) {

		if (!(event.getEntity() instanceof Player)) {

			Entity mount = event.getMount();
			trackedEntities.add(mount);

			Entity rider = event.getEntity();
			trackedEntities.add(rider);
		}

	}

	public static Location wrapper(Location loc) {

		if (config.getBoolean("wrapNorthSouth")) {
			if (loc.getZ() > config.getInt("worldEdgeSouth")) {

				loc.setZ(config.getInt("worldEdgeNorth") + 1);

			} else if (loc.getZ() < config.getInt("worldEdgeNorth")) {

				loc.setZ(config.getInt("worldEdgeSouth") - 1);

			}
		}

		if (config.getBoolean("wrapEastWest")) {
			if (loc.getX() > config.getInt("worldEdgeEast")) {

				loc.setX(config.getInt("worldEdgeWest") + 1);

			} else if (loc.getX() < config.getInt("worldEdgeWest")) {

				loc.setX(config.getInt("worldEdgeEast") - 1);

			}
		}

		return loc;

	}

}
