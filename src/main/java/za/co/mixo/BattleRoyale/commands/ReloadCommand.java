package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.Status;

public class ReloadCommand  extends Commands{
    public ReloadCommand(){
        super("reload");
    }

    @Override
    public boolean runCommand(Player player) {
        JSONObject reload = new JSONObject();
        player.setStatus(Status.RELOAD);
        player.setMessage(reload.put("message:","Reloading shots, do not interrupt."));

        return true;
    }
}
