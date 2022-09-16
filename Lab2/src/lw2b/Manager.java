package lw2b;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Manager {
    private int numberOfGoods = 13;
    private int totalPrice = 0;

    private BlockingQueue<Product> storage = new ArrayBlockingQueue<Product>(numberOfGoods);
    private BlockingQueue<Product> storage_car = new ArrayBlockingQueue<Product>(numberOfGoods);
    private BlockingQueue<Product> car = new ArrayBlockingQueue<Product>(numberOfGoods);

    Thread Ivanov,Petrov,Nechyporchuk;

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
            storage_car.put(storage.take());
            System.out.println("Іванов виносить майно..." );
            Thread.currentThread().sleep(250);
        }

    }

    private void producer_consumer() throws InterruptedException {
        while (true) {
            car.put(storage_car.take());
            System.out.println("Петров вантажить майно..." );
            Thread.currentThread().sleep(500);

            if (storage.isEmpty() && storage_car.isEmpty()) {
                break;
            }
        }
    }

    private void consumer() throws InterruptedException {
        while (true) {
            totalPrice += car.take().getPrice();
            System.out.println("Нечипорчук підраховує вартість..." );
            Thread.currentThread().sleep(750);

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

