package zadanie2;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class Person implements Runnable {
    private int ID;
    private int categoryID;
    private String category;
    private Window window;
    private int[] backPerson;

    public Person(int ID, int categoryID, String category, Window window, int[] backPerson) {
        this.ID = ID;
        this.categoryID = categoryID;
        this.category = category;
        this.window = window;
        this.backPerson = backPerson;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void run() {
        if (window.tryToService(this)) {
            System.out.println("Клиент с ID = " + ID + " обслужен окном: " + window.getID());
        } else {
            backPerson[categoryID]++;
            System.out.println("Клиент с ID = " + ID + " с категорией = " + category + " ушёл из-за занятости или неподходящего окна");
        }
    }
}
