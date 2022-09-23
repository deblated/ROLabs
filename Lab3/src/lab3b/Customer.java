package lab3b;

public class Customer implements Runnable {
    private final HairdresserShop shop;
    MySemaphore semaphore;
    private int id;
    static int ID = 1;

    public void setId(int ID) {
        this.id = ID;
        this.ID++;
    }
    public static int getID(){
        return ID;
    }

    public Customer(HairdresserShop shop, MySemaphore semaphore) {
        this.shop = shop;
        this.semaphore = semaphore;
        this.setId(getID());
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while(true) {
            if (this.semaphore.getAvailable()) {
                try {
                    Thread.currentThread().sleep(((getId()%4+1)*1500));
                    this.semaphore.acquire();
                    int currentCount = shop.customersCount();

                    if (shop.getIsSleeping()==false) {
                        System.out.println("Клієнт " + id + " ліг спати... Наразі він " + (currentCount + 1) + " в черзі");
                    } else if (shop.getIsSleeping()==true) {
                        System.out.println("Клієнт " + id + " будить перукаря... Наразі він " + (currentCount + 1));
                        shop.setIsSleeping(false);
                    }
                    shop.addCustomer(this);

                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }
        }
    }
}
