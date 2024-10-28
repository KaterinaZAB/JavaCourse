package zadanie3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Doctor implements Runnable {
    private BlockingQueue<Patient> queue;
    private MRT mrt;
    private boolean isOver = false;

    public Doctor(BlockingQueue<Patient> queue, MRT mrt) {
        this.queue = queue;
        this.mrt = mrt;
    }

    public void setIsOver(boolean isOver) {
        this.isOver= isOver;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Patient patient = queue.poll(); // берем пациента из очереди

                if (patient == null) {
                    if (isOver) {
                        break;
                    }
                    Thread.sleep(500);
                    continue;
                }

                System.out.println("Терапевт начал обследовать пациента с ID = " + patient.getID());
                Thread.sleep(new Random().nextInt(1000));

                //synchronized используется для организации синхронизации между терапевтом и аппаратом МРТ
                synchronized (mrt) {
                    if (mrt.getBusy()) {
                        System.out.println("МРТ занято, терапевт ждёт.");
                        mrt.wait();
                    }

                    System.out.println("Терапевт закончил обследовать пациента с ID = " + patient.getID());
                    mrt.scanPatient(patient);
                }
            }
            System.out.println("Терапевт закончил осмотр всех пациентов");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            mrt.setIsOver(true);
        }
    }
}
