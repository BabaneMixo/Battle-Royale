package za.co.mixobabane.battleroyale.Commands;

import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.Avatar.Direction;
import za.co.mixobabane.battleroyale.Avatar.Position;
import za.co.mixobabane.battleroyale.Avatar.Status;

import java.util.ArrayList;
public class FireCommand extends Commands{
    public FireCommand(){
        super("fire");
    }

    @Override
    public boolean runCommand(Avatar avatar) {
        JSONObject fire = new JSONObject();
        Position position = avatar.getPosition();

        int shotDistance = avatar.getSteps();
        ArrayList<Avatar> otherAvatars = avatar.getRobotList();

        boolean hit = false;
        String name = "";
        JSONObject state = new JSONObject();
        int distance = 0;

        for(Avatar otherAvatar: otherAvatars){
            if(!avatar.getRobotName().equals(otherAvatar.getRobotName()) && avatar.getShotsRemaining() > 0){
                Position robotPosition = otherAvatar.getPosition();

                if(avatar.currentDirection == Direction.NORTH){
                    if(robotPosition.isIn(new Position(position.x(), position.y() +shotDistance)
                            , position) && otherAvatar.getStatus()!= Status.DEAD){
                        hit = true;
                        name = otherAvatar.getRobotName();
                        otherAvatar.setCurrentShields(otherAvatar.getCurrentShields()-1);
                        state = otherAvatar.getState();
                        distance = robotPosition.y()-position.y() ;

                    }
                }
                else if(avatar.currentDirection == Direction.SOUTH){
                    if(robotPosition.isIn(position
                            , new Position(position.x(), position.y()-shotDistance)) && otherAvatar.getStatus()!= Status.DEAD){
                        hit = true;
                        name = otherAvatar.getRobotName();
                        otherAvatar.setCurrentShields(otherAvatar.getCurrentShields()-1);
                        state = otherAvatar.getState();
                        distance = position.y() - robotPosition.y();

                    }
                }
                else if(avatar.currentDirection == Direction.EAST){
                    if(robotPosition.isIn(position
                            , new Position(position.x()+shotDistance, position.y())) && otherAvatar.getStatus()!= Status.DEAD ){
                        hit = true;
                        name = otherAvatar.getRobotName();
                        otherAvatar.setCurrentShields(otherAvatar.getCurrentShields()-1);
                        state = otherAvatar.getState();
                        distance = robotPosition.x() - position.x() ;

                    }
                }
                else if(avatar.currentDirection == Direction.WEST){
                    if(robotPosition.isIn(new Position(position.x()-shotDistance , position.y())
                            , position) && otherAvatar.getStatus()!= Status.DEAD){
                        hit = true;
                        name = otherAvatar.getRobotName();
                        otherAvatar.setCurrentShields(otherAvatar.getCurrentShields()-1);
                        state = otherAvatar.getState();
                        distance = position.x()-robotPosition.x() ;

                    }
                }
            }else if(avatar.getShotsRemaining() == 0){
                avatar.setMessage(fire.put("message","You're out of ammo.. reload shots!"));
                return false;
            }
        }

        if(hit){
            fire.put("message","Hit");
            fire.put("distance",distance);
            fire.put("avatar",name);
            fire.put("state",state);
            avatar.setMessage(fire);
        } else {
            avatar.setMessage(fire.put("message","Miss"));
        }
        avatar.setShotsRemaining(avatar.getShotsRemaining()-1);
        return true;
    }
}