package lab3b;

public class MySemaphore{
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