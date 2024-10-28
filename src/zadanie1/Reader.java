package zadanie1;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;

public class Reader implements Runnable {
    CopyOnWriteArrayList<Integer> listOfNumbers;

    public Reader(CopyOnWriteArrayList<Integer> listOfNumbers) {
        this.listOfNumbers = listOfNumbers;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(1000));

            if (!listOfNumbers.isEmpty()) {  // проверка на наличие элементов
                Integer num = listOfNumbers.remove(0);
                System.out.println("Прочитали: " + num);
            } else {
                System.out.println("Список пуст, нечего читать");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
