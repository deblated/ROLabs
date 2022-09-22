package lw3a;

public class HoneyBarrel {
    private int numOfSwallows;
    private int currentNumOfSwallows;
    private int numOfBees;
    private Sem sem;
    private Thread bear;
    private Thread[] bees;
    private boolean isSleepingBear;
    private boolean isSleepingBee;


    HoneyBarrel(int N, int n){
        this.numOfSwallows=N;
        this.currentNumOfSwallows = 0;
        this.numOfBees=n;
        this.sem = new Sem();
        this.bear= new Thread(new Bear(this,sem));
        bear.setName("Ведмідь");
        this.bees=new Thread[numOfBees];
        for(int i =0;i<numOfBees;i++){
            bees[i] = new Thread(new Bee(this,sem));
            bees[i].setName("Бджола "+ (i+1));
        }
        this.isSleepingBear = false;
        this.isSleepingBee = true;
    }

    public void StartWork(){
        for(int i =0;i<this.numOfBees;i++){
            bees[i].start();
        }
        bear.start();
    }

    public synchronized void BeeWork() {
        if (currentNumOfSwallows<numOfSwallows){
            currentNumOfSwallows++;
            System.out.println(Thread.currentThread().getName()+" наповнює глечик " + this.getCurrentNumOfSwallows()+"/"+ this.getNumOfSwallows()+"...");
            try{
                Thread.currentThread().sleep(250);
            }
            catch(InterruptedException e){}
        }
        else{
            isSleepingBee=true;
            isSleepingBear=false;
        }
  }

    public void BearWork(){
        if (currentNumOfSwallows==numOfSwallows) {
            currentNumOfSwallows = 0;
            System.out.println(Thread.currentThread().getName() + " смакує чудовим медом...");
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        else{
            isSleepingBee=false;
            isSleepingBear=true;
        }
    }

    public boolean getBearStatus(){
        return isSleepingBear;
    }
    public boolean getBeeStatus(){
        return isSleepingBee;
    }

    public synchronized boolean getCurrentStatus(){
        return numOfSwallows==currentNumOfSwallows;
    }
    public int getCurrentNumOfSwallows(){
        return currentNumOfSwallows;
    }
    public int getNumOfSwallows(){
        return numOfSwallows;
    }




}


