package za.co.mixo.BattleRoyale.world;

import za.co.mixo.BattleRoyale.Player;

import java.util.ArrayList;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    Random rand = new Random();

    private final int visibility = 10;
    private ArrayList<Obstacles> obstacles = new ArrayList<>();
    private int reloadSeconds = 4;
    private int repairSeconds = 4;
    private ArrayList<Player> playerList = new ArrayList<>();

    public World(){}
    public World(ArrayList<Player> playerList){
        this.playerList = playerList;
    }


    public int getVisibility(){
        return this.visibility;
    }

    public int getReloadSeconds() {
        return this.reloadSeconds;
    }

    public int getRepairSeconds() {
        return this.repairSeconds;
    }

    public void addRobotList(ArrayList<Player> players){
        this.playerList = players;
    }

    public ArrayList<Player> getRobots(){
        return this.playerList;
    }

    /**
     * creates a list of randomly placed obstacles
     * @return ArrayList<Obstacles>
     */
    public ArrayList<Obstacles> getObstacles() {
        int number = rand.nextInt(1000);

        for (int i =0; i<=number; i++ ) {
            int x = ThreadLocalRandom.current().nextInt(-10, 10+ 1);
            int y = ThreadLocalRandom.current().nextInt(-20, 20 + 1);
            Obstacles obstacle = new Obstacles(x, y);
            obstacles.add(obstacle);
        }
        return obstacles;
    }

    /**
     * shows all obstacles' positions in the world
     */

    public void showObstacles() {

        List<Obstacles> obstaclesList = getObstacles();
        if (obstaclesList.size()>0) {
            System.out.println("There are some obstacles:");
        }

        for(Obstacles obstacle : obstaclesList){
            System.out.println("- At position "+obstacle.getX()+","+
                    obstacle.getY()+" (to "+Integer.valueOf(obstacle.getX()+5) +
                    ","+Integer.valueOf(obstacle.getY()+5) + ")");

        }
    }
}
