package clinic;

public class Patient {

    private String id ;
    private int arrivalTime;
    private int consultationTime;

    public Patient(String id, int arrivalTime, int consultationTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.consultationTime = consultationTime;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getConsultationTime() {
        return consultationTime;
    }

    public void setConsultationTime(int consultationTime) {
        this.consultationTime = consultationTime;
    }
}
