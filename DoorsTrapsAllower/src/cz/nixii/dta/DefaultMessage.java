package cz.nixii.dta;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DefaultMessage {{
	
	File msg = new File(DoorsTrapsAllower.inst.getDataFolder() + "/messages.yml");
    if (!msg.exists()) {
      
        try {
          
            msg.createNewFile();
          
        } catch (IOException ex) {
          
            System.out.println("ERROR: Failed to create messages.yml file!");
            ex.printStackTrace();
          
        }
      
        FileConfiguration msgfc = YamlConfiguration.loadConfiguration(msg);
      
        //Default values 
        msgfc.set("DoorsTrapsSuccessAllowed", "&aYou successfully allowed doors/traps at &a%X% %Y% %Z%");
        msgfc.set("DoorsTrapsUnSuccessAllowed", "&4You unsuccessfully allowed doors/traps at &a%X% %Y% %Z%");
        msgfc.set("DoorsTrapsSuccessBanned", "&aYou successfully banned doors/traps at &a%X% %Y% %Z%");
        msgfc.set("DoorsTrapsUnSuccessBanned", "&4You unsuccessfully banned doors/traps at &a%X% %Y% %Z%");
        
        try {
			msgfc.save(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}}
