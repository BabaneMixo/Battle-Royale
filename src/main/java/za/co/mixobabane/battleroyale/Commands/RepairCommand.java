package za.co.mixobabane.battleroyale.Commands;


import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.*;
import za.co.mixobabane.battleroyale.Commands.Commands;

public class RepairCommand extends Commands{
    public RepairCommand(){
        super("repair");
    }

    @Override
    public boolean runCommand(Avatar avatar) {

        JSONObject reload = new JSONObject();
        avatar.setStatus(Status.REPAIR);
        avatar.setMessage(reload.put("message:","Repairing shields, do not interrupt."));

        return true;
    }
}