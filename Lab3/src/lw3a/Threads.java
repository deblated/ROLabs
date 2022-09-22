package lw3a;

class Sem{
    private boolean isAvailable = true;
    public void acquire() throws InterruptedException{
        this.isAvailable=false;
    }
    public void release(){
        this.isAvailable=true;
    }
    public boolean getAvailable(){
        return isAvailable;
    }
}

class Bear implements Runnable {
    private final HoneyBarrel barrel;
    private Sem sem;

    public Bear(HoneyBarrel barrel, Sem sem){
        this.barrel = barrel;
        this.sem = sem;
    }
    @Override
    public void run() {
        while (true) {
            if(/*this.sem.getAvailable() && barrel.getCurrentStatus()*/ barrel.getBeeStatus()==true && barrel.getBearStatus()==false){
                try{
                    this.sem.acquire();
                    barrel.BearWork();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    sem.release();
                }
            }
            try{
                Thread.currentThread().sleep(1);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Bee implements Runnable {
    private final HoneyBarrel barrel;
    private Sem sem;

    public Bee(HoneyBarrel barrel, Sem sem){
        this.barrel = barrel;
        this.sem = sem;
    }
    @Override
    public void run() {
        while (true) {
            if(/*this.sem.getAvailable() &&!barrel.getCurrentStatus()*/ barrel.getBeeStatus()==false && barrel.getBearStatus()==true){
                try{
                    this.sem.acquire();
                    barrel.BeeWork();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    sem.release();
                }
            }
            try{
                Thread.currentThread().sleep(1);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

