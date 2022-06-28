package BuisnessLayer.Tile;

import CallBacks.SwapPlacesCaller;

public class Wall extends Tile {

    public Wall(Position position,SwapPlacesCaller sp){super(position, '#', sp);}
    public Wall(){super('#');}

    public void move (Tile tile){
        tile.moveT(this);
    }

    public void moveT(Wall w){/*not supposed to happen*/}
    public void moveT(Empty e){/*not supposed to happen*/}
    public void moveT(Unit u){}
}
