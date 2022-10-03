package lab4b;

class FileScreen implements Runnable {
    private final Garden garden;

    public FileScreen(Garden garden) {
        this.garden = garden;
    }

    private void monitor(){
        garden.write();
    }
    @Override
    public void run() {
        while (true) {
            monitor();
            System.out.println(getClass().getSimpleName() + " перевірив сад");
            try {
                Thread.currentThread().sleep(3000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class ConsoleScreen implements Runnable {
    private final Garden garden;

    public ConsoleScreen(Garden garden) {
        this.garden = garden;
    }

    private void monitor(){
        garden.print();
    }
    @Override
    public void run() {
        while (true) {
            System.out.println(getClass().getSimpleName() + " перевірив сад");
            monitor();
            try {
                Thread.currentThread().sleep(3500);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}