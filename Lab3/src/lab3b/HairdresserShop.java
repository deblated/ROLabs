package lab3b;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HairdresserShop {
    private BlockingQueue<Customer> customers;
    private Thread[] threads;
    private Thread hairdresser;
    private int size;
    private MySemaphore sem;
    private boolean isSleeping = false;

    public boolean getIsSleeping(){
        return isSleeping;
    }
    public void setIsSleeping(boolean val){
        isSleeping = val;
    }
    public HairdresserShop(int N) {
        this.sem = new MySemaphore();
        this.customers = new LinkedBlockingQueue<>();
        this.size=N;
        threads = new Thread[size];
        for(int i = 0; i < size; i++) {
            threads[i] = new Thread(new Customer(this, sem));
        }
        hairdresser = new Thread(new Hairdresser(this, sem));
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


