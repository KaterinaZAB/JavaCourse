package zadanie1;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;

public class Writer implements Runnable {
    CopyOnWriteArrayList<Integer> listOfNumbers;
    static int i = 0;

    public Writer(CopyOnWriteArrayList<Integer> listOfNumbers) {
        this.listOfNumbers = listOfNumbers;
    }

    @Override
    public void run() {
        try {
            i++;
            Thread.sleep(new Random().nextInt(1000));  // Случайная задержка
            listOfNumbers.add(i);
            System.out.println("Записали: " + i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
