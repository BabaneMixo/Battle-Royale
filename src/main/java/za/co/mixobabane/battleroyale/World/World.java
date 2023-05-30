package za.co.mixobabane.battleroyale.World;

import za.co.mixobabane.battleroyale.Avatar.Avatar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    Random rand = new Random();

    private final int visibility = 5;
    private ArrayList<Obstacles> obstacles = new ArrayList<>();
    private ArrayList<HealthItems> medKits = new ArrayList<>();
    private int reloadSeconds = 4;
    private int repairSeconds = 4;
    private ArrayList<Avatar> avatarList = new ArrayList<>();

    public World(){}
    public World(ArrayList<Avatar> robotList){
        this.avatarList = robotList;
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

    public void addRobotList(ArrayList<Avatar> robots){
        this.avatarList = robots;
    }

    public ArrayList<Avatar> getRobots(){
        return this.avatarList;
    }

    public ArrayList<Obstacles> getObstacles() {
        int number = rand.nextInt(100);

        for (int i =0; i<=number; i++ ) {
            int x = ThreadLocalRandom.current().nextInt(-50, 50+ 1);
            int y = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
            Obstacles obstacle = new Obstacles(x, y);
            obstacles.add(obstacle);
        }
        return obstacles;
    }

    public ArrayList<HealthItems> generateMedKits(){
        int number = rand.nextInt(10);
        for (int i =0; i<=number; i++ ) {
            int x = ThreadLocalRandom.current().nextInt(-50, 50+ 1);
            int y = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
            HealthItems medKit = new HealthItems(x,y);
            medKits.add(medKit);}
        return medKits;
    }


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
