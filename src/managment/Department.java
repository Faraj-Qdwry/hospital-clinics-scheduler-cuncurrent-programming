package managment;

import utilities.*;

public class Department {

    // Initialize threads
    ResFileReader fileReader = new ResFileReader();
    Scheduler scheduler = new Scheduler();
    Timer timer = new Timer();
    ReportGenerator report = new ReportGenerator();


    public static void main(String[] args) throws InterruptedException {

        Timer.startTimer();



        // Create the file reader thread

        // get and create doctors for the number of doctors, assign them to clinics and run the doctors threads

        // start the timer thread

        // start a scheduler thread

        // start the file reader thread
    }
}
