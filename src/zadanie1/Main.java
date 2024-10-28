package zadanie1;

import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> listOfNumbers = new CopyOnWriteArrayList<>();

        Writer writer = new Writer(listOfNumbers);
        Reader reader = new Reader(listOfNumbers);

        int i = 0;
        while (i < 5) {
            System.out.println("\ni: " + (i + 1));
            writer.run();
            Thread.sleep(500);  // задержка между записями и чтениями
            reader.run();
            i++;
        }
    }
}
