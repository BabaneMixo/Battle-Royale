package za.co.mixobabane.battleroyale.Commands;


import za.co.mixobabane.battleroyale.Avatar.*;

public class ReloadCommand  extends Commands{
    public ReloadCommand(){
        super("reload");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        int waitSeconds = avatar.getReloadSeconds();
        avatar.setStatus(Status.REPAIR);
        return true;
    }
}
