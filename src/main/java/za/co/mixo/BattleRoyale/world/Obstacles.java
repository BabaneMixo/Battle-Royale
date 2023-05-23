package za.co.mixo.BattleRoyale.world;

import java.util.Random;

public class Obstacles {
    private int x;
    private int y;
    Random random = new Random();

    public Obstacles(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean blocksPosition(Position position) {
        return (position.isIn(new Position(x, y + (5 - 1)),
                new Position(x + (5 - 1), y))) ;
    }

    public boolean blocksPath(Position a, Position b) {
        if (a.x() == b.x() && this.y >= a.y() ){
            if ((this.x+5 >= a.x() && a.x()>=this.x )){
                return true;}

        } else if (a.y() == b.y() && this.x >= a.x() ){
            if ((this.y+5 >= a.y() && a.y()>=this.y )){
                return true;};
        }

        return false;
    }
}
