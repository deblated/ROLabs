package lab3b;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.Semaphore;


public class HairdresserShop {
    private BlockingQueue<Customer> customers;
    private Thread[] threads;
    private Thread hairdresser;
    private int size;
    private boolean isSleeping = false;
    private Semaphore semaphore;

    public boolean getIsSleeping(){
        return isSleeping;
    }
    public void setIsSleeping(boolean val){
        isSleeping = val;
    }
    public HairdresserShop(int N) {
        this.semaphore=new Semaphore(1);
        this.customers = new LinkedBlockingQueue<>();
        this.size=N;
        threads = new Thread[size];
        for(int i = 0; i < size; i++) {
            threads[i] = new Thread(new Customer(this, semaphore));
        }
        hairdresser = new Thread(new Hairdresser(this, semaphore));
    }

    public void start() {
        for(int i = 0; i < size; i++) {
            threads[i].start();
        }
        hairdresser.start();
    }

    public void addCustomer(Customer customer) throws InterruptedException {
        customers.put(customer);
    }

    public Customer takeCustomer() throws InterruptedException {
        return customers.take();
    }

    public int customersCount() {
        return customers.size();
    }
}


