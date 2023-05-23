package za.co.mixo.BattleRoyale.movements;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Direction;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.commands.Commands;

public class TurnCommand extends Commands {
    public TurnCommand(JSONArray args){
        super("turn", args);
    }

    @Override
    public boolean runCommand(Player player){
        JSONObject turnOutput = new JSONObject();
        Direction currentDirection = player.getCurrentDirection();
        if(getCommandArgs().get(0).toString().equalsIgnoreCase("right")){
            if(currentDirection.equals(Direction.NORTH)){
                player.setCurrentDirection(Direction.EAST);
            } else if(currentDirection.equals(Direction.EAST)){
                player.setCurrentDirection(Direction.SOUTH);
            }else if(currentDirection.equals(Direction.SOUTH)){
                player.setCurrentDirection(Direction.WEST);
            }else if(currentDirection.equals(Direction.WEST)){
                player.setCurrentDirection(Direction.NORTH);
            }
            turnOutput.put("message","DONE: I turned right");

        }else if(getCommandArgs().get(0).toString().equalsIgnoreCase("left")){
            if(currentDirection.equals(Direction.NORTH)){
                player.setCurrentDirection(Direction.WEST);
            } else if(currentDirection.equals(Direction.EAST)){
                player.setCurrentDirection(Direction.NORTH);
            }else if(currentDirection.equals(Direction.SOUTH)){
                player.setCurrentDirection(Direction.EAST);
            }else if(currentDirection.equals(Direction.WEST)){
                player.setCurrentDirection(Direction.SOUTH);
            }
            turnOutput.put("message","DONE: I turned left");
        }else {
            turnOutput.put("message","Can't turn " + getCommandArgs().get(0));
            player.setMessage(turnOutput);
            return false;
        }
        player.setMessage(turnOutput);
        return true;
    }
}
