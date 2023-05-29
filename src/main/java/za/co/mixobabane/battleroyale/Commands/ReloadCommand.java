package za.co.mixobabane.battleroyale.Commands;


import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.*;

public class ReloadCommand  extends Commands{
    public ReloadCommand(){
        super("reload");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        JSONObject reload = new JSONObject();
        avatar.setStatus(Status.RELOAD);
        avatar.setMessage(reload.put("message:","Reloading shots, do not interrupt."));

        return true;
    }
}