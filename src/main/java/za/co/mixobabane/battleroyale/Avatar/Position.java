package za.co.mixobabane.battleroyale.Avatar;

public record Position(int x, int y) {

    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.y();
        boolean withinBottom = this.y >= bottomRight.y();
        boolean withinLeft = this.x >= topLeft.x();
        boolean withinRight = this.x <= bottomRight.x();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

}
