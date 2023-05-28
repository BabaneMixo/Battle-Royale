package za.co.mixobabane.battleroyale.Commands;

import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.Avatar.Direction;
import za.co.mixobabane.battleroyale.Avatar.Position;

import java.util.ArrayList;

public class FireCommand extends Commands{
    public FireCommand(){
        super("fire");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        JSONObject fire = new JSONObject();
        int shots = avatar.getShots();

        if(hitOrMiss(avatar,shots)){
            fire.put("message","Hit");
            fire.put("distance",shots);
            fire.put("robot",avatar.getRobotName());
            fire.put("state",new StateCommand());
            avatar.setMessage(fire.toString());
        } else if (!hitOrMiss(avatar,shots)) {
            avatar.setMessage(fire.put("message","Miss").toString());
        }
        return true;
    }

    private boolean hitOrMiss(Avatar robot, int shotDistance){
        Position robotPosition = robot.getPosition();
        if(robot.currentDirection == Direction.NORTH){
            return hitsARobot(robotPosition.x(), robotPosition.y() + shotDistance);
        }
        else if(robot.currentDirection == Direction.SOUTH){
            return hitsARobot(robotPosition.x(), robotPosition.y() - shotDistance);
        }
        else if(robot.currentDirection == Direction.EAST){
            return hitsARobot(robotPosition.x() + shotDistance, robotPosition.y());
        }
        else if(robot.currentDirection == Direction.WEST){
            return hitsARobot(robotPosition.x() - shotDistance, robotPosition.y());
        }
        return false;
    }

    private boolean hitsARobot(int x,int y){
        ArrayList<Position> robotPositions = getRobotsPositions();
        return robotPositions.contains(new Position(x, y));
    }

    private ArrayList<Position> getRobotsPositions(){
        return null;
    }


}
