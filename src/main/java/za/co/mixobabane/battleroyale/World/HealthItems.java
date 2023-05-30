package za.co.mixobabane.battleroyale.World;

public class HealthItems implements MedKit{
    private int x ;
    private int y ;

    public HealthItems(int x, int y){
        this.x = x;
        this.y = y;
    }
    /**
     * Get X coordinate of bottom left corner of med kit.
     *
     * @return x coordinate
     */
    @Override
    public int getBottomLeftX() {
        return this.x;
    }

    /**
     * Get Y coordinate of bottom left corner of med kit.
     *
     * @return y coordinate
     */
    @Override
    public int getBottomLeftY() {
        return this.y;
    }

    /**
     * Gets the side of  med kit (assuming square med kit)
     *
     * @return the length of one side in nr of steps
     */
    @Override
    public int getSize() {
        return 2;
    }

}
