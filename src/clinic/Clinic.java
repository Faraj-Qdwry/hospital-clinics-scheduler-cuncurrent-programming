package clinic;

import utilities.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Clinic {

    private int WAITING_PATENTS_LIMIT = 3;
    private ArrayBlockingQueue<Patient> patients;
    private List<Integer> patienAVGwaiting;
    private String id;

    public Clinic(String id) {
        this.id = id;
        patients = new ArrayBlockingQueue(WAITING_PATENTS_LIMIT, true);
        patienAVGwaiting = new ArrayList<>();
    }

    /**
     * gives next patient , if queue is empty it waits
     */
    public Patient getNextPatient() throws InterruptedException {
        Patient patient = patients.take();
        patienAVGwaiting.add(Timer.getCurrentMinute() - patient.getArrivalTime());
        return patient;
    }


    /**
     * @param patient is inserted and true returned if queue is not full else false is returned
     */
    public boolean insertPatient(Patient patient) {
        return patients.offer(patient);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFull() {
        return patients.size() == WAITING_PATENTS_LIMIT;
    }

    public int getCount() {
        return patients.size();
    }

    public double getPatientsAvgWaitingTime() {
        return patienAVGwaiting
                .stream()
                .mapToInt((n) -> n)
                .summaryStatistics()
                .getAverage();
    }
}
