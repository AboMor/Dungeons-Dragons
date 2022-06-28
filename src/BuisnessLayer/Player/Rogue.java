package BuisnessLayer.Player;

import CallBacks.AreaVision;
import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Resource.Energy;

import java.util.List;

public class Rogue extends Player {
    // ----------------------------------- fields ----------------------------------------------------------------------
    private int cost;
    private Energy energy;
    private int abilityRange;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Rogue(String name, Integer healthAmount, Integer defensePoints, Integer attackPoints, int cost) {
        super(name, healthAmount, defensePoints, attackPoints);
        this.cost = cost;
        this.energy = new Energy(100, 100);
        this.abilityRange=2;
    }

    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public Integer getCost(){return cost;}
    public Integer getCurrentEnergy(){return energy.getCurrentAmount();}
    public Integer getEnergyAmount(){return energy.getMaxAmount();}
    public void setCurrentEnergy(int value){energy.setCurrentAmount(value);}
    public Integer getAbilityRange(){return abilityRange;}

    public String describe(){ return super.describe()+String.format("\t\tenergy: %d/%d\t\t cost: %d\t\tability range: %d\t\t",getCurrentEnergy(),getEnergyAmount(),getCost(),getAbilityRange());}


    //in each tick the rogue's energy is increased by 10 in addition to his superClass actions
    public void tick(AreaVision av){
        setCurrentEnergy(10);
        super.tick(av);
    }

    //upon leveling up the following should be executed in addition to the supercalss actions
    public void LevelingUp(){
        super.LevelingUp();
        setCurrentEnergy(100);
        setAttackPoints(3*getPlayerLevel());
    }

    //casts Rouge's special ability - Fan Of Knives
    //hits everyone in his ability range for an amount equals to the rouges attack points at the cost of energy
    public void SpecialAbility(AreaVision av){
        if (getCurrentEnergy()>getCost()){
            message.call(String.format("%s cask a Fan Of Knives", getName()));
            List<Enemy> enemiesWithInRange = av.range(getPosition(), getAbilityRange());
            String s = "";
            for (Enemy e: enemiesWithInRange) {
                e.setHealthAmount(-attackPoints);
                s=s+", "+e.getName();
                if (e.getHealthAmount()<=0) e.death(this);
            }
            message.call(s+ String.format(" were hurt by %d points", getAttackPoints()));
            setCurrentEnergy(-cost);
            message.call(String.format("by casting a Fan Of Knives %s lost %d mana", getName(), getCost()));
        }
        else message.call(String.format("%s tried to cask a Blizzard but didn't have enough mana", getName()));

    }


}
