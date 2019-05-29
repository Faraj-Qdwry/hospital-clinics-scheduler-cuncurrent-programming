package clinic;

import java.util.concurrent.ArrayBlockingQueue;

public class Clinic {
//    Lock lock = new ReentrantLock();
//    Condition isEmpty = lock.newCondition();
//    Condition isFull = lock.newCondition();

    private int PATENTS_LIMIT = 3;
    private ArrayBlockingQueue<Patient> patients;
    private String id;

    public Clinic(String id) {
        this.id = id;
        patients = new ArrayBlockingQueue(PATENTS_LIMIT, true);
    }

    /**
     * gives next patient , if queue is empty it waits */
    public Patient getNextPatient() throws InterruptedException {
        return patients.take();
    }


    /**
     * @param patient is inserted and true returned if queue is not full else false is returned */
    public boolean insertPateint(Patient patient) {
        return patients.offer(patient);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
