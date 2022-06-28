package BuisnessLayer.Tile;

import CallBacks.SwapPlacesCaller;

public class Empty extends Tile {

    public Empty(Position position, SwapPlacesCaller sp){ super(position, '.', sp); }
    public Empty(){super('.');}

    public void move (Tile tile){
        tile.moveT(this);
    }

    public void moveT(Wall w){/*not supposed to happen*/}
    public void moveT(Empty e){/*not supposed to happen*/}
    public void moveT(Unit u){swapPlaces(u);}
}
