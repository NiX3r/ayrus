package cz.nixii.dta;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DoorsTrapsAllower extends JavaPlugin implements Listener{

	public static JavaPlugin inst;
	
	private static List<String> list;
	
	private static String message;
	
	static FileConfiguration config;
	static File fList;
	static FileConfiguration fListFC;
	static File fMessage;
	static FileConfiguration fMessageFC;
	
	public void onEnable() {
		
		System.out.println("Starting plugin DoorsTrapsAllower programmed by NiX3r");
		
		inst = this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		new DefaultMessage();
		new DefaultList();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		getLocations();
		
	}
	
	private static void getLocations() {
		
		fList = new File(inst.getDataFolder() + "/list.yml");
		fListFC = YamlConfiguration.loadConfiguration(fList);
		
		list = fListFC.getStringList("Locations");
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent event) {
		
		Action action = event.getAction();
        Block clicked = event.getClickedBlock();
		String location = "";
		Boolean isNew = true;
        
        config = inst.getConfig();
        
        fList = new File(DoorsTrapsAllower.inst.getDataFolder() + "/list.yml");
		fListFC = YamlConfiguration.loadConfiguration(fList);
		fMessage = new File(DoorsTrapsAllower.inst.getDataFolder() + "/messages.yml");
		fMessageFC = YamlConfiguration.loadConfiguration(fMessage);
        
		//Bukkit.broadcastMessage(clicked.getType().toString());
		
		try {
			
			location = clicked.getX() + "-" + clicked.getY() + "-" + clicked.getZ();
			
			if(!list.isEmpty()) {
				
				for(String s: list) {
					
					if(s.equals(location)) {
						
						isNew = false;
						break;
						
					}
					
				}
				
			}
	        
			if(clicked.getType().toString().contains("DOOR") && !location.equals("")) { //Players opening doors
				
				//&& event.getPlayer().getInventory().getItemInHand().getType().equals(config.getString("ItemForAllowing"))
				if(action == Action.RIGHT_CLICK_BLOCK && event.getPlayer().hasPermission(config.getString("PermissionForAllowing")) ) {
					
					if(event.getPlayer().getInventory().getItemInHand().getType().toString().equals(config.getString("ItemForAllowing"))) {
						
						if(isNew) {
							
							list.add(location);
							
							fListFC.set("Locations", list);
							
							message = config.getString("Prefix");
							
							try {
								fListFC.save(fList);
								message = message + fMessageFC.getString("DoorsTrapsSuccessAllowed");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								message = message + fMessageFC.getString("DoorsTrapsUnSuccessAllowed");
							}
							
							message = message.replace("%X%", String.valueOf(clicked.getX()));
							message = message.replace("%Y%", String.valueOf(clicked.getY()));
							message = message.replace("%Z%", String.valueOf(clicked.getZ()));
							message = message.replaceAll("&", "§");
							event.getPlayer().sendMessage(message);
							
						}else {
							
							list.remove(list.indexOf(location));
							
							fListFC.set("Locations", list);
							
							message = config.getString("Prefix");
							
							try {
								fListFC.save(fList);
								message = message + fMessageFC.getString("DoorsTrapsSuccessBanned");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								message = message + fMessageFC.getString("DoorsTrapsUnSuccessBanned");
							}
							
							message = message.replace("%X%", String.valueOf(clicked.getX()));
							message = message.replace("%Y%", String.valueOf(clicked.getY()));
							message = message.replace("%Z%", String.valueOf(clicked.getZ()));
							message = message.replaceAll("&", "§");
							event.getPlayer().sendMessage(message);
							
						}
						
					}
					
				}else {
					
					if(isNew) {
						
						event.setCancelled(true);
						
					}
					
				}
				
				//Bukkit.broadcastMessage("Player " + event.getPlayer().getName() + " interact with doors.");
				
			}
			
		}catch(Exception e) {
			
			//nothing to here
			//location = clicked.getX() + "-" + clicked.getY() + "-" + clicked.getZ();
			event.setCancelled(true);
			
		}
		
		
		
	}
	
}
