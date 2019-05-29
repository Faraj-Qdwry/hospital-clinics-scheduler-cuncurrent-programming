package clinic;

import java.util.concurrent.ArrayBlockingQueue;

public class Clinic {
//    Lock lock = new ReentrantLock();
//    Condition isEmpty = lock.newCondition();
//    Condition isFull = lock.newCondition();

    private int PATENTS_LIMIT = 3;
    private ArrayBlockingQueue<Patient> patients;
    private Doctor doctor;

    public Clinic() {
        patients = new ArrayBlockingQueue(PATENTS_LIMIT, true);
        this.doctor = new Doctor(patients);
    }


//    public Patient getNextPateint() throws InterruptedException {
//        return patients.take();
//    }

    /**
     * @param patient is inserted and true returned if queue is not full else false is returned
     */
    public boolean insertPateint(Patient patient) {
        return patients.offer(patient);
    }
}
