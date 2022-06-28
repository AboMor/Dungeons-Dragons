package BuisnessLayer.Enemy;
import BuisnessLayer.Player.Player;
import BuisnessLayer.Tile.Position;

public class Monster extends Enemy {

    // ----------------------------------- fields ----------------------------------------------------------------------
    private int visionRange;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Monster( String name, char c,Integer healthAmount, Integer defensePoints, Integer attackPoints, int experience, int visionRange){
        super(name,c,healthAmount,defensePoints,attackPoints,experience);
        this.visionRange=visionRange;
    }


    // ----------------------------------- methods ----------------------------------------------------------------------

    // in each tick the monster checks if the player is in its vision and if it is chaces after him
    //if he isnt the it walks in randon direction
    public void tick(Player player){
        Position playerPos = player.getPosition();
        if(getPosition().isInRange(playerPos, visionRange)){
            int dx = getPosition().getX()-playerPos.getX();
            int dy =getPosition().getY()-playerPos.getY();
            if (Math.abs(dx)>Math.abs(dy)){
                if(dx>0) move(-1,0);  //left
                else move(1,0);  //right
            }
            else{
                if (dy>0) move(0,-1);  //up
                else move(0,1);  //down
            }
        }
        else {
            double rand = Math.random();
            if (rand<0.25) move(-1,0);    //left
            else if (rand<0.5) move(1,0);  //right
            else if (rand<0.75) move(0,-1);  //up
            else  move(0,1);  //down
        }
    }



}
