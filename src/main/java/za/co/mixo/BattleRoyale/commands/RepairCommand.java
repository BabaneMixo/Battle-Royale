package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.Status;

public class RepairCommand extends Commands{
    public RepairCommand(){
        super("repair");
    }

    @Override
    public boolean runCommand(Player player) {

        JSONObject reload = new JSONObject();
        player.setStatus(Status.REPAIR);
        player.setMessage(reload.put("message:","Repairing shields, do not interrupt."));

        return true;
    }
}
