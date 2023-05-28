package za.co.mixobabane.battleroyale.movements;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.Avatar.UpdateResponse;
import za.co.mixobabane.battleroyale.Commands.Commands;

public class ForwardCommand extends Commands {
    public ForwardCommand(JSONArray args){
        super("forward", args);
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        JSONArray arguments = getCommandArgs();
        JSONObject jsonObject = new JSONObject();
        int nrSteps = Integer.parseInt(arguments.get(0).toString());
        if (avatar.updatePosition(+nrSteps).equals(UpdateResponse.OK) ){
            avatar.setMessage(jsonObject.put("message: ", "Done").toString());
        }else if(avatar.updatePosition(nrSteps).equals(UpdateResponse.OUTSIDE_WORLD)){
            avatar.setMessage(jsonObject.put("message: ",
                    "Sorry! I cannot go outside my safe zone.").toString());
            return false;
        }else if (avatar.updatePosition(nrSteps).equals(UpdateResponse.OBSTRUCTED)){
            avatar.setMessage(jsonObject.put("message: ",
                    "There is an obstacle in the way.").toString());
            return false;
        }else if (avatar.updatePosition(nrSteps).equals(UpdateResponse.ROBOT_OBSTRUCTION)){
            avatar.setMessage(jsonObject.put("message: ",
                    "There is a robot in the way.").toString());
            return false;
        }
        return true;
    }
}
