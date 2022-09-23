package lab3b;

import java.util.concurrent.Semaphore;


public class Hairdresser implements Runnable {
    HairdresserShop shop;
    Semaphore semaphore;
    public Hairdresser(HairdresserShop shop, Semaphore semaphore) {
        this.shop = shop;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        while(true) {
                try {
                    if (shop.customersCount() == 0 && !shop.getIsSleeping()) {
                        System.out.println("Перукар ліг поспати...");
                        shop.setIsSleeping(true);
                    }
                    Customer currentCustomer = shop.takeCustomer();
                    System.out.println("Перукар почав стригти " + (currentCustomer.getId()+1));
                    Thread.currentThread().sleep(30);
                    System.out.println("Перукар закінчив стригти " + (currentCustomer.getId()+1) + " і пішов будити наступного клієнта");

                    currentCustomer.setId(currentCustomer.getID());

                    synchronized (currentCustomer) {
                        currentCustomer.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}