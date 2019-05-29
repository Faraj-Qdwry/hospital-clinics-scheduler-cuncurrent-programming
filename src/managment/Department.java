package managment;

import utilities.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Department {

    // Initialize threads
    protected static ResFileReader fileReader;
    protected static Scheduler scheduler;
    protected static Timer timer;
    protected static ReportGenerator report;

    // number of doctors available
    protected static int doctorsAvailable;

    // set the number of doctors available
    public void setDoctorsAvailable(int doctorsAvailable){
        this.doctorsAvailable = doctorsAvailable;
    }


    public static void main(String[] args) {
        // Create the file reader thread
        fileReader = new ResFileReader();

        // get and create doctors for the number of doctors, assign them to clinics and run the doctors threads
        ExecutorService doctorsThreads = Executors.newFixedThreadPool(doctorsAvailable);

        for (int doctors = 0; doctors < doctorsAvailable; doctors++) {

        }

        // start the timer thread

        // start a scheduler thread

        // start the file reader thread
    }

    // method to add patient to common patients queue
}
