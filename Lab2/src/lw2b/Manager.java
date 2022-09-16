package lw2b;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Manager {
    private final int numberOfGoods = 13;
    private int totalPrice = 0;

    private BlockingQueue<Product> storage = new ArrayBlockingQueue<Product>(numberOfGoods);
    private BlockingQueue<Product> storage_car = new ArrayBlockingQueue<Product>(numberOfGoods);
    private BlockingQueue<Product> car = new ArrayBlockingQueue<Product>(numberOfGoods);

    private Thread Ivanov,Petrov,Nechyporchuk;

    public void manageProducts() throws InterruptedException {
        generateProducts();

        Ivanov = new Thread(new Guy1());
        Petrov = new Thread(new Guy2());
        Nechyporchuk = new Thread(new Guy3());

        Ivanov.start();
        Petrov.start();
        Nechyporchuk.start();

        Ivanov.join();
        Petrov.join();
        Nechyporchuk.join();
    }

    private void generateProducts() throws InterruptedException {
        for (int i = 0; i < numberOfGoods; i++) {
            storage.put(new Product());
        }
    }
    private void producer() throws InterruptedException {
        while (!storage.isEmpty()) {
            System.out.println("Іванов виносить майно..." );
            storage_car.put(storage.take());
            Thread.currentThread().sleep(100);
        }
    }
    private void producer_consumer() throws InterruptedException {
        while (true) {
            System.out.println("Петров вантажить майно..." );
            car.put(storage_car.take());
            Thread.currentThread().sleep(200);

            if (storage.isEmpty() && storage_car.isEmpty()) {
                break;
            }
        }
    }
    private void consumer() throws InterruptedException {
        while (true) {
            System.out.println("Нечипорчук підраховує вартість..." );
            totalPrice += car.take().getPrice();
            Thread.currentThread().sleep(300);

            if (storage.isEmpty() && car.isEmpty() && storage_car.isEmpty()) {
                break;
            }
        }
        System.out.println("");
        System.out.println("Зрадники розікрали склад на " + totalPrice + " гривень");
    }
    class Guy1 implements Runnable {
        public void run() {
            try {
                producer();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
    class Guy2 implements Runnable {
        public void run() {
            try {
                producer_consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
    class Guy3 implements Runnable {
        public void run() {
            try {
                consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
}


class Product {
    private int price;
    public Product() {
        this.price = (int)(Math.random() * 10000);
    }
    public int getPrice() {
        return price;
    }
}

