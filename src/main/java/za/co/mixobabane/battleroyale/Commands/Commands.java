package za.co.mixobabane.battleroyale.Commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.movements.BackCommand;
import za.co.mixobabane.battleroyale.movements.ForwardCommand;
import za.co.mixobabane.battleroyale.movements.TurnCommand;

public abstract class Commands {
    String commandName;
    JSONArray commandArgs;

    public Commands(String name){
        this.commandName = name;
    }
    public Commands(String name,JSONArray arguments){
        this.commandName = name;
        this.commandArgs = arguments;
    }

    public abstract boolean runCommand(Avatar avatar);

    public String getCommandName(){
        return this.commandName;
    }

    public JSONArray getCommandArgs(){
        return this.commandArgs;
    }

    public static Object makeCommand(JSONObject commands) {
        String command = (String) commands.get("command");
        JSONArray arguments = commands.getJSONArray("arguments");
        switch (command.toLowerCase()) {
            case "quit":
                return new QuitCommand();
            case "reload":
                return new ReloadCommand();
            case "repair":
                return new RepairCommand();
            case "look":
                return new LookCommand();
            case "fire":
                return new FireCommand();
            case "state":
                return new StateCommand();
            case "launch":
                return new LaunchCommand(arguments);
            case "back":
                return new BackCommand(arguments);
            case "forward":
                return new ForwardCommand(arguments);
            case "turn":
                return new TurnCommand(arguments);
            default:
                throw new IllegalArgumentException("Unsupported command");
        }
    }
}
