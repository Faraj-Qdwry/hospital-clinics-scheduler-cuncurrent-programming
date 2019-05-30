package managment;

import clinic.Clinic;
import clinic.Doctor;
import clinic.Patient;
import utilities.*;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Department {

    // Initialize runnable and callable references
    protected static ResFileReader fileReader;
    protected static Scheduler scheduler;
    protected static ReportGenerator reportGenerator;

    // number of doctors available in the department
    protected static int doctorsAvailable = 0;
    // a min heap to store all the available doctors that have been assigned to clinics
    protected static PriorityQueue<Doctor> doctorsHeap;

    // a queue to store the patient queue
    public static Queue<Patient> patientQueue;


    // set the number of doctors available
    public static void setDoctorsAvailable(int doctors) {
        doctorsAvailable = doctors;
    }

    public static void main(String[] args) throws InterruptedException {

        // Create the file reader thread and start it
        Thread fileReaderThread = new Thread(fileReader);
        fileReaderThread.start();
        // while no number of doctors have been specified yet
        while (doctorsAvailable <= 0) {
        }

        // get and create doctors for the number of doctors, assign them to clinics and run the doctors threads
        ExecutorService doctorsPool = Executors.newFixedThreadPool(doctorsAvailable);

        // add the doctors to a min heap, and submit them to doctors pool
        for (int doctors = 0; doctors < doctorsAvailable; doctors++) {
            Doctor doctorObj = new Doctor(Integer.toString(doctors), new Clinic(Integer.toString(doctors)));
            doctorsPool.submit(doctorObj);
            doctorsHeap.add(doctorObj);
        }

        // start the timer thread
        Timer.startTimer();

        // start a scheduler thread
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();
    }
}
