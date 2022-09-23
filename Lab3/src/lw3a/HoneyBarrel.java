package lw3a;

public class HoneyBarrel {
    private int numOfSwallows;
    private int currentNumOfSwallows;
    private final int numOfBees;
    private Thread bear;
    private Thread[] bees;

    private boolean semaphore;

    HoneyBarrel(int N, int n){
        this.numOfSwallows=N;
        this.currentNumOfSwallows = 0;
        this.numOfBees=n;
        this.bear= new Thread(new Bear(this));
        bear.setName("Ведмідь");
        this.bees=new Thread[numOfBees];
        for(int i =0;i<numOfBees;i++){
            bees[i] = new Thread(new Bee(this));
            bees[i].setName("Бджола "+ (i+1));
        }
        this.semaphore = true;
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
            System.out.println(Thread.currentThread().getName()+" наповнює глечик " + this.currentNumOfSwallows+"/"+ this.numOfSwallows+"...");
            try{
                Thread.currentThread().sleep(250);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        else{
            this.semaphore=false;
        }
  }

    public void BearWork(){
        if (currentNumOfSwallows==numOfSwallows) {
            currentNumOfSwallows = 0;
            System.out.println(Thread.currentThread().getName() + " смакує чудовим медом...");
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
           this.semaphore=true;
        }
    }

    public boolean getStatus(){return semaphore;}
}


