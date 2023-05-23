package za.co.mixo.BattleRoyale.movements;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.UpdateResponse;
import za.co.mixo.BattleRoyale.commands.Commands;

public class BackCommand extends Commands {
    public BackCommand(JSONArray args){
        super("back", args);
    }

    @Override
    public boolean runCommand(Player player) {
        JSONArray arguments = getCommandArgs();
        JSONObject jsonObject = new JSONObject();
        int nrSteps = Integer.parseInt(arguments.get(0).toString());
        if (player.updatePosition(-nrSteps).equals(UpdateResponse.OK) ){
            player.setMessage(jsonObject.put("message: ", "Done"));
        }else if(player.updatePosition(-nrSteps).equals(UpdateResponse.OUTSIDE_WORLD)){
            player.setMessage(jsonObject.put("message: ",
                    "Sorry! I cannot go outside my safe zone."));
            return false;
        }else if (player.updatePosition(-nrSteps).equals(UpdateResponse.OBSTRUCTED)){
            player.setMessage(jsonObject.put("message: ",
                    "There is an obstacle in the way."));
            return false;
        }else if (player.updatePosition(-nrSteps).equals(UpdateResponse.ROBOT_OBSTRUCTION)){
            player.setMessage(jsonObject.put("message: ",
                    "There is a robot in the way."));
            return false;
        }
        return true;
    }
}
