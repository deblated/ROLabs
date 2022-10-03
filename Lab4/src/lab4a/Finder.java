package lab4a;

public abstract class Finder implements Runnable {
    protected final Database database;
    protected String searchParam;
    protected boolean option;

    protected Finder(Database database, String toSearch) {
        this.database = database;
        this.searchParam = toSearch;
        this.option=true;
    }

    protected Finder(Database database){
        this.database=database;
        this.option=false;
    }
    protected abstract String find();

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1250);
                String searchResult = find();
                if(searchResult != null) {
                    System.out.println(getClass().getSimpleName() + " успішно виконав запит з параметром \"" + searchParam + "\", результат: \"" + searchResult +"\"");
                } else {
                    System.out.println(getClass().getSimpleName() + " не зміг виконати запит");

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class NameFinder extends Finder {
    public NameFinder(Database database, String searchParam) {
        super(database, searchParam);
    }
    public NameFinder(Database database) {
        super(database);
    }

    @Override
    protected String find() {
        try {
            if(option!=true){
                searchParam = database.getRandomPhoneNumber();
            }
            return database.getNameByPhoneNumber(searchParam);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class PhoneFinder extends Finder {
    public PhoneFinder(Database database, String searchParam) {
        super(database, searchParam);
    }
    public PhoneFinder(Database database) {
        super(database);
    }

    @Override
    protected String find() {
        try {
            if(option!=true){
                searchParam = database.getRandomName();
            }
            return database.getPhoneNumberByName(searchParam);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}