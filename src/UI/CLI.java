package UI;

import BuisnessLayer.Player.Player;
import BuisnessLayer.Tile.Tile;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Scanner;

public class CLI {

    public void showBoard (Tile[][] board, Player p){
        System.out.flush();
        for (Tile[] line: board) {
            for( Tile t: line){
                System.out.print(t.toChar());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(p.describe());
    }

    public void showPlayerList(Player[] players){
        System.out.println("CHOOSE A PLAYER:");
        for(int i= 1; i<players.length; i++)
            System.out.println(i+ ": " + players[i].describe());
    }

    public void PlayerPicked(Player p){
        System.out.println("You have picked"+ p.getName());
    }

    public void print(String s){
        System.out.println(s);
    }

    public void nextLevel(Tile[][] oldBoard){
        char[][] view = new char[oldBoard.length][];
        for (int i=0; i<oldBoard.length; i++) {
            view[i] = new char[oldBoard[i].length];
            for (int j = 0; j < oldBoard[i].length; j++)
                view[i][j] ='*';
        }
        int middleLine= view.length/2;
        int middleColumn = view[0].length/2-5;
        view[middleLine][middleColumn]='N';
        view[middleLine][middleColumn+1]='E';
        view[middleLine][middleColumn+2]='X';
        view[middleLine][middleColumn+3]='T';
        view[middleLine][middleColumn+4]=' ';
        view[middleLine][middleColumn+5]='L';
        view[middleLine][middleColumn+6]='E';
        view[middleLine][middleColumn+7]='V';
        view[middleLine][middleColumn+8]='E';
        view[middleLine][middleColumn+9]='L';

        for(int i=0; i<view.length; i++) {
            for (int j = 0; j < view[i].length; j++)
                System.out.print(view[i][j]);
            System.out.println();
        }
        System.out.println("PRESS ANY KEY TO CONTINUE");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
    }

    public void EndGame() {
        char[][] view = new char[10][];
        for (int i = 0; i < 10; i++) {
            view[i] = new char[10];
            for (int j = 0; j < 10; j++)
                view[i][j] = '*';
        }
        int middleLine = view.length / 2;
        int middleColumn = view[0].length / 2 - 4;
        view[middleLine][middleColumn] = 'E';
        view[middleLine][middleColumn + 1] = 'N';
        view[middleLine][middleColumn + 2] = 'D';
        view[middleLine][middleColumn + 3] = ' ';
        view[middleLine][middleColumn + 4] = 'G';
        view[middleLine][middleColumn + 5] = 'A';
        view[middleLine][middleColumn + 6] = 'M';
        view[middleLine][middleColumn + 7] = 'E';

        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[i].length; j++)
                System.out.print(view[i][j]);
            System.out.println();
        }
    }
    public void WinGame() {
        char[][] view = new char[20][];
        for (int i = 0; i < 20; i++) {
            view[i] = new char[20];
            for (int j = 0; j <20; j++)
                view[i][j] = '*';
        }
        int middleLine = view.length / 2 + 1;
        int middleColumn = view[0].length / 2 - 4;
        view[middleLine][middleColumn] = 'W';
        view[middleLine][middleColumn + 1] = 'I';
        view[middleLine][middleColumn + 2] = 'N';
        view[middleLine][middleColumn + 3] = ' ';
        view[middleLine][middleColumn + 4] = 'G';
        view[middleLine][middleColumn + 5] = 'A';
        view[middleLine][middleColumn + 6] = 'M';
        view[middleLine][middleColumn + 7] = 'E';

        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[i].length; j++)
                System.out.print(view[i][j]);
            System.out.println();
        }
    }
}
