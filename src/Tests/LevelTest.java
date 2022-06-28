package Tests;
import BuisnessLayer.*;
import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Enemy.Monster;
import BuisnessLayer.Enemy.Trap;
import BuisnessLayer.Player.Mage;
import BuisnessLayer.Player.Player;
import BuisnessLayer.Player.Warrior;
import static org.junit.Assert.*;

import BuisnessLayer.Tile.Empty;
import BuisnessLayer.Tile.Position;
import BuisnessLayer.Tile.Tile;
import BuisnessLayer.Tile.Wall;
import CallBacks.AreaVision;
import CallBacks.SwapPlacesCaller;
import UI.CLI;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class LevelTest<EmptyTile> {
    CLI cli;
    Tile[][] board;
    List<Enemy> enemeis;
    Level first;
    Wall wall1;
    Wall wall2;
    Wall wall3;
    Wall wall4;
    Empty empty1;
    Empty empty2;
    Empty empty3;
    Empty empty4;
    Monster monster1;
    Trap trap1;
    Player player1;
    SwapPlacesCaller sp;
    @org.junit.jupiter.api.BeforeEach
    void Setup(){
        cli=new CLI();
        wall1 = new Wall(new Position(0,0),sp);
        wall2= new Wall(new Position(1,0),sp);
        wall3 = new Wall(new Position(2,0),sp);
        wall4 = new Wall(new Position(3,0),sp);
        empty1=new Empty(new Position(0,1),sp);
        empty2 = new Empty(new Position(1,1),sp);
        empty3 = new Empty(new Position(2,1),sp);
        monster1 = new Monster("Lannister Solider", 's', 80, 3, 8, 25, 3);
        trap1 = new Trap("Death Trap", 'D', 500, 20, 100, 250, 1, 10);
        player1= new Mage("Melisandre", 100, 1,5,300,30,15,5,6);
        player1.setPosition(new Position(2,2));
        monster1.setPosition(new Position(1,2));
        trap1.setPosition(new Position(0,2));
        enemeis = new LinkedList<Enemy>();
        enemeis.add(trap1);
        enemeis.add(monster1);
        board = new Tile[3][3];
        board[0][0]=wall1;
        board[0][1]=wall2;
        board[0][2]=wall3;
        board[1][0]= empty1;
        board[1][1]= empty2;
        board[1][2]= empty3;
        board[2][0]= trap1;
        board[2][1]=monster1;
        board[2][2]= player1;
        first = new Level(board,enemeis,player1,cli);
        monster1.setEdcb((Enemy e)->first.UponEnemyDeath(e));
        trap1.setEdcb((Enemy e)->first.UponEnemyDeath(e));
    }

    @Test
    public void enemy(){
        Setup();
        cli.showBoard(board,player1);
        monster1.death(player1);//test monster death
        assertEquals('.',board[2][1].toChar());
        Setup();
        monster1.setHealthAmount(-20);//test decreasing health
        assertEquals(60,monster1.getHealthAmount());
        int x=empty2.getPosition().getX();
        int y=empty2.getPosition().getY();
        monster1.swapPosition(empty2);//test swapping positions
        assertEquals(x,monster1.getPosition().getX());
        assertEquals(y,monster1.getPosition().getY());
        x=monster1.getPosition().getX();
        y=monster1.getPosition().getY();
        monster1.move(wall1);//suppose to do nothing-same position
        assertEquals(x,monster1.getPosition().getX());
        assertEquals(y,monster1.getPosition().getY());
        System.out.println(monster1.describe());
        player1.attack(monster1);
        System.out.println(monster1.describe());//testing enemy attack
        monster1.attack(player1);
        System.out.println(player1.describe());


    }
    @Test
    public void player() {
        Setup();
        cli.showBoard(board, player1);
        player1.LevelingUp();
        cli.showBoard(board, player1);
        player1.setHealthAmount(-30);//test decreasing health
        assertEquals(270, player1.getHealthAmount());
        int x = empty3.getPosition().getX();
        int y = empty3.getPosition().getY();
        player1.swapPosition(empty3);//test swapping positions
        assertEquals(x, player1.getPosition().getX());
        assertEquals(y, player1.getPosition().getY());
        x = player1.getPosition().getX();
        y = player1.getPosition().getY();
        player1.move(wall1);//suppose to do nothing-same position
        assertEquals(x, player1.getPosition().getX());
        assertEquals(y, player1.getPosition().getY());
        System.out.println(monster1.describe());
        player1.attack(monster1);
        System.out.println(monster1.describe());//testing enemy attack
        monster1.attack(player1);
        System.out.println(player1.describe());
        player1.death();
        assertEquals('X', board[2][2].toChar());

    }
    @Test
    public void levelingupMage(){
        Setup();
        player1.LevelingUp();
    }
//    @Test
//    public void playerTick(){
//        Setup();
//        player1.tick((Position p, int range) -> first.range(p, range));
//        assertEquals(76,player1.getCurrentMana());
//        for(int i=0;i<10;i++)
//            player1.tick((Position p, int range) -> first.range(p, range));
//        assertEquals(86,player1.getCurrentMana());
//    }

}
