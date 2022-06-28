package BuisnessLayer.Player;

import CallBacks.AreaVision;
import BuisnessLayer.Enemy.Enemy;

import java.util.List;

public class Warrior extends Player {

    // ----------------------------------- fields ----------------------------------------------------------------------
    private int coolDown;
    private int remainingCooldown;
    private int abilityRange;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Warrior(String name, Integer healthAmount, Integer defensePoints, Integer attackPoints, int coolDown){
        super(name, healthAmount, defensePoints, attackPoints);
        this.coolDown=coolDown;
        this.remainingCooldown=0;
        this.abilityRange=3;
    }

    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public  Integer getCoolDown(){return coolDown;}
    public void setCoolDown(int value){coolDown=value;}
    public Integer getRemainingCooldown(){return remainingCooldown;}
    public void setRemainingCooldown(int value){remainingCooldown=Math.max(0, getRemainingCooldown()-value);}
    public Integer getAbilityRange(){return abilityRange;}

    public String describe() {
        String s = super.describe();
        while (s.length() < 87)
            s = s + " ";
        s = s + "ability range: " + getAbilityRange();
        while (s.length() < 107)
            s = s + " ";
        s = s + "cooldown: " + getRemainingCooldown();
        return s;
    }
    //in each tick the warrior's cooldown is decreased by 1 in addition to his superClass actions
    public void tick(AreaVision av){
        setRemainingCooldown(1);
        super.tick(av);
    }

    //upon leveling up the following should be executed in addition to the supercalss actions
    public void LevelingUp(){
        super.LevelingUp();
        setRemainingCooldown(getRemainingCooldown());
        setHealthPool(5*getPlayerLevel());
        setAttackPoints(2*getPlayerLevel());
        setDefensePoints(getPlayerLevel());
    }

    //casts Warrior special ability - Avengers Shield
    //randomly hits one enemy within ability range for an amount equal to 10% of his max health and heals the warrior for amount equals to 10Xdefense
    public void SpecialAbility(AreaVision av){
        if (getRemainingCooldown()==0){
            message.call(String.format("%s cask a Avengers Shield", getName()));
            List<Enemy> enemiesWithInRange = av.range(getPosition(), getAbilityRange());
            if(enemiesWithInRange.isEmpty()){
                Enemy e = enemiesWithInRange.get((int)(Math.random()*enemiesWithInRange.size()));
                e.setHealthAmount(-getHealthPool()/10);
                message.call(String.format("%s was hurt by %d points", getName(), getHealthPool()/10));
                if (e.getHealthAmount()<=0) e.death(this);
            }
            setHealthAmount(10*getDefensePoints());
            setRemainingCooldown(-getCoolDown());
            message.call(String.format("by casting an Avengers Shield %s gained %d health and restarted her cooldown back to %d", getName(), 10*getDefensePoints(),getCoolDown() ));
        }
    }
}
