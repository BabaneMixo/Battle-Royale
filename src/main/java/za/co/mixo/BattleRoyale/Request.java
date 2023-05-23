package za.co.mixo.BattleRoyale;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixo.BattleRoyale.commands.Commands;

public class Request {
    public Request(String username, String command, JSONArray arguments){

    }

    /** This method returns a json string of the request from the client/robot.
    @param command command from robot.
    @param name name of the robot.
    @return Json string.
    * */
    public String toJsonString(String name, String command, JSONArray arguments){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name.toLowerCase());
        jsonObject.put("command", command.toLowerCase());
        jsonObject.put("arguments" ,arguments);

        return jsonObject.toString();
    }
}
