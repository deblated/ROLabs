package lab4a;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    private final String Filename = "lab4a.txt";
    private final ReadWriteLock Lock;
    ArrayList<BaseUser> Users;

    public Database(){
        this.Lock = new ReentrantReadWriteLock();
        this.Users = new ArrayList<>();
        try(FileWriter writer = new FileWriter(Filename, false)){
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public void addToDatabase(BaseUser User) throws InterruptedException{
        try(FileWriter writer = new FileWriter(Filename, true)) {
            Lock.writeLock().lock();
            Users.add(User);
            writer.write(User.getFirstName()+" "+User.getFatherName()+" "+User.getLastName()+" - "+User.getPhoneNumber()+", ");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Lock.writeLock().unlock();
        }
    }

    public ArrayList<BaseUser> deleteFromDatabase() throws InterruptedException{
        ArrayList<BaseUser> toDelete =new ArrayList<>();
        try {
            Lock.writeLock().lock();
            for(int i =0;i<Users.size();){
                i+=Math.random()*(Users.size()/2)+1;
                if(i< Users.size()){
                    toDelete.add(Users.get(i));
                }
            }
            if(Users.removeAll(toDelete)) {
                try(FileWriter writer = new FileWriter(Filename, false)) {
                    for(BaseUser user : Users) {
                        writer.write(user.getFirstName()+" "+user.getFatherName()+" "+user.getLastName()+" - "+user.getPhoneNumber()+", ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        } finally {
            Lock.writeLock().unlock();
            return toDelete;
        }
    }

    public String getNameByPhoneNumber(String phoneNumber) throws InterruptedException{
        return find("phone",phoneNumber);
    }

    public String getPhoneNumberByName(String lastName) throws InterruptedException{
        return find("name",lastName);
    }

    private String find(String option, String val){
        this.Lock.readLock().lock();
        String strForScan;
        String []splittedStr;
        BaseUser user = new BaseUser("","","","");
        try(FileReader reader = new FileReader(Filename)) {
             Scanner scan = new Scanner(reader).useDelimiter(", ");
             while(scan.hasNext()) {
                 strForScan = scan.next();
                 splittedStr = strForScan.split("\\s");
                 if (splittedStr[4].equals(val) || splittedStr[2].equals(val)) {
                     user.setFirstName(splittedStr[0]);
                     user.setFatherName(splittedStr[1]);
                     user.setLastName(splittedStr[2]);
                     user.setPhoneNumber(splittedStr[4]);
                     break;
                 }
             }
         } catch (IOException e) {
             throw new RuntimeException(e);
         } finally {
            Lock.readLock().unlock();
         }
        if(option.equals("phone")){
            return !user.getFullName().equals("  ") ? user.getFullName() : "НЕ ЗНАЙДЕНО";
        }
        else if(option.equals("name")){
            return !user.getPhoneNumber().equals("") ? user.getPhoneNumber() : "НЕ ЗНАЙДЕНО";
        }
        else{
            return "ПОМИЛКА";
        }
    }

    public String getRandomPhoneNumber(){
        return Users.get((int)(Math.random() * Users.size())).getPhoneNumber();
    }

    public String getRandomName(){
        return Users.get((int)(Math.random() * Users.size())).getLastName();
    }

    public int getUsersSize(){
        return Users.size();
    }
}

