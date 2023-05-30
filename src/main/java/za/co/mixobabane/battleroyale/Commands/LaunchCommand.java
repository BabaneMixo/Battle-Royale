package za.co.mixobabane.battleroyale.Commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;

import java.io.IOException;

public class LaunchCommand extends Commands{
    public LaunchCommand(JSONArray args){
        super("launch", args);
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        JSONArray arguments = getCommandArgs();
        JSONObject launchOutput = new JSONObject();
        if(arguments.length() == 1){
            try {
                int args = Integer.parseInt(arguments.get(0).toString());
                if (args == 1){ avatar.setAvatarMake("Sniper");
                    avatar.setMaxShields(5);
                    avatar.setCurrentShields(5);
                    avatar.setDistance(50);
                    avatar.setMaxShots(10);
                    avatar.setShotsRemaining(10);
                } else if (args ==2) {
                    avatar.setAvatarMake("AK-47");
                    avatar.setDistance(40);
                    avatar.setCurrentShields(5);
                    avatar.setMaxShields(5);
                    avatar.setMaxShots(20);
                    avatar.setShotsRemaining(20);
                } else if (args==3) {
                    avatar.setAvatarMake("Steyr AUG");
                    avatar.setMaxShields(5);
                    avatar.setCurrentShields(5);
                    avatar.setDistance(30);
                    avatar.setMaxShots(30);
                    avatar.setShotsRemaining(30);
                } else if (args ==4) {
                    avatar.setAvatarMake("M4 carbine");
                    avatar.setMaxShields(5);
                    avatar.setCurrentShields(5);
                    avatar.setDistance(20);
                    avatar.setMaxShots(40);
                    avatar.setShotsRemaining(40);
                } else if (args==5) {
                    avatar.setAvatarMake("Pistol");
                    avatar.setDistance(10);
                    avatar.setCurrentShields(5);
                    avatar.setMaxShields(5);
                    avatar.setMaxShots(50);
                    avatar.setShotsRemaining(50);
                }



            }catch (NumberFormatException e){
                avatar.setMessage(launchOutput.put("message:","Maximum shields and shots should be integers and not empty."));
                return false;
            }
            launchOutput.put("message", "Avatar was successfully launched");
            launchOutput.put("Avatar Type", avatar.getAvatarMake());
            launchOutput.put("position",avatar.getPosition());
            launchOutput.put("visibility",avatar.getVisibility());
            launchOutput.put("reload",avatar.getReloadSeconds());
            launchOutput.put("repair",avatar.getRepairSeconds());
            launchOutput.put("shields",avatar.getShields());

            avatar.setMessage(launchOutput);
        }else{
            avatar.setMessage(launchOutput.put("message","Launch command takes 1 argument."));
            return false;
        }
        return true;
    }
}