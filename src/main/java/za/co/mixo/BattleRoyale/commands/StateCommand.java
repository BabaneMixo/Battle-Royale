package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;

public class StateCommand extends Commands{
    public StateCommand(){
        super("state");
    }

    @Override
    public boolean runCommand(Player player) {
        JSONObject state = new JSONObject();

        state.put("position", player.getPosition());
        state.put("direction", player.getCurrentDirection());
        state.put("shields", player.getCurrentShields());
        state.put("shots"," "+ player.getShotsRemaining());
        state.put("status", player.getStatus());
        player.setMessage(state);
        player.setState(state.toString());
        return true;
    }
}
