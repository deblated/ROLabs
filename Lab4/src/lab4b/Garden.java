package lab4b;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.io.FileWriter;
import java.io.IOException;

public class Garden {
    private final boolean [][]FlowerField;
    private final ReadWriteLock Lock;
    private final String Filename = "lab4b.txt";

    public Garden(int size) {
        this.FlowerField = new boolean[size][size];
        this.Lock = new ReentrantReadWriteLock();
        modifyFlowerStatus(false);
        try(FileWriter writer = new FileWriter(Filename, false)){
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void modifyFlowerStatus(boolean option){
        try {
            Lock.writeLock().lock();
            if (option) {
                for (int i = 0; i < FlowerField.length; i++) {
                    for (int j = 0; j < FlowerField.length; j++) {
                        FlowerField[i][j] = true;
                    }
                }
            } else {
                int bool;
                for (int i = 0; i < FlowerField.length; i++) {
                    for (int j = 0; j < FlowerField.length; j++) {
                        bool = (int) (Math.random() * 2);
                        if (bool == 1) {
                            FlowerField[i][j] = true;
                        } else {
                            FlowerField[i][j] = false;
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            Lock.writeLock().unlock();
        }
    }

    public void write(){
        Lock.readLock().lock();
        try(FileWriter writer = new FileWriter(Filename, true);) {
            writer.write(convert());
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Lock.readLock().unlock();
        }
    }

    public void print(){
        Lock.readLock().lock();
        System.out.printf(convert());
        Lock.readLock().unlock();
    }

    private String convert(){
        String result = "";
        //String result="Д - добре, П - погано\n";
        for (int i = 0; i < FlowerField.length; i++) {
            for (int j = 0; j < FlowerField.length; j++) {
                if (FlowerField[i][j]){
                    result+="Д ";
                }
                else{
                    result+="П ";
                }
            }
            result+="\n";
        }
        return result;
    }
}





