package BuisnessLayer.Resource;

public abstract class Resource {
    protected int maxAmount;
    protected int currentAmount;
    public Resource(int maxAmount,int currentAmount){
        this.currentAmount=currentAmount;
        this.maxAmount=maxAmount;
    }
    public int getCurrentAmount(){return currentAmount; }
    public int getMaxAmount(){return maxAmount; }
    public void setMaxAmount(int newMax){this.maxAmount=newMax;}
    public void setCurrentAmount(int value){
        this.currentAmount=Math.min(getMaxAmount(), getCurrentAmount()+value);
        if (this.getCurrentAmount()<=0) this.currentAmount=0;}

}
