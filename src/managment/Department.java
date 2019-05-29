package managment;

import utilities.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Department {

    // Initialize threads
    ResFileReader fileReader = new ResFileReader();
    Scheduler scheduler = new Scheduler();
    Timer timer = new Timer();
    ReportGenerator report = new ReportGenerator();


    public static void main(String[] args) {
        // Create the file reader thread

        // get and create doctors for the number of doctors, assign them to clinics and run the doctors threads

        // start the timer thread

        // start a scheduler thread

        // start the file reader thread
    }

    // method to add patient to common patients queue
}
