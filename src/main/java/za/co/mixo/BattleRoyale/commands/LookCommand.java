package za.co.mixo.BattleRoyale.commands;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Direction;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.world.Obstacles;
import za.co.mixo.BattleRoyale.world.Position;

import java.util.ArrayList;

public class LookCommand extends Commands{

    public LookCommand(){
        super("look");
    }

    @Override
    public boolean runCommand(Player player) {
        int visibility = player.getVisibility();
        Position position = player.getPosition();

        JSONObject lookOutput = new JSONObject();
        ArrayList<JSONObject> blocked = new ArrayList<>();

        ArrayList<Player> playerList = player.getRobotList();
        ArrayList<Obstacles> obstaclesList = player.getObstacles();

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

        for (Player blockingPlayer : playerList) {

            if (!player.getRobotName().equals(blockingPlayer.getRobotName())) {
                Position robotPosition = blockingPlayer.getPosition();

                if (robotPosition.isIn(new Position(position.x(), position.y())
                        , new Position(position.x()+visibility, position.y()))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.EAST);
                    look.put("type", "robot");
                    look.put("steps", position.x() - robotPosition.x());
                    blocked.add(look);
                } else if (robotPosition
                        .isIn(new Position(position.x()-visibility , position.y())
                                , new Position(position.x(), position.y()))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.WEST);
                    look.put("type", "robot");
                    look.put("steps", position.x() + robotPosition.x());
                    blocked.add(look);

                } else if (robotPosition
                        .isIn(new Position(position.x(), position.y() +visibility)
                                , new Position(position.x(), position.y()))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.NORTH);
                    look.put("type", "robot");
                    look.put("steps", robotPosition.y()- position.y());
                    blocked.add(look);
                } else if (robotPosition
                        .isIn(new Position(position.x(), position.y())
                                , new Position(position.x(), position.y()-visibility))) {
                    JSONObject look = new JSONObject();
                    look.put("direction", Direction.SOUTH);
                    look.put("type", "robot");
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
        player.setMessage(lookOutput);
        return true;
    }
}
