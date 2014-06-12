package me.virtualturtle.SpawnFix;

import org.bukkit.plugin.java.JavaPlugin;

public class SpawnFix extends JavaPlugin{
	private SpawnListener listener; 
	public static int levels = 80;
	
	public SpawnFix() {
		listener = new SpawnListener();
	}
	
	public void onEnable() {
		ReadLevels();
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	private void ReadLevels() {
		SpawnFix.levels = getConfig().getInt("Levels", SpawnFix.levels);
		getConfig().set("Levels", SpawnFix.levels);
		saveConfig();
	}
}
