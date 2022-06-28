package BuisnessLayer.Tile;

public class Position {
    // ----------------------------------- fields ----------------------------------------------------------------------
    private int x;
    private int y;
    // ----------------------------------- constructor ----------------------------------------------------------------------
    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    // ----------------------------------- methods ----------------------------------------------------------------------
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void move(int dx, int dy){
        setX(getX()+dx);
        setY(getY()+dy);
    }

    public boolean isInRange(Position playerPos, int visionRange) {
        return (Math.sqrt(Math.abs(playerPos.getX()-getX())^2+(playerPos.getY()-getY())^2))<=visionRange;
    }
}
