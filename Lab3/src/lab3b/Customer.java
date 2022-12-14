package lab3b;

import java.util.concurrent.Semaphore;


public class Customer implements Runnable {
    private final HairdresserShop shop;
    private Semaphore semaphore;
    private int id;
    private static int ID = 0;

    public void setId(int ID) {
        this.id = ID;
        this.ID++;
    }
    public static int getID(){
        return ID;
    }

    public int getId() {
        return id;
    }

    public Customer(HairdresserShop shop, Semaphore semaphore) {
        this.shop = shop;
        this.semaphore = semaphore;
        this.setId(getID());
    }

    @Override
    public void run() {
        while(true) {
                try {
                    Thread.currentThread().sleep(((getId()%3)+1)*1);
                    semaphore.acquire();
                    Thread.currentThread().sleep(((getId()%3)+1)*30);
                    int currentCount = shop.customersCount();

                    if (!shop.getIsSleeping()) {
                        System.out.println("Клієнт " + (id+1) + " ліг спати... Наразі він " + (currentCount + 1) + " в черзі");
                    } else if (shop.getIsSleeping()) {
                        System.out.println("Клієнт " + (id+1) + " будить перукаря... Наразі він " + (currentCount + 1));
                        shop.setIsSleeping(false);
                    }
                    shop.addCustomer(this);
                    semaphore.release();
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
