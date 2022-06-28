package CallBacks;

import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Tile.Position;

import java.util.List;

public interface AreaVision {
    public List<Enemy> range(Position pos, int range);
}
