package lab4b;

public class Nature implements Runnable {
    private final Garden garden;

    public Nature(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while(true) {
            garden.modifyFlowerStatus(false);
            System.out.println("Природа змінали стан рослин на свій огляд");
            try {
                Thread.currentThread().sleep(2500);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}