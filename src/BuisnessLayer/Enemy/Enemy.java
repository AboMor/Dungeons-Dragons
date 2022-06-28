package BuisnessLayer.Enemy;
import BuisnessLayer.Player.Player;
import BuisnessLayer.Tile.Empty;
import BuisnessLayer.Tile.Unit;
import BuisnessLayer.Tile.Wall;
import CallBacks.EnemyDeathCallback;

public abstract class Enemy extends Unit {
    // ----------------------------------- fields ----------------------------------------------------------------------
    protected Integer experience;
    public EnemyDeathCallback edcb;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Enemy(String name, char c, Integer healthAmount, Integer defensePoints, Integer attackPoints, int experience){
        super(name,c,healthAmount,defensePoints,attackPoints);
        this.experience=experience;
    }


    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public Integer getExperience(){return experience;}
    public void setEdcb(EnemyDeathCallback edcb){ this.edcb=edcb; }

    public String describe(){ return super.describe()+String.format("\t\texp: %s\t\tposition: %d x %d\t\t", getExperience(), getPosition().getX(), getPosition().getY());}

    public abstract void tick (Player player);

    //when attacking randomize 2 numbers that represents attack power of the emeny and defend power of the player
    // then if attack>defend harm the player with the remainder
    //if the player is dead act upon it
    public void attack(Player p){
        message.call(String.format("%s engaged in combat with %s", this.getName(), p.getName()));
        message.call(this.describe());
        message.call(p.describe());
        int attack = (int)(Math.random()*(getAttackPoints()+1));
        int defend = (int)(Math.random()*(p.getDefensePoints()+1));
        message.call(String.format("%s rolled %d attack points", this.getName(), attack));
        message.call(String.format("%s rolled %d defence points", p.getName(), defend));
        if (attack>defend) {
            p.setHealthAmount(defend- attack);
            message.call(String.format(" eventually %s was hurt by %d points", p.getName(), attack-defend));
            if (p.getHealthAmount() <= 0) p.death();
        }
    }

    //recieve change in cordinates, get the new tile with the new cordinated and send to the respected 'move' function
    //depending on which tile the enemy is trying to step onto
    protected void move(int dx, int dy){
        move(getTileFromCord(getPosition().getX()+dx, getPosition().getY()+dy));
    }

    public void moveT(Unit u){
        u.moveU(this);
    }

    public void moveU(Enemy e){}

    public void moveU(Player p){attack(p);}

    public void moveT(Wall w){}

    public void moveT(Empty e){swapPlaces(e);}

    //supposed to replace his tile with an empty one
    public void death(Player p){
        message.call(String.format("you killed %s and gained %d experience", getName(), getExperience()));
        p.setExperience(this.getExperience());
        edcb.UponEnemyDeath(this);
    }

}
