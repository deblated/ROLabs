package lw3a;

class MySemaphore {
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
    private MySemaphore semaphore;

    public Bear(HoneyBarrel barrel, MySemaphore semaphore){
        this.barrel = barrel;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        while (true) {
            if(barrel.getBeeStatus()==true && barrel.getBearStatus()==false){
                try{
                    this.semaphore.acquire();
                    barrel.BearWork();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    semaphore.release();
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
    private MySemaphore semaphore;

    public Bee(HoneyBarrel barrel, MySemaphore semaphore){
        this.barrel = barrel;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        while (true) {
            if(barrel.getBeeStatus()==false && barrel.getBearStatus()==true){
                try{
                    this.semaphore.acquire();
                    barrel.BeeWork();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    semaphore.release();
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

