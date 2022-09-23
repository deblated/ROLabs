package lab3b;

public class Hairdresser implements Runnable {
    HairdresserShop shop;
    MySemaphore semaphore;
    public Hairdresser(HairdresserShop shop, MySemaphore semaphore) {
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
                    Thread.currentThread().sleep(3000);
                    System.out.println("Перукар закінчив стригти " + (currentCustomer.getId()+1) + " і пішов будити наступного клієнта");

                    currentCustomer.setId(currentCustomer.getID());

                    synchronized (currentCustomer) {
                        currentCustomer.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}