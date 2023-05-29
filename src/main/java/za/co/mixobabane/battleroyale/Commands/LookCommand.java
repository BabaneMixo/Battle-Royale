package za.co.mixobabane.battleroyale.Commands;

import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.Avatar.Direction;
import za.co.mixobabane.battleroyale.Avatar.Position;
import za.co.mixobabane.battleroyale.World.Obstacles;

import java.util.ArrayList;


public class LookCommand extends Commands{

    public LookCommand(){
        super("look");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        int visibility = avatar.getVisibility();
        Position position = avatar.getPosition();

        JSONObject lookOutput = new JSONObject();
        ArrayList<JSONObject> blocked = new ArrayList<>();

        ArrayList<Avatar> avatarList = avatar.getRobotList();
        ArrayList<Obstacles> obstaclesList = avatar.getObstacles();

        for (Obstacles obstacle: obstaclesList){
            if (new Position(obstacle.getX(),obstacle.getY())
                    .isIn(new Position(position.x(),position.y()+visibility)
                            ,new Position(position.x(),position.y()))){
                JSONObject look = new JSONObject();
                look.put("direction",Direction.NORTH);
                look.put("type","obstacle");
                look.put("steps",obstacle.getY()- position.y());
                blocked.add(look);}

            else if (new Position(obstacle.getX(),obstacle.getY())
                    .isIn(new Position(position.x(),position.y()-visibility)
                            ,new Position(position.x(),position.y()))){
                JSONObject look = new JSONObject();
                look.put("direction",Direction.SOUTH);
                look.put("type","obstacle");
                look.put("steps",position.y()-obstacle.getY());
                blocked.add(look);}

            else if (new Position(obstacle.getX(),obstacle.getY())
                    .isIn(new Position(position.x()+visibility,position.y())
                            ,new Position(position.x(),position.y()))){
                JSONObject look = new JSONObject();
                look.put("direction",Direction.EAST);
                look.put("type","obstacle");
                look.put("steps",obstacle.getX()- position.x());
                blocked.add(look);}

            else if (new Position(obstacle.getX(),obstacle.getY())
                    .isIn(new Position(position.x()-visibility,position.y())
                            ,new Position(position.x(),position.y()))){
                JSONObject look = new JSONObject();
                look.put("direction",Direction.WEST);
                look.put("type","obstacle");
                look.put("steps",position.x()-obstacle.getX());
                blocked.add(look);
            }
        }

        for (Avatar otherAvatar: avatarList) {

            if (!avatar.getRobotName().equals(otherAvatar.getRobotName())) {
                Position robotPosition = otherAvatar.getPosition();

                if (robotPosition.isIn(new Position(position.x(), position.y())
                        , new Position(position.x()+visibility, position.y()))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.EAST);
                    look.put("type", "avatar");
                    look.put("steps", position.x() - robotPosition.x());
                    blocked.add(look);
                } else if (robotPosition
                        .isIn(new Position(position.x()-visibility , position.y())
                                , new Position(position.x(), position.y()))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.WEST);
                    look.put("type", "avatar");
                    look.put("steps", position.x() + robotPosition.x());
                    blocked.add(look);

                } else if (robotPosition
                        .isIn(new Position(position.x(), position.y() +visibility)
                                , new Position(position.x(), position.y()))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.NORTH);
                    look.put("type", "avatar");
                    look.put("steps", robotPosition.y()- position.y());
                    blocked.add(look);
                } else if (robotPosition
                        .isIn(new Position(position.x(), position.y())
                                , new Position(position.x(), position.y()-visibility))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.SOUTH);
                    look.put("type", "avatar");
                    look.put("steps", position.y() - robotPosition.y());
                    blocked.add(look);
                }
            }
        }

        if(blocked.isEmpty()){
            lookOutput.put("message","There is nothing detected within visibility range.");
        }else {
            lookOutput.put("objects",blocked);
        }
        avatar.setMessage(lookOutput);
        return true;
    }
}