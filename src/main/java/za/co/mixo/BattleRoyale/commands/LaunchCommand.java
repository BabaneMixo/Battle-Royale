package za.co.mixo.BattleRoyale.commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;

public class LaunchCommand extends Commands{
    public LaunchCommand(JSONArray args){
        super("launch", args);
    }

    @Override
    public boolean runCommand(Player player) {
        JSONArray arguments = getCommandArgs();
        JSONObject launchOutput = new JSONObject();
        if(arguments.length() == 1){
            try {
                int args = Integer.parseInt(arguments.get(0).toString());
                if (args == 1){ player.setRobotMake("Sniper");
                    player.setMaxShields(5);
                    player.setCurrentShields(5);
                    player.setDistance(5);
                    player.setMaxShots(1);
                    player.setShotsRemaining(1);
                } else if (args ==2) {
                    player.setRobotMake("AK-47");
                    player.setDistance(4);
                    player.setCurrentShields(5);
                    player.setMaxShields(5);
                    player.setMaxShots(2);
                    player.setShotsRemaining(2);
                } else if (args==3) {
                    player.setRobotMake("Steyr AUG");
                    player.setMaxShields(5);
                    player.setCurrentShields(5);
                    player.setDistance(3);
                    player.setMaxShots(3);
                    player.setShotsRemaining(3);
                } else if (args ==4) {
                    player.setRobotMake("M4 carbine");
                    player.setMaxShields(5);
                    player.setCurrentShields(5);
                    player.setDistance(2);
                    player.setMaxShots(4);
                    player.setShotsRemaining(4);
                } else if (args==5) {
                    player.setRobotMake("Pistol");
                    player.setDistance(1);
                    player.setCurrentShields(5);
                    player.setMaxShields(5);
                    player.setMaxShots(5);
                    player.setShotsRemaining(5);
                }



            }catch (NumberFormatException e){
                player.setMessage(launchOutput.put("message:","Maximum shields and shots should be integers and not empty."));
                return false;
            }
            launchOutput.put("position", player.getPosition());
            launchOutput.put("visibility", player.getVisibility());
            launchOutput.put("reload", player.getReloadSeconds());
            launchOutput.put("repair", player.getRepairSeconds());
            launchOutput.put("shields", player.getShields());

            player.setMessage(launchOutput);
        }else{
            player.setMessage(launchOutput.put("message","Launch command takes 1 argument."));
            return false;
        }
        return true;
    }
}
