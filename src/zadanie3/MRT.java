package zadanie3;

import java.util.Random;

public class MRT implements Runnable {
    private boolean isBusy = false;
    private Patient patient;
    private boolean isOver = false;

    public synchronized void scanPatient(Patient patient) throws InterruptedException {
        System.out.println("МРТ начал обследовать пациента с ID = " + patient.getID());
        this.patient = patient;
        isBusy = true;
        notifyAll();
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    //занятость кабинета
    public synchronized boolean getBusy() {
        return isBusy;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    if (isOver && patient == null) {
                        break;
                    }
                    if (patient == null) {
                        wait();
                    }
                }

                Thread.sleep(new Random().nextInt(4000));
                System.out.println("МРТ закончил обследовать пациента с id = " + patient.getID());

                synchronized (this) {
                    isBusy = false;
                    patient = null;
                    notifyAll();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("МРТ закончил свою работу");
        }
    }
}
