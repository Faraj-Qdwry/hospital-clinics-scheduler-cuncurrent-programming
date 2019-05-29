package clinic;

public class Patient {

    private int id ;
    private int waiting_time;
    private int consultation_time;

    public Patient(int id, int waiting_time, int consultation_time) {
        this.id = id;
        this.waiting_time = waiting_time;
        this.consultation_time = consultation_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getConsultation_time() {
        return consultation_time;
    }

    public void setConsultation_time(int consultation_time) {
        this.consultation_time = consultation_time;
    }
}
