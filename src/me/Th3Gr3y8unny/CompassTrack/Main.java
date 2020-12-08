package me.Th3Gr3y8unny.CompassTrack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static String name;
	private static Player ptracking;
	
	@Override
	public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (args.length == 1) {
			if(Bukkit.getPlayerExact(args[0]) != null) {
				ptracking = Bukkit.getPlayerExact(args[0]);
				name = args[0];
				
				p.sendMessage(ChatColor.GREEN + "Tracking " + name + "!" );
           	 	Location loc = ptracking.getLocation();
           	 	p.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
				p.setCompassTarget(loc);
			}
			else
				p.sendMessage(ChatColor.RED + "Not a valid player!");
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerClicks(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    Action action = event.getAction();
	    ItemStack item = event.getItem();
	    
	    //Player right clicks
	     if ( action.equals( Action.RIGHT_CLICK_AIR ) || action.equals( Action.RIGHT_CLICK_BLOCK ) ) {
	    	 
	    	 //Checks if item is a compass
	         if ( item != null && item.getType() == Material.COMPASS ) {
	        	 
	        	 //Checks if the "/track <name>" was used to track a player
	             if(name == null) {
	            	 player.sendMessage(ChatColor.RED + "Use /track <name> to track a player!");
	             }
	             
	             //Checks if player is in the same world
	             if(ptracking.getWorld() != player.getWorld()) {
	            	 player.sendMessage(ChatColor.RED + ptracking.getDisplayName() + "is in a different World!");
	             }
	             else {
	            	 player.sendMessage(ChatColor.GREEN + "Tracking " + name + "!");
	            	 Location loc = ptracking.getLocation();
	            	 
	            	 
	            	 player.setCompassTarget(loc);
	            	
	             }
	         } 
	     }
	}
}
