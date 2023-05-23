package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;

public class OrientationCommand extends Commands{
    public OrientationCommand(){
        super("orientation");
    }

    @Override
    public boolean runCommand(Player player) {
        JSONObject orientation = new JSONObject();

        orientation.put("direction", player.getCurrentDirection());
        orientation.put("position", player.getPosition());
        player.setMessage(orientation);
        return true;
    }
}
