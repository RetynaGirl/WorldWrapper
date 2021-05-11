package com.teknoserval.worldwrapper;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.teknoserval.worldwrapper.ConfigHandler;

public class WorldWrapper extends JavaPlugin {

	private FileConfiguration config;
	
	
	@Override
	public void onEnable() {
		
		this.getCommand("basic").setExecutor(new CommandExecuter(this));
		
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		
		config = this.getConfig();
		ConfigHandler.init(config);
		saveConfig();
		
		EventListener.init(config, this);
		
		TickHandler.init(this, config);
		
	}
	
	@Override
	public void onDisable() {
		
		EventListener.shutdown();
		
		saveConfig();
		
	}
	
	

}
