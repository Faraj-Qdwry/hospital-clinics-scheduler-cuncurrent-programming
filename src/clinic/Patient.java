package clinic;

public class Patient {

    private String id ;
    private int waitingTime;
    private int consultationTime;

    public Patient(String id, int waitingTime, int consultationTime) {
        this.id = id;
        this.waitingTime = waitingTime;
        this.consultationTime = consultationTime;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getConsultationTime() {
        return consultationTime;
    }

    public void setConsultationTime(int consultationTime) {
        this.consultationTime = consultationTime;
    }
}
