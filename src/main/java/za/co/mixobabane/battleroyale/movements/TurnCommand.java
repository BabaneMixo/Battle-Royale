package za.co.mixobabane.battleroyale.movements;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.Avatar.Direction;
import za.co.mixobabane.battleroyale.Commands.Commands;


public class TurnCommand extends Commands {
    public TurnCommand(JSONArray args){
        super("turn", args);
    }

    @Override
    public boolean runCommand(Avatar avatar){
        JSONObject turnOutput = new JSONObject();
        Direction currentDirection = avatar.getCurrentDirection();
        if(getCommandArgs().get(0).toString().equalsIgnoreCase("right")){
            if(currentDirection.equals(Direction.NORTH)){
                avatar.setCurrentDirection(Direction.EAST);
            } else if(currentDirection.equals(Direction.EAST)){
                avatar.setCurrentDirection(Direction.SOUTH);
            }else if(currentDirection.equals(Direction.SOUTH)){
                avatar.setCurrentDirection(Direction.WEST);
            }else if(currentDirection.equals(Direction.WEST)){
                avatar.setCurrentDirection(Direction.NORTH);
            }
            turnOutput.put("message","DONE: I turned right");

        }else if(getCommandArgs().get(0).toString().equalsIgnoreCase("left")){
            if(currentDirection.equals(Direction.NORTH)){
                avatar.setCurrentDirection(Direction.WEST);
            } else if(currentDirection.equals(Direction.EAST)){
                avatar.setCurrentDirection(Direction.NORTH);
            }else if(currentDirection.equals(Direction.SOUTH)){
                avatar.setCurrentDirection(Direction.EAST);
            }else if(currentDirection.equals(Direction.WEST)){
                avatar.setCurrentDirection(Direction.SOUTH);
            }
            turnOutput.put("message","DONE: I turned left");
        }else {
            turnOutput.put("message","Can't turn " + getCommandArgs().get(0));
            avatar.setMessage(turnOutput);
            return false;
        }
        avatar.setMessage(turnOutput);
        return true;
    }

}
