package za.co.mixobabane.battleroyale.Avatar;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Commands.Commands;
import za.co.mixobabane.battleroyale.World.Obstacles;
import za.co.mixobabane.battleroyale.World.World;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Avatar {
    private final Position upperConstraint = new Position(-200,100);
    private final Position lowerConstraint = new Position(100,-200);
    public static final Position Start = new
            Position(0,0);
    private World world = new World();

    private String robotName;
    private String robotMake;
    private String command;
    private JSONArray arguments;
    private Position position;
    public Direction currentDirection;
    private String message;
    private String result;
    private String state;

    private int maxShields;
    private int maxShots;
    public int currentShields;
    public int shotsRemaining;
    public Status status;
    private ArrayList<Avatar> avatarList;

    public Avatar(String robotName, JSONArray argsList, ArrayList<Avatar> avatarList){
        this.robotName = robotName;
        this.arguments = argsList;
        this.position = new
                Position(ThreadLocalRandom.current().nextInt(-10, 10 + 1),
                ThreadLocalRandom.current().nextInt(-25, 25 + 1));
        this.currentDirection = Direction.NORTH;
        this.status = Status.NORMAL;
        this.avatarList = avatarList;
    }
    public Avatar(String robotName){

        this.position = new
                Position(0,0);
        this.currentDirection = Direction.NORTH;
        this.robotName = robotName;
    }
    public Avatar(String robotName, JSONArray argsList){
        this.robotName = robotName;
        this.arguments = argsList;
        this.position = new Position(0,0);

        this.currentDirection = Direction.NORTH;
        this.status = Status.NORMAL;
        this.avatarList = avatarList;
    }

    public int getVisibility(){
        return world.getVisibility();
    }

    public int getReloadSeconds(){
        return world.getReloadSeconds();
    }

    public int getRepairSeconds(){
        return world.getRepairSeconds();
    }

    public int getShields(){
        return this.maxShields;
    }

    public void setMaxShields(int maxShields){
        this.maxShields = maxShields;
    }

    public int getShots(){
        return this.maxShots;
    }

    public void setMaxShots(int maxShots){
        this.maxShots = maxShots;
    }

    public void setRobotMake(String make){
        this.robotMake = make;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Direction getCurrentDirection() {

        return this.currentDirection;
    }
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public Position getPosition() {
        return this.position;
    }


  /**
   * --------------------------------------------------------------------------
   * -------------------------------------------------------------------------
   * =========================================================================
   * ==========================================================================**/

    public void setMessage(String message) {
        this.message = message;

    }
    public void setState(String state){
        this.state = state;
    }
    public String getMessage(){
        return this.message;
    }
    public void setResult(String result) {
        this.result = result;}

    public String getResult() {
        return result;
    }

    public String getRobotName(){
        return robotName;
    }

    public ArrayList<JSONObject> getRobots(){
        JSONObject robotInfo = new JSONObject();
        ArrayList<JSONObject> robots = new ArrayList<>();

        ArrayList<Avatar> avatarList = this.avatarList;
        System.out.println(avatarList.toString());

        robotInfo.put("Avatar name",this.getRobotName());
        robotInfo.put("position",this.getPosition());
        robotInfo.put("direction",this.getCurrentDirection());
        robots.add(robotInfo);

        robots.add(robotInfo);
        return robots;
    }


    public ArrayList<Obstacles> getObstacles(){
        return world.getObstacles();
    }

    public boolean handleCommand(Commands commands){
        if(commands.runCommand(this)){
            setResult("OK");
        }else {
            setResult("ERROR");
        }
        return true;
    }
    public String getState(){
        JSONObject state = new JSONObject();
        state.put("position",this.getPosition());
        state.put("direction",this.getCurrentDirection());
        state.put("shields",this.getShields());
        state.put("shots",this.getShots());
        state.put("status",this.getStatus());
        return state.toString();
    }
//    public JSONObject getUserInput() throws IOException {
//        Scanner sc = new Scanner(System.in);
//        JSONObject commandString = new JSONObject();
//        System.out.println("Please enter robot name: ");
//        this.robotName = sc.nextLine();
//        commandString.put("name",robotName);
//        System.out.println("Enter command to run: ");
//        this.command = sc.nextLine();
//        commandString.put("command",command);
//        System.out.println("Command arguments: ");
//        this.arguments = new JSONArray(sc.nextLine());
//        commandString.put("arguments",arguments);
//        return commandString;
//    }

    public JSONObject setUserOutput() throws IOException{
        JSONObject outputString = new JSONObject();
        outputString.put("result",getResult());
        outputString.put("data",getMessage());
        outputString.put("state",getState());
        return outputString;
    }

    public UpdateResponse updatePosition (int nrSteps) {
        int newX = this.position.x();
        int newY = this.position.y();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        } else if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }
        if (Direction.EAST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        } else if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(upperConstraint, lowerConstraint)) {
            this.position = newPosition;
            return UpdateResponse.OK;
        }
        return UpdateResponse.OUTSIDE_WORLD;
    }

}
