package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;

public class HelpCommand extends Commands {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean runCommand(Player player) {
        JSONObject object = new JSONObject();
        object.put("LAUNCH - ","Launch a robot into the world");
        object.put("STATE - ","Ask the robot for the state of the world");
        object.put("LOOK - ","Look around the world");
        object.put("REPAIR - ","Instructs the robot to repair its shields");
        object.put("RELOAD - ","Instruct the robot to reload its weapons");
        object.put("FIRE - ","Instruct the robot to fire its gun");
        object.put("ORIENTATION - ","Gets the direction and position");
        object.put("FORWARD - ","Moves forward by a specified number of steps");
        object.put("BACK - ","Moves back by a specified number of steps");
        object.put("RIGHT - ","Turned right");
        object.put("LEFT - ","Turned left");
        player.setMessage(object);
        return true;
    }

}
