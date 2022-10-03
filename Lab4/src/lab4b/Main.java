package lab4b;

public class Main {
    public static void main (String[] args) throws Exception{
        Garden garden = new Garden(5);

        Thread gardener = new Thread(new Gardener(garden));
        Thread nature = new Thread(new Nature(garden));
        Thread filescreen = new Thread(new FileScreen(garden));
        Thread consolescreen = new Thread(new ConsoleScreen(garden));
        try {
            gardener.start();
            Thread.currentThread().sleep(10);
            nature.start();
            Thread.currentThread().sleep(10);
            filescreen.start();
            Thread.currentThread().sleep(10);
            consolescreen.start();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
