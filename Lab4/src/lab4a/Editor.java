package lab4a;

import java.util.ArrayList;

class Adder implements Runnable {
    private final Database database;

    public Adder(Database database) {
        this.database = database;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                BaseUser toAdd = new BaseUser();
                database.addToDatabase(toAdd);
                System.out.println(getClass().getSimpleName() + " wrote user. There are " + database.getUsersSize() + " users in database");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Deleter implements Runnable {
    private final Database database;

    public Deleter(Database database) {
        this.database = database;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(2500);
                ArrayList<BaseUser> deletedUsers = database.deleteFromDatabase();

                if(!deletedUsers.isEmpty()) {
                    System.out.println(getClass().getSimpleName() + " видалив "+deletedUsers.size()+" користувача/ів "+database.getUsersSize()+" користувачів лишилось");
                } else {
                    System.out.println(getClass().getSimpleName() + " не зміг виконати операцію видалення");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}