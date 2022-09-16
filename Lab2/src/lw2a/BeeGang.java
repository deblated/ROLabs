package lw2a;

public class BeeGang implements Runnable {
    private final Forest forest;

    public BeeGang(Forest forest) {
        this.forest = forest;
    }
    @Override
    public void run() {
        while (true) {
            int rowToCheck = forest.getRowToSearch();

            if (rowToCheck == -1 || rowToCheck>=forest.getRows()) {
                System.out.println(Thread.currentThread().getName() + " закінчила свою роботу і повернувся до відпочинку");
                break;
            }
            System.out.println(Thread.currentThread().getName() + " почала прочісувати ділянку номер " + rowToCheck);

            boolean[] row = forest.getRow(rowToCheck);
            for (int i = 0; i < forest.getColumns(); i++) {
                if (row[i]==true) {
                    forest.bearWasFound();
                    System.out.println(Thread.currentThread().getName() + " знайшла ведмедя в ділянці " + rowToCheck + " на позиції " + i);
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + " прочісала ділянку номер " + rowToCheck);
        }
    }
}