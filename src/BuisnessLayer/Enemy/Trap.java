package BuisnessLayer.Enemy;

import BuisnessLayer.Player.Player;

public class Trap extends Enemy {

    // ----------------------------------- fields ----------------------------------------------------------------------
    private int visibilityTime;
    private int invisibilityTime;
    private int tickCounter;
    private boolean visible;
    private int attackRange;
    private char visibleTile;
    private int visionRange;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Trap(String name, char c, Integer healthAmount, Integer defensePoints, Integer attackPoints, int experience, int visibilityTime, int invisibilityTime) {
        super(name,c,healthAmount,defensePoints,attackPoints,experience);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        this.visible=true;
        this.tickCounter=0;
        this.attackRange=2;
        this.visibleTile=c;
        this.visionRange=2;
    }

    // ----------------------------------- methods ----------------------------------------------------------------------


    public void tick(Player player){
        tickCounter++;
        if (tickCounter==visibilityTime)
        { visible=false; setTile('.'); }
        if(tickCounter==visibilityTime+invisibilityTime){
            visible=true; setTile(visibleTile); tickCounter=0;
        }
        if(getPosition().isInRange(player.getPosition(), visionRange)){
           attack(player);
        }


    }


}