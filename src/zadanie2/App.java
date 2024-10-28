package zadanie2;

//Окно 1 принимает все категории граждан;
//Окно 2 — только пожилых;
//Окно 3 — только бизнесменов.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class App {
    public static void main(String[] args) throws InterruptedException {
        String[] listCategory = new String[]{"молодой", "пожилой", "бизнесмен"};

        Window[] windows = new Window[]{
                new Window(1, listCategory),
                new Window(2, new String[]{"пожилой"}),
                new Window(3, new String[]{"бизнесмен"})
        };

        int[] totalPerson = new int[listCategory.length];
        int[] backPerson = new int[listCategory.length];

        int countPerson = 20;
        List<Thread> listThread = new ArrayList<>();

        for (int i = 1; i <= countPerson; i++) {
            int categoryID = new Random().nextInt(listCategory.length);

            // клиент, каждый экземпляр которого является потоком
            Person person = new Person(
                    i,
                    categoryID,
                    listCategory[categoryID],
                    windows[new Random().nextInt(windows.length)],
                    backPerson);
            totalPerson[categoryID]++;

            System.out.println("\nСоздан клиент №" + i + " с категорией " + person.getCategory());
            Thread thread = new Thread(person);
            listThread.add(thread);

            thread.start();
            Thread.sleep(new Random().nextInt(1000));  // Имитация времени между приходом клиентов
        }

        // ожидание завершения всех потоков
        for (Thread thread : listThread) {
            thread.join();
        }


        System.out.println("Всего пришло " + Arrays.stream(totalPerson).sum() +
                ". Из них ушло: " + Arrays.stream(backPerson).sum() + ". Подробнее:");
        for (int i = 0; i < listCategory.length; i++) {
            System.out.println(listCategory[i] + ": Пришло: " + totalPerson[i] + ". Ушло: " + backPerson[i] +
                    " (" + ((double) backPerson[i] / totalPerson[i] * 100) + "%)");
        }
    }
}
