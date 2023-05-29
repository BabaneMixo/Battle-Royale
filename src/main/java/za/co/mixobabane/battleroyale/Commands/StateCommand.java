package za.co.mixobabane.battleroyale.Commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;

public class StateCommand extends Commands{
    public StateCommand(){
        super("state");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        JSONObject state = new JSONObject();
        state.put("Med Kit",avatar.getAvatarMake());
        state.put("position",avatar.getPosition());
        state.put("direction",avatar.getCurrentDirection());
        state.put("shields",avatar.getCurrentShields());
        state.put("shots"," "+avatar.getShotsRemaining());
        state.put("status",avatar.getStatus());
        avatar.setMessage(state);
        avatar.setState(state.toString());
        return true;
    }
}
