package BuisnessLayer.Player;

import CallBacks.AreaVision;
import BuisnessLayer.Tile.Empty;
import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Tile.Unit;
import BuisnessLayer.Tile.Wall;

import java.util.Scanner;

public abstract class Player extends Unit {
    // ----------------------------------- fields ----------------------------------------------------------------------
    private Integer experience;
    private Integer playerLevel;
    private boolean isDead=false;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Player(String name, Integer healthAmount, Integer defensePoints, Integer attackPoints){
        super(name, '@', healthAmount, defensePoints, attackPoints);
        this.experience=0;
        this.playerLevel=1;
    }

    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public Integer getExperience() { return experience;}
    public void setExperience(int value){
        experience= getExperience()+value;
        if (getExperience()>=50*getPlayerLevel())
            LevelingUp();
    }
    public Integer getPlayerLevel() {return playerLevel; }
    public void setPlayerLevel(int value){playerLevel=getPlayerLevel()+value;}

    public String describe() {
        String s = super.describe();
        while (s.length() < 60)
            s = s + " ";
        s = s + "exp: " + getExperience();
        while (s.length() < 69)
            s = s + " ";
        s = s + "player Level: " + getPlayerLevel();
        return s;
    }

    //in each tick the player recieve an input from the user and acts on it -ether movement or special ability
    public void tick(AreaVision av) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        actionUponInput(input, av);
    }

    //devide into cases depending on the input
    private void actionUponInput(String input, AreaVision av){
        if (input.equals("a")) move(-1,0);
        else if (input.equals("d")) move(1,0);
        else if (input.equals("w")) move(0,-1);
        else if (input.equals("s")) move(0,1);
        else if (input.equals("e")) SpecialAbility(av);
        else tick(av);
    }

    //recieve change in cordinates, get the new tile with the new cordinated and send to the respected 'move' function
    //depending on which tile the player is trying to step onto
    private  void move(int dx, int dy){
        move(getTileFromCord(getPosition().getX()+dx, getPosition().getY()+dy));
    }

    public void moveT(Unit u){
        u.moveU(this);
    }

    public void moveU(Enemy e){ attack(e);}

    public void moveU(Player p){ /* not supposed to happen */ }

    public void moveT(Wall w){}

    public void moveT(Empty e){swapPlaces(e);}

    //when attacking randomize 2 numbers that represents attack power of the player and defend power of the enemy
    // then if attack>defend harm the enemy with the remainder
    //if the enemy is dead take his xp and update the game maneger
    public void attack(Enemy e){
        message.call(String.format("%s engaged in combat with %s", this.getName(), e.getName()));
        message.call(this.describe());
        message.call(e.describe());
        int attack = (int)(Math.random()*(getAttackPoints()+1));
        int defend = (int)(Math.random()*(e.getDefensePoints()+1));
        message.call(String.format("%s rolled %d attack points", this.getName(), attack));
        message.call(String.format("%s rolled %d defence points", e.getName(), defend));
        if (attack>defend) {
            e.setHealthAmount(defend-attack);
            message.call(String.format(" eventually %s was hurt by %d points", e.getName(), attack-defend));
            if (e.getHealthAmount() <= 0) {
                swapPlaces(e);
                e.death(this);
            }
        }
    }

    public abstract void SpecialAbility(AreaVision av);

    //upon leveling up the following should be executed
    public void LevelingUp(){
        message.call(String.format("%s reached level %d", getName(), getPlayerLevel()+1));
        setExperience(-50*getPlayerLevel());
        setPlayerLevel(1);
        setHealthPool(10*getPlayerLevel());
        setHealthAmount(getHealthPool());
        setAttackPoints(4*getPlayerLevel());
        setDefensePoints(getPlayerLevel());
        message.call(describe());
    }

    public void death(){
        setTile('X');
        isDead=true;
    }

    public boolean isDead(){
        return isDead;
    }
}
