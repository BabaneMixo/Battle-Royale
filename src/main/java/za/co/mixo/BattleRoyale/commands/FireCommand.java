package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Direction;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.Status;
import za.co.mixo.BattleRoyale.world.Position;

import java.util.ArrayList;

public class FireCommand extends Commands{
    public FireCommand(){
        super("fire");
    }

    @Override
    public boolean runCommand(Player player) {
        JSONObject fire = new JSONObject();
        Position position = player.getPosition();

        int shotDistance = player.getSteps();
        ArrayList<Player> otherPlayers = player.getRobotList();

        boolean hit = false;
        String name = "";
        JSONObject state = new JSONObject();
        int distance = 0;

        for(Player otherPlayer : otherPlayers){
            if(!player.getRobotName().equals(otherPlayer.getRobotName()) && player.getShotsRemaining() > 0){
                Position robotPosition = otherPlayer.getPosition();

                if(player.currentDirection == Direction.NORTH){
                    if(robotPosition.isIn(new Position(position.x(), position.y() +shotDistance)
                            , position) && otherPlayer.getStatus()!= Status.DEAD){
                        hit = true;
                        name = otherPlayer.getRobotName();
                        otherPlayer.setCurrentShields(otherPlayer.getCurrentShields()-1);
                        state = otherPlayer.getState();
                        distance = robotPosition.y()-position.y() ;

                    }
                }
                else if(player.currentDirection == Direction.SOUTH){
                    if(robotPosition.isIn(position
                            , new Position(position.x(), position.y()-shotDistance)) && otherPlayer.getStatus()!= Status.DEAD){
                        hit = true;
                        name = otherPlayer.getRobotName();
                        otherPlayer.setCurrentShields(otherPlayer.getCurrentShields()-1);
                        state = otherPlayer.getState();
                        distance = position.y() - robotPosition.y();

                    }
                }
                else if(player.currentDirection == Direction.EAST){
                    if(robotPosition.isIn(position
                            , new Position(position.x()+shotDistance, position.y())) && otherPlayer.getStatus()!= Status.DEAD ){
                        hit = true;
                        name = otherPlayer.getRobotName();
                        otherPlayer.setCurrentShields(otherPlayer.getCurrentShields()-1);
                        state = otherPlayer.getState();
                        distance = robotPosition.x() - position.x() ;

                    }
                }
                else if(player.currentDirection == Direction.WEST){
                    if(robotPosition.isIn(new Position(position.x()-shotDistance , position.y())
                            , position) && otherPlayer.getStatus()!= Status.DEAD){
                        hit = true;
                        name = otherPlayer.getRobotName();
                        otherPlayer.setCurrentShields(otherPlayer.getCurrentShields()-1);
                        state = otherPlayer.getState();
                        distance = position.x()-robotPosition.x() ;

                    }
                }
            }else if(player.getShotsRemaining() == 0){
                player.setMessage(fire.put("message","You're out of ammo.. reload shots!"));
                return false;
            }
        }

        if(hit){
            fire.put("message","Hit");
            fire.put("distance",distance);
            fire.put("robot",name);
            fire.put("state",state);
            player.setMessage(fire);
        } else {
            player.setMessage(fire.put("message","Miss"));
        }
        player.setShotsRemaining(player.getShotsRemaining()-1);
        return true;
    }




}
