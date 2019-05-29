package clinic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class Doctor implements Callable<Patient> {
    private int SLEEP_TIME = 15;
    private volatile boolean isAvailable = true;
    private volatile int treated = 0;
    private ArrayBlockingQueue<Patient> patients;

    public Doctor(ArrayBlockingQueue<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public Patient call() throws Exception {

        //todo here we keep running through our pateints till System time == 240 then we exit !
        return null;
    }
}
