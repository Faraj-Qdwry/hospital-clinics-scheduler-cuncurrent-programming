package managment;

import clinic.Clinic;
import clinic.Doctor;
import clinic.Patient;
import utilities.*;
import utilities.Timer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Department {

    /**
     * Initialize runnable and callable references
     **/
    protected static ResFileReader fileReader;
    public Scheduler scheduler;
    protected static ReportGenerator reportGenerator;
    static Lock lock = new ReentrantLock();
    public volatile static Condition schedulerCondition = lock.newCondition();

    /**
     * Doctor, clinic and patient Initialization
     **/
    // number of doctors available in the department
    protected static int doctorsAvailable = 0;
    static ArrayList<Doctor> doctorsFileList;
    public static volatile boolean isOpen = true;

    // a min heap to store all the available doctors that have been assigned to clinics
    protected static PriorityBlockingQueue<Doctor> doctorsHeap;

    // a queue to store the patient queue
    public static LinkedBlockingDeque<Patient> patientQueue;

    // set the number of doctors available
    public static void setDoctorsAvailable(int doctors) {
        doctorsAvailable = doctors;
    }

    static String clinicNames[] = {"C-1", "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10"};
    public volatile static boolean docListReady = false;


    // Get doctors list read
    public static void setDoctorListFromFile(ArrayList<Doctor> doctors) {
        doctorsFileList = doctors;
    }


    // set the doctors as ready and available to work
    public static void setDoctorsReady() {
        docListReady = true;
    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        ReportGenerator.startReport();
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

        System.out.println("*******************************************************");
        System.out.println("******************** Hospital Opened ******************");
        System.out.println("*******************************************************");

        // start the timer thread
        Timer.startTimer();

        // start a scheduler thread
        new Thread(new Scheduler()).start();

        ArrayList<DoctorReport> docReports = new ArrayList<>();

        try {
            ReportGenerator.addToReport(System.lineSeparator());
            ReportGenerator.addToReport("Doctors Available: " + doctorsAvailable + System.lineSeparator());
            for (Doctor currentDoctor : doctorsFileList) {
                ReportGenerator.addToReport("Doctor: " + currentDoctor.getId() + System.lineSeparator());
            }

            ReportGenerator.addToReport("Doctors Performance Report : " + System.lineSeparator());
            ReportGenerator.addToReport("----------------------------------------------------------------" + System.lineSeparator());
            ReportGenerator.addToReport(DoctorReport.tableHeader() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Future Objects for report generation
        for (Future report : futures) {
            try {
                DoctorReport doctorReport = (DoctorReport) report.get();
                docReports.add(doctorReport);
                generateDocReport(doctorReport);
                //System.out.println(doctorReport.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            ReportGenerator.addToReport("----------------------------------------------------------------" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateOverAllReport(docReports);

        doctorsPool.awaitTermination(5, TimeUnit.MINUTES);
        doctorsPool.shutdown();
    }

    private static void generateOverAllReport(ArrayList<DoctorReport> docReports) {
        int numberOfTotalVisits = ResFileReader.patientNumber;
        try {
            ReportGenerator.addToReport("================================================================" + System.lineSeparator());
            ReportGenerator.addToReport("OverAll Report : " + System.lineSeparator());
            ReportGenerator.addToReport("Number Of Patients Total Visits : " + numberOfTotalVisits + System.lineSeparator());

            int totalTreated = docReports.stream().mapToInt((report)->report.getTreatedPatients()).sum();
            double totalPatientsAvgConsultationTime = docReports.stream().mapToDouble((report)->report.getPatientsAvgConsultationTime()).summaryStatistics().getAverage();
            double totalPatientsAvgWaitingTime = docReports.stream().mapToDouble((report)->  report.getPatientsAvgWaitingTime()).summaryStatistics().getAverage();

            ReportGenerator.addToReport("Total Number Of Treated :" + totalTreated + System.lineSeparator());
            ReportGenerator.addToReport("Total Patients Avg ConsultationTime : " + totalPatientsAvgConsultationTime + System.lineSeparator());
            ReportGenerator.addToReport("Total Patients AvgWaitingTime : " + totalPatientsAvgWaitingTime + System.lineSeparator());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void generateDocReport(DoctorReport doctorReport) {
        System.out.println(doctorReport.toString());
    }

    private static void initDataStructures(int size) {
        doctorsHeap = new PriorityBlockingQueue<>(size,
                Comparator.comparingInt(Doctor::getTreatedPatients)
        );

        patientQueue = new LinkedBlockingDeque<>();
    }
}

