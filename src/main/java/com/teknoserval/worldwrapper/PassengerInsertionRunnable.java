package com.teknoserval.worldwrapper;

import java.util.List;

import org.bukkit.entity.Entity;

public class PassengerInsertionRunnable implements Runnable {

	private List<Entity> passengers;
	private Entity vehicle;

	public PassengerInsertionRunnable(List<Entity> passengersIn, Entity vehicleIn) {

		passengers = passengersIn;
		vehicle = vehicleIn;

	}

	@Override
	public void run() {

		for (Entity passenger : passengers) {

			vehicle.addPassenger(passenger);

		}

	}

}
