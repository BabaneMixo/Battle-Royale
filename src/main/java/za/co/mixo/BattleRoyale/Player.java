package za.co.mixo.BattleRoyale;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixo.BattleRoyale.commands.Commands;
import za.co.mixo.BattleRoyale.world.World;
import za.co.mixo.BattleRoyale.world.Obstacles;
import za.co.mixo.BattleRoyale.world.Position;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
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
    private JSONObject message;
    private String result;
    private String state;
    public int steps;
    private int maxShields;
    private int maxShots;
    public int currentShields;
    public int shotsRemaining;
    public Status status;
    private ArrayList<Player> playerList;

    public Player(String robotName, JSONArray argsList, ArrayList<Player> playerList){
        this.robotName = robotName;
        this.arguments = argsList;
        this.position = new
                Position(ThreadLocalRandom.current().nextInt(-10, 10 + 1),
                ThreadLocalRandom.current().nextInt(-25, 25 + 1));
        this.currentDirection = Direction.NORTH;
        this.status = Status.NORMAL;
        this.playerList = playerList;
    }
    public Player(String robotName){

        this.position = new
                Position(0,0);
        this.currentDirection = Direction.NORTH;
        this.robotName = robotName;
    }
    public Player(String robotName, JSONArray argsList){
        this.robotName = robotName;
        this.arguments = argsList;
        this.position = new Position(0,0);

        this.currentDirection = Direction.NORTH;
        this.status = Status.NORMAL;
    }
    public int getSteps(){
        return this.steps;
    }
    public  void setShotsRemaining(int shots){
        this.shotsRemaining =shots;
    }
    public int getShotsRemaining(){
        return this.shotsRemaining;
    }
    public  void setDistance(int steps){
        this.steps =steps;
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


    public int getCurrentShields(){
        return this.currentShields;
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

    public void setMessage(JSONObject message) {
        this.message = message;

    }

    public void setState(String state){
        this.state = state;
    }

    public JSONObject getMessage(){
        return this.message;
    }

    public void setResult(String result) {
        this.result = result;}

    public String getResult() {
        return result;
    }

    public ArrayList<Obstacles> getObstacles(){
        return world.getObstacles();
    }

    public String getRobotName(){
        return robotName;
    }

    public ArrayList<Player> getRobotList(){
        ArrayList<Player> playerList = this.playerList;
        return playerList;
    }

    /**
     * This method sets the current shield remaining on the robot and sets robot
     * status to DEAD when the remaining shields are equal to zero*/
    public void setCurrentShields(int currentShields) {
        this.currentShields = currentShields;
        if(this.currentShields == 0){
            setStatus(Status.DEAD);
        }
    }


    /**
     * This method gets the robot's information and put them into a JSONObject
     * @return JSONObject of robot info
     */
    public ArrayList<JSONObject> getRobots(){
        JSONObject robotInfo = new JSONObject();
        ArrayList<JSONObject> robots = new ArrayList<>();
        ArrayList<Player> playerList = this.playerList;
        robotInfo.put("Robot name",this.getRobotName());
        robotInfo.put("position",this.getPosition());
        robotInfo.put("direction",this.getCurrentDirection());
        robots.add(robotInfo);
        robots.add(robotInfo);
        return robots;
    }


    /**
     * sets the results to 'OK' message when a command runs successfully, otherwise sets message to 'ERROR'.
     * @param commands
     * @return boolean
     */
    public boolean handleCommand(Commands commands){
        if(commands.runCommand(this)){
            setResult("OK");
        }else {
            setResult("ERROR");
        }
        return true;
    }

    /**
     * This method collects data of the robot and returns it as state of the
     * robot in a JSONObject*/

    public JSONObject getState(){
        JSONObject state = new JSONObject();
        state.put("position",this.getPosition());
        state.put("direction",this.getCurrentDirection());
        state.put("shields",this.getCurrentShields());
        state.put("shots",this.getShotsRemaining());
        state.put("status",this.getStatus());
        return state;
    }

    /**
     * This is a response method, it takes all the information of the robot from the world
     * and combines its data and state into a JSONObject */

    public String setUserOutput() throws IOException{
        JSONObject outputString = new JSONObject();
        JSONObject state = getState();
        outputString.put("result",getResult());
        outputString.put("data",getMessage());
        outputString.put("state",state);
        return outputString.toString(4);
    }

    /**
     * This method updates a robot's position by checking its current position
     * and updates the x/y coordinate with the given nrSteps
     * @param nrSteps
     * @return UpdateResponse
     */
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


