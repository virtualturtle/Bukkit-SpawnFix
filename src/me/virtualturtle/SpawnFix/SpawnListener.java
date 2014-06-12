package me.virtualturtle.SpawnFix;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnListener implements Listener {
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_BLOCK &&
				player.getItemInHand().getType() == Material.AIR &&
				block.getType() == Material.MOB_SPAWNER) {
			
			if (player.getLevel() < SpawnFix.levels){
				player.sendMessage("ERROR 404: " + SpawnFix.levels + " LEVELS NOT FOUND");
				return;
			}
			
			CreatureSpawner testSpawner = (CreatureSpawner) block.getState();
			
			player.setItemInHand(new ItemStack(Material.MOB_SPAWNER, 1));
			
			ItemMeta data = player.getItemInHand().getItemMeta();
			List<String> lore = new ArrayList<String>();
			
			lore.add(testSpawner.getSpawnedType().name());
			
			data.setLore(lore);
			player.getItemInHand().setItemMeta(data);
			player.setLevel(player.getLevel() - SpawnFix.levels);
			block.breakNaturally();
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlaceBlock(BlockPlaceEvent event){
		Block block = event.getBlock();
		if (block.getType() == Material.MOB_SPAWNER){
			CreatureSpawner testSpawner = (CreatureSpawner) block.getState();
			List<String> lore = event.getItemInHand().getItemMeta().getLore();
			if (lore != null && !lore.isEmpty()){
				testSpawner.setSpawnedType(EntityType.valueOf(lore.get(0)));
			}
		}
	}
}