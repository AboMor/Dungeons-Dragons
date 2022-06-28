package BuisnessLayer.Tile;

import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Player.Player;
import BuisnessLayer.Resource.Health;
import CallBacks.MessageCallBack;
import CallBacks.SwapPlacesCaller;
import CallBacks.TileRequest;

public abstract class Unit extends Tile {
    // ----------------------------------- fields ----------------------------------------------------------------------
    protected String name;
    protected Health health;
    protected Integer defensePoints;
    protected Integer attackPoints;
    protected TileRequest tileRequest;
    protected MessageCallBack message;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Unit(String name, char c, Integer healthAmount, Integer defensePoints, Integer attackPoints){
        super(c); //generating data from tile class
        this.name=name;
        this.health=new Health(healthAmount,healthAmount);
        this.defensePoints=defensePoints;
        this.attackPoints=attackPoints;
        this.tileRequest= null;
    }

    public void init(Position pos, SwapPlacesCaller sp, TileRequest tr){
        super.init(pos, sp);
        setTileRequest(tr);
    }

    public void UnitInit(TileRequest tr, MessageCallBack message){
        setTileRequest(tr);
        setMessage(message);
    }
    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public String getName() {
        return name;
    }
    public Integer getHealthAmount() { return this.health.getCurrentAmount(); }
    public void setHealthAmount(int value){ health.setCurrentAmount(value);}
    public Integer getHealthPool(){return health.getMaxAmount();}
    public void setHealthPool(int value){health.setMaxAmount(value);}
    public Integer getDefensePoints() {return defensePoints; }
    public void setDefensePoints(int value){defensePoints=defensePoints+value;}
    public Integer getAttackPoints() {
        return attackPoints;
    }
    public void setAttackPoints(int value){attackPoints=attackPoints+value;}
    public void setTileRequest(TileRequest tr){tileRequest=tr;}
    public void setMessage(MessageCallBack mcb){message=mcb;}

    //the descreption of the unit- supposed to be printed below the board.
    public String describe() {
        String s = getName();
        while (s.length()<16)
            s=s+" ";
        s=s+"Health: "+ getHealthAmount()+"/"+getHealthPool();
        while (s.length()<34)
            s=s+" ";
        s=s+"Attack: "+ getAttackPoints();
        while (s.length()<47)
            s=s+" ";
        s=s+"Defense: "+ getDefensePoints();
        return s;
    }


    //returns a tile with those cordinates (using visitor pattern and TileRequest interface)
    public Tile getTileFromCord(int x, int y){
        return tileRequest.request(x,y);
    }

    public void move (Tile tile){
        tile.moveT(this);
    }

    public void moveT(Wall w){}

    public void moveT(Empty e){}

    public abstract void moveT(Unit t);

    public abstract void moveU(Enemy e);

    public abstract void moveU(Player p);

}
