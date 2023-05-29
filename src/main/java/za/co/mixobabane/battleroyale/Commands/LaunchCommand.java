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
                if (args == 1){ avatar.setRobotMake("Sniper");
                    avatar.setMaxShields(5);
                    avatar.setCurrentShields(5);
                    avatar.setDistance(5);
                    avatar.setMaxShots(1);
                    avatar.setShotsRemaining(1);
                } else if (args ==2) {
                    avatar.setRobotMake("AK-47");
                    avatar.setDistance(4);
                    avatar.setCurrentShields(5);
                    avatar.setMaxShields(5);
                    avatar.setMaxShots(2);
                    avatar.setShotsRemaining(2);
                } else if (args==3) {
                    avatar.setRobotMake("Steyr AUG");
                    avatar.setMaxShields(5);
                    avatar.setCurrentShields(5);
                    avatar.setDistance(3);
                    avatar.setMaxShots(3);
                    avatar.setShotsRemaining(3);
                } else if (args ==4) {
                    avatar.setRobotMake("M4 carbine");
                    avatar.setMaxShields(5);
                    avatar.setCurrentShields(5);
                    avatar.setDistance(2);
                    avatar.setMaxShots(4);
                    avatar.setShotsRemaining(4);
                } else if (args==5) {
                    avatar.setRobotMake("Pistol");
                    avatar.setDistance(1);
                    avatar.setCurrentShields(5);
                    avatar.setMaxShields(5);
                    avatar.setMaxShots(5);
                    avatar.setShotsRemaining(5);
                }



            }catch (NumberFormatException e){
                avatar.setMessage(launchOutput.put("message:","Maximum shields and shots should be integers and not empty."));
                return false;
            }
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