package com.teknoserval.worldwrapper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecuter implements CommandExecutor {

	private final WorldWrapper plugin;

	public CommandExecuter(WorldWrapper plugin) {

		this.plugin = plugin;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("basic")) {

			return true;

		} else if (command.getName().equalsIgnoreCase("basic2")) {

			if (!(sender instanceof Player)) {

				sender.sendMessage("This command can only be run by a player.");

			} else {

				Player player = (Player) sender;

			}

		}

		return false;
	}

}
