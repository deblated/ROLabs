package lab4b;

public class Gardener implements Runnable {
    private final Garden garden;

    public Gardener(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while(true) {
            garden.modifyFlowerStatus(true);
            System.out.println("Садівник полив квіти");
            try {
                Thread.currentThread().sleep(2700);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}