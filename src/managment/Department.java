package managment;

import clinic.Clinic;
import clinic.Doctor;
import clinic.Patient;
import utilities.*;
import utilities.Timer;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Department {

    /** Initialize runnable and callable references **/
    protected static ResFileReader fileReader;
    public Scheduler scheduler;
    protected static ReportGenerator reportGenerator;
    static Lock lock = new ReentrantLock();
    public volatile static Condition schedulerCondition = lock.newCondition();

    /** Doctor, clinic and patient Initialization **/
    // number of doctors available in the department
    protected static int doctorsAvailable = 0;
    static ArrayList<Doctor> doctorsFileList;

    // a min heap to store all the available doctors that have been assigned to clinics
    protected static PriorityBlockingQueue<Doctor> doctorsHeap;

    // a queue to store the patient queue
    public static BlockingQueue<Patient> patientQueue;

    static String clinicNames[] = {"C-0", "C-1", "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10"};

    public volatile static boolean docListReady = false;


    // Get doctors list read
    public static void setDoctorListFromFile(ArrayList<Doctor> doctors) {
        doctorsFileList = doctors;
    }

    // set the number of doctors available
    public static void setDoctorsAvailable(int doctors) {
        doctorsAvailable = doctors;
    }

    // set the doctors as ready and available to work
    public static void setDoctorsReady() {
        docListReady = true;
    }

    public static void main(String[] args) throws InterruptedException {

        // Create the file reader thread and start it
        ResFileReader fileReader = new ResFileReader();
        Thread readerThread = new Thread(fileReader);
        readerThread.start();

        // waits till docs are all available
        while (!docListReady) {
        }

        initDataStructures(doctorsFileList.size());

        // get and create doctors for the number of doctors, assign them to clinics and run the doctors threads
        ExecutorService doctorsPool = Executors.newFixedThreadPool(doctorsAvailable);
        ArrayList<Future<DoctorReport>> futures = new ArrayList<>();

        // add the doctors to a min heap, and submit them to doctors pool
        for (int i = 0; i < doctorsAvailable; i++) {
            Doctor doctorObj = doctorsFileList.get(i).setClinic(new Clinic(clinicNames[i]));
            futures.add(doctorsPool.submit(doctorObj));
            doctorsHeap.add(doctorObj);
        }


        // start the timer thread
        Timer.startTimer();

        // start a scheduler thread
        new Thread(new Scheduler()).start();

        // Future Objects for report generation
        for (Future report : futures) {
            try {
                DoctorReport doctorReport = (DoctorReport) report.get();
                System.out.println(doctorReport.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        doctorsPool.awaitTermination(20, TimeUnit.MINUTES);
    }

    private static void initDataStructures(int size) {
        doctorsHeap = new PriorityBlockingQueue<>(size,
                Comparator.comparingInt(Doctor::getTreatedPatients)
        );

        patientQueue = new ArrayBlockingQueue<>(size, true);

    }
}

