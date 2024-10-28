package zadanie2;
//окно, принимающее гражданина (поток захватывает монитор объекта Window)
// и обслуживающее его (метод service(Person)).

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Window {
    private int ID;
    private String[] listCategory;
    private boolean isBusy = false;
    private final Lock lock = new ReentrantLock();  // Блокировка для синхронизации

    public Window(int ID, String[] listCategory) {
        this.ID = ID;
        this.listCategory = listCategory;
    }

    public int getID(){
        return ID;
    }

    public String[] getListCategory(){
        return listCategory;
    }

    public boolean tryToService(Person person) {
        lock.lock();

        try {
            if (isBusy || !isCategorySupported(person.getCategory())) {
                return false;
            }
            isBusy = true;  // окно занято
            service(person);  // обслуживаем клиента
            isBusy = false;  // окно освободилось
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;

        } finally {
            lock.unlock();
        }
    }

    private boolean isCategorySupported(String category) {
        for (String supportedCategory : listCategory) {
            if (supportedCategory.equals(category)) {
                return true;
            }

        }
        return false;
    }

    private void service(Person person) throws InterruptedException {
        System.out.println("Окно " + ID + " обслуживает клиента с категорией: " + person.getCategory());
        Thread.sleep(new Random().nextInt(2000));  // задержка для имитации времени обслуживания
    }
}
