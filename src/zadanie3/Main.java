package zadanie3;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Patient> queue = new LinkedBlockingQueue<>();
        MRT mrt = new MRT();
        Doctor doctor = new Doctor(queue, mrt);

        int countPatient = 10;
        int maxQueuePatient = 0;

        Thread doctorThread = new Thread(doctor);
        doctorThread.start();

        Thread mrtThread = new Thread(mrt);
        mrtThread.start();

        for (int i = 0; i < countPatient; i++) {
            Patient patient = new Patient(i);
            System.out.println("Пришёл новый пациент с ID = " + patient.getID());
            queue.put(patient);

            if (queue.size() > maxQueuePatient) {
                maxQueuePatient = queue.size();
            }

            Thread.sleep(new Random().nextInt(1000));
        }

        doctor.setIsOver(true);
        doctorThread.join();
        mrtThread.join();

        System.out.println("Максимальная длина очереди = " + maxQueuePatient);
    }
}
