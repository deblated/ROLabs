package lab4a;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        Database base = new Database();
        for(int i = 0;i<10;i++){
            base.addToDatabase(new BaseUser());
        }

        Thread nameSelector = new Thread(new NameFinder(base));
        Thread phoneSelector = new Thread(new PhoneFinder(base));
        Thread adder = new Thread(new Adder(base));
        Thread deleter = new Thread(new Deleter(base));

        nameSelector.start();
        phoneSelector.start();
        adder.start();
        deleter.start();
    }
}