package za.co.mixobabane.battleroyale.Commands;


import za.co.mixobabane.battleroyale.Avatar.*;
import za.co.mixobabane.battleroyale.Commands.Commands;

public class RepairCommand  extends Commands {
    public RepairCommand(){
        super("reload");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        int waitSeconds = avatar.getReloadSeconds();
        avatar.setStatus(Status.REPAIR);
        return true;
    }
}
