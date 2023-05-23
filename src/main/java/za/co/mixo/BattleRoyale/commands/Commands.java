package za.co.mixo.BattleRoyale.commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.movements.*;

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

    /**
     * This method executes the command that correspond to the user's request
     * and either returns a true is successful, else returns a false
     * @param player
     * @return boolean
     */
    public abstract boolean runCommand(Player player);

    /**
     * returns name of current command
     * @return Command name
     */
    public String getCommandName(){
        return this.commandName;
    }
    /**
     * returns arguments array of current command
     * @return JSONArray arguments
     */
    public JSONArray getCommandArgs(){
        return this.commandArgs;
    }

    /**
     * Creates command from user's request message
     * @param commands
     * @return Command to run
     */
    public static Commands makeCommand(JSONObject commands) {
        String command = (String) commands.get("command");
        JSONArray arguments = commands.getJSONArray("arguments");
        switch (command.toLowerCase()) {
            case "help":
                return new HelpCommand();
            case "reload":
                return new ReloadCommand();
            case "repair":
                return new RepairCommand();
            case "look":
                return new LookCommand();
            case "orientation":
                return new OrientationCommand();
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
