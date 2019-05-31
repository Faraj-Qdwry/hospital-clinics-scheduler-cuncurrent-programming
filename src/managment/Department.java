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

    // Initialize runnable and callable references
    protected static ResFileReader fileReader;
    public Scheduler scheduler;
    protected static ReportGenerator reportGenerator;

    // number of doctors available in the department
    protected static int doctorsAvailable = 0;
    static ArrayList<Doctor> doctorsList;
    // a min heap to store all the available doctors that have been assigned to clinics
    protected static PriorityBlockingQueue<Doctor> doctorsHeap;

    // a queue to store the patient queue
    public static BlockingQueue<Patient> patientQueue;


    // set the number of doctors available
    public static void setDoctorsAvailable(int doctors) {
        doctorsAvailable = doctors;
    }

    static String clinicNames[] = {"C-0", "C-1", "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10"};

    static Lock lock = new ReentrantLock();
    public volatile static Condition schedulerCondition = lock.newCondition();

    public volatile static boolean docListReady = false;


    public static void setDoctorList(ArrayList<Doctor> doctors) {
        //System.out.print("Doctors : ");
        //doctors.stream().forEach((doctor) -> System.out.print(doctor.getId()+" ,"));
        doctorsList = doctors;
    }

    public static void readyDocs() {
        docListReady = true;
        //noDoctorsYet.signalAll();
    }

    public static void main(String[] args) throws InterruptedException {

        // Create the file reader thread and start it
        ResFileReader fileReader = new ResFileReader();
        Thread reader = new Thread(fileReader);
        reader.start();

        // waits till docs are all available
        while (!docListReady) {
        }

        initDataStructures(doctorsList.size());

        // get and create doctors for the number of doctors, assign them to clinics and run the doctors threads
        ExecutorService doctorsPool = Executors.newFixedThreadPool(doctorsAvailable);

        ArrayList<Future<DoctorReport>> futures = new ArrayList<>();

        // add the doctors to a min heap, and submit them to doctors pool
        for (int i = 0; i < doctorsAvailable; i++) {
            Doctor doctorObj = doctorsList.get(i).setClinic(new Clinic(clinicNames[i]));
            futures.add(doctorsPool.submit(doctorObj));
            doctorsHeap.add(doctorObj);
            //System.out.println("Doctos Added "+i);
        }


        // start the timer thread
        Timer.startTimer();

        // start a scheduler thread
        // scheduler = new Scheduler();
        new Thread(new Scheduler()).start();
        //scheduler.start();


        for (Future report: futures) {
            try {
                DoctorReport doctorReport = (DoctorReport) report.get();
                System.out.println(doctorReport.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        doctorsPool.awaitTermination(20, TimeUnit.MINUTES);

    }

//    public static void pauseScheduler() {
//        scheduling = false;
//        try {
//            //while (!scheduling)
//            scheduler.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void resumeScheduler() {
//        scheduling = true;
//        scheduler.notify();
//    }

    private static void initDataStructures(int size) {
        doctorsHeap = new PriorityBlockingQueue<>(size,
                (o1, o2) -> {
                    if (o1.getTreatedPatients() < o2.getTreatedPatients())
                        return 1;
                    else if (o2.getTreatedPatients() > o1.getTreatedPatients())
                        return -1;
                    else
                        return 0;
                }
        );

        patientQueue = new ArrayBlockingQueue<>(size, true);

        //scheduler = new Scheduler();
    }
}
