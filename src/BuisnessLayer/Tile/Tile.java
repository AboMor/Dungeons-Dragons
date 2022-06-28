package BuisnessLayer.Tile;


import CallBacks.SwapPlacesCaller;

public abstract class Tile  {
    // ----------------------------------- fields ----------------------------------------------------------------------
    private char tile;
    private Position position;
    private SwapPlacesCaller swapPlacesCaller;

    // ----------------------------------- constructors ----------------------------------------------------------------------
    public Tile(Position position,char tile, SwapPlacesCaller sp){
        this.position=position;
        this.tile=tile;
        this.swapPlacesCaller=sp;
    }

    public Tile(char tile){
        this.tile=tile;
    }

    public void init(Position p, SwapPlacesCaller sp){
        setPosition(p);
        setSwapPlacesCaller(sp);
    }

    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public Position getPosition(){ return  position;}
    public void setPosition(Position Position){this.position=Position;}
    public char getTile() {
        return tile;
    }
    public void setTile(char tile){
        this.tile=tile;
    }
    public void setSwapPlacesCaller(SwapPlacesCaller sp){this.swapPlacesCaller=sp;}


    //returns the distance between this and tile b
    public double range(Tile b){
        double ans=0;
        double x=this.getPosition().getX()-b.getPosition().getX();
        x=x*x;
        double y=this.getPosition().getY()-b.getPosition().getY();
        y=y*y;
        ans=x+y;
        ans=Math.sqrt(ans);
        return ans;
    }

    //returns the char that represents the tile
    public char toChar () {
        return tile;
    }

    public void swapPlaces(Tile a){
        swapPlacesCaller.SwapPlaces(a, this);
        swapPosition(a);
    }

    //supposed to swap position in the Tile[][] in level and chanch the position in each tile
    public void swapPosition(Tile a){
        int ax= a.getPosition().getX();
        int ay= a.getPosition().getY();
        a.getPosition().setX(this.getPosition().getX());
        a.getPosition().setY(this.getPosition().getY());
        this.getPosition().setX(ax);
        this.getPosition().setY(ay);
    }

    public abstract void move (Tile tile);

    public abstract void moveT(Wall w);

    public abstract void moveT(Empty e);

    public abstract void moveT(Unit u);


}
