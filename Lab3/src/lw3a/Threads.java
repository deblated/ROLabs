package lw3a;

class Bear implements Runnable {
    private final HoneyBarrel barrel;

    public Bear(HoneyBarrel barrel){
        this.barrel = barrel;
    }
    @Override
    public void run() {
        while (true) {
            if(!barrel.getStatus()){
                    barrel.BearWork();
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

    public Bee(HoneyBarrel barrel){
        this.barrel = barrel;
    }
    @Override
    public void run() {
        while (true) {
            if(barrel.getStatus()){
                    barrel.BeeWork();
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

