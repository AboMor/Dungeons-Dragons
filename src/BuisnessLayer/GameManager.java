package BuisnessLayer;

import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Enemy.Monster;
import BuisnessLayer.Enemy.Trap;
import BuisnessLayer.Player.Mage;
import BuisnessLayer.Player.Player;
import BuisnessLayer.Player.Rogue;
import BuisnessLayer.Player.Warrior;
import BuisnessLayer.Tile.Empty;
import BuisnessLayer.Tile.Position;
import BuisnessLayer.Tile.Tile;
import BuisnessLayer.Tile.Wall;
import UI.CLI;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class GameManager {

    public static void main(String[] args) {
        File levels = new File(args[0]);
        List<File> levelFiles = null;
        if (levels.exists())
            levelFiles = Arrays.stream(levels.listFiles())
                    .filter(file -> file.getName().matches("^level\\d+\\.txt$"))
                    .filter(File::isFile)
                    .collect(Collectors.toList());
        new GameManager(levelFiles);
    }

    Player player;
    CLI cli = new CLI();
    static int PLAYERSNUM = 6;
        List<File> levelFiles;

    public GameManager(List<File> levelFiles){
        this. levelFiles= levelFiles;
        player = choosePlayer();
        startGame();
    }

    public Player choosePlayer(){
        Player[] players = getPlayersList();
        cli.showPlayerList(players);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        Player ans = null;
        while (ans==null) {
            try {
                cli.PlayerPicked(players[Integer.parseInt(input)]);
                ans = players[Integer.parseInt(input)];
            } catch (Exception e) {
                cli.print("you should enter a number between 1 and " + PLAYERSNUM);
                input = sc.nextLine();
            }
        }
        return ans;
    }

    public Player[] getPlayersList(){
        Player[] ans = new Player[PLAYERSNUM+1];
        ans[1]= new Warrior("Jon Snow", 300, 4, 30, 3);
        ans[2]= new Warrior("The Hound", 400, 6, 20, 5);
        ans[3] = new Mage("Melisandre", 100, 1,5,300,30,15,5,6);
        ans[4] = new Mage("Thoros Of Myr", 250, 4,25,150,20,20,3,4);
        ans[5] = new Rogue("Arya Stark", 150,2,40,20);
        ans[6] = new Rogue("Bronn", 250,3,35,50);
        return ans;
    }

    public Map<Character, Supplier<Enemy>> getEnemyFactory(){
        List<Supplier<Enemy>> supplierList= Arrays.asList(
                () -> new Monster("Lannister Solider", 's', 80, 3, 8, 25, 3),
                () -> new Monster("Lannister Knight", 'k', 200, 8, 14, 50, 4),
                () -> new Monster("Queen’s Guard", 'q', 400, 15, 20, 100, 5),
                () -> new Monster("Wright", 'z', 600, 15, 30, 100, 3),
                () -> new Monster("Bear-Wright", 'b', 1000, 30, 75, 250, 4),
                () -> new Monster("Giant-Wright", 'g', 1500, 40, 100, 500, 5),
                () -> new Monster("White Walker", 'w', 2000, 50, 150, 1000, 6),
                () -> new Monster("The Mountain", 'M', 1000, 25, 60, 500, 6),
                () -> new Monster("Queen Cersei", 'C', 100, 10, 10, 1000, 1),
                () -> new Monster("Night’s King", 'K', 5000, 150, 300, 5000, 8),
                () -> new Trap("Bonus Trap", 'B', 1, 1, 1, 250, 5, 5),
                () -> new Trap("Queen’s Trap", 'Q', 250, 10, 50, 100, 3, 5),
                () -> new Trap("Death Trap", 'D', 500, 20, 100, 250, 1, 10)
        );
        return supplierList.stream().collect(Collectors.toMap(s -> s.get().toChar(), Function.identity()));
    }


    public void startGame(){
        for (File level: levelFiles) {
            UploadLevel(level);
            if (player.isDead()) break;
        }
        if (player.isDead()) cli.EndGame();
        else cli.WinGame();
    }

    public void UploadLevel(File data){
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(data));
            BufferedReader br2 = new BufferedReader(new FileReader(data));
            Map<Character, Supplier<Enemy>> enemyFactory = getEnemyFactory();
            int length =0;
            String line;
            while ((line = br1.readLine()) != null) { length++;}
            Tile[][] board = new Tile[length][];
            List<Enemy> enemies = new LinkedList<>();
            Level level = new Level(board, enemies, player, cli);
            int l = 0;//represnt the line number
            while ((line = br2.readLine()) != null) {
                board[l]=new Tile[line.length()];
                for (int c = 0; c<line.length(); c++){
                    char ch = line.charAt(c);
                    switch (ch) {
                        case '#': board[l][c] = new Wall(); break;
                        case '.': board[l][c] = new Empty(); break;
                        case '@':
                            board[l][c] = player;
                            player.UnitInit((int x, int y)-> level.getTile(x,y), (String s)->level.Call(s));
                            break;
                        default:
                            Enemy enemy = enemyFactory.get(ch).get();
                            board[l][c] = enemy;
                            enemy.UnitInit((int x, int y)-> level.getTile(x,y), (String s)->level.Call(s));
                            enemies.add(enemy);
                            enemy.setEdcb((Enemy e)->level.UponEnemyDeath(e));
                    }
                    board[l][c].init(new Position(c,l), (Tile a, Tile b)-> level.SwapPlaces(a,b));
                }
                l++;
            }
            level.setBoardGame(board);
            level.setEnemyList(enemies);
            level.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
