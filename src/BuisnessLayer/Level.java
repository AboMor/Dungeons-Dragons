package BuisnessLayer;

import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Player.Player;
import BuisnessLayer.Tile.Empty;
import BuisnessLayer.Tile.Position;
import BuisnessLayer.Tile.Tile;
import UI.CLI;

import java.util.LinkedList;
import java.util.List;

public class Level{

    // ----------------------------------- fields ----------------------------------------------------------------------
    private Tile[][] boardGame;
    private Player player;
    private List<Enemy> enemyList;
    private CLI cli;

    // ----------------------------------- constructors ----------------------------------------------------------------------
    public Level(Tile[][] board, List<Enemy> enemies, Player player, CLI cli){
        this.boardGame=board;
        this.enemyList=enemies;
        this.player=player;
        this. cli = cli;
    }


    // ----------------------------------- methods ----------------------------------------------------------------------
    public void play(){
        while (!enemyList.isEmpty()) {
            tick();
            if (player.isDead()) break;
        }
        if (!player.isDead())
            cli.nextLevel(boardGame);
        else cli.showBoard(boardGame,player);
    }

    // player and each enemy plays
    public void tick(){
        cli.showBoard(boardGame, player);
        player.tick((Position p, int range) -> range(p, range));
        for (Enemy e: enemyList)
            e.tick(player);
    }

    //return all the enemies within range
    public List<Enemy> range(Position pos, int range){
        List<Enemy> ans= new LinkedList<>();
        for (Enemy e: enemyList) {
            if (e.getPosition().isInRange(pos, range))
                ans.add(e);
        }
        return ans;
    }

    // returns the tile in this cordinates
    public Tile getTile(int x, int y){
        return boardGame[y][x];
    }

    public void setBoardGame(Tile[][] boardGame) {
        this.boardGame = boardGame;
    }

    public void setEnemyList(List<Enemy> enemies) {
        this.enemyList=enemies;
    }

    public void SwapPlaces(Tile a, Tile b){
        Position posA = a.getPosition();
        Position posB =b.getPosition();
        boardGame[posA.getY()][posA.getX()] = b;
        boardGame[posB.getY()][posB.getX()]=a;
    }

    public void UponEnemyDeath(Enemy e){
        Position Epos = e.getPosition();
        enemyList.remove(e);
        boardGame[Epos.getY()][Epos.getX()]= new Empty(Epos, (Tile a, Tile b)-> SwapPlaces(a,b));
    }

    public void Call(String s){
        cli.print(s);
    }


}
