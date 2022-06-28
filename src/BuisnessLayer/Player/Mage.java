package BuisnessLayer.Player;

import CallBacks.AreaVision;
import BuisnessLayer.Enemy.Enemy;
import BuisnessLayer.Resource.Mana;

import java.util.List;

public class Mage extends Player {

    // ----------------------------------- fields ----------------------------------------------------------------------
    private Mana mana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    // ----------------------------------- constructors ----------------------------------------------------------------------

    public Mage(String name, Integer healthAmount, Integer defensePoints, Integer attackPoints,
                int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange){
        super(name, healthAmount, defensePoints, attackPoints);
        mana = new Mana(manaPool,manaPool/4);
        this.manaCost=manaCost;
        this.spellPower=spellPower;
        this.hitsCount=hitsCount;
        this.abilityRange=abilityRange;
    }

    // ----------------------------------- methods ----------------------------------------------------------------------

    //...............//getters and setters//.....................
    public Integer getManaPool(){return mana.getMaxAmount();}
    public void setManaPool(int value){mana.setMaxAmount(value);}
    public Integer getCurrentMana(){return mana.getCurrentAmount();}
    public void setCurrentMana(int value){ mana.setCurrentAmount(value);}
    public Integer getManaCost(){return manaCost;}
    public Integer getSpellPower(){return spellPower;}
    public void setSpellPower(int value){spellPower=spellPower+value;}
    public Integer getHitsCount(){return hitsCount;}
    public Integer getAbilityRange(){return abilityRange;}

    public String describe(){ return super.describe()+String.format("\t\tmana: %d/%d\t\tmana cost: %d\t\tspell power: %d\t\tability range: %d\t\t",getCurrentMana(),getManaPool(),getManaCost(), getSpellPower(), getAbilityRange());}


    //in each tick the mage's mana is increased by the player level in addition to his superClass actions
    public void tick(AreaVision av){
        setCurrentMana(getPlayerLevel());
        super.tick(av);
    }

    //upon leveling up the following should be executed in addition to the supercalss actions
    public void LevelingUp(){
        super.LevelingUp();
        setManaPool(25*getPlayerLevel());
        setCurrentMana(getManaPool()/4);
        setSpellPower(10*getPlayerLevel());
    }

    //casts Mage's spacial ability- Blizzard
    //Blizzard randomly hits emenies with in range for an amount equals to the mage’s spell power at the cost of mana.
    public void SpecialAbility(AreaVision av){
        if (getCurrentMana()>getManaCost()){
            message.call(String.format("%s cask a Blizzard", getName()));
            List<Enemy> enemiesWithInRange = av.range(getPosition(), getAbilityRange());
            int hits =0;
            String s = "";
            while (hits<getHitsCount() & !enemiesWithInRange.isEmpty()){
                Enemy e = enemiesWithInRange.remove((int)(Math.random()*enemiesWithInRange.size()));
                if (Math.random()<0.5) {
                    s=s+", "+e.getName();
                    e.setHealthAmount(-getSpellPower());
                    if (e.getHealthAmount()<=0) e.death(this);
                    hits++;}
            }
            message.call(s+ String.format(" were hurt by %d points", getSpellPower()));
            setCurrentMana(-manaCost);
            message.call(String.format("by casting a Blizzard %s lost %d mana", getName(), getManaCost()));
        }
        else message.call(String.format("%s tried to cask a Blizzard but didn't have enough mana", getName()));

    }

}
