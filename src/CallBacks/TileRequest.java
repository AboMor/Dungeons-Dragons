package CallBacks;

import BuisnessLayer.Tile.Tile;

public interface TileRequest {
    Tile request(int x, int y);
}
