package za.co.mixobabane.battleroyale.Commands;


import za.co.mixobabane.battleroyale.Avatar.Avatar;

public class QuitCommand extends Commands{
    public QuitCommand(){
        super("quit");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        return true;
    }
}
