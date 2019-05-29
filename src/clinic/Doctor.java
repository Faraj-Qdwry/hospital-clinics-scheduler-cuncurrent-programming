package clinic;

import utilities.*;

import java.util.concurrent.Callable;
import static java.lang.Thread.sleep;

public class Doctor implements Callable<DoctorReport> {
    private int SLEEP_TIME = 15*1000;
    private volatile boolean isAvailable = true;
    private volatile int treatedPatients = 0;
    private Clinic clinic;
    private String id;

    public Doctor(String id,Clinic clinic) {
        this.id = id;
        this.clinic = clinic;
    }

    @Override
    public DoctorReport call() throws Exception {
        DoctorReport doctorReport = new DoctorReport(id,clinic.getId());
        while (Timer.getCurranTime() < Timer.WORK_DURATION) {
            //check if sleep time
            if (treatedPatients == 8){
                isAvailable = false;
                sleep(SLEEP_TIME);
                isAvailable = true;
            }

            // get and consult next patient
            Patient patient = clinic.getNextPatient();
            System.out.println("Doctor : "+id+" At Clinic : "+clinic.getId()+"Consulting patient : " + patient.getId());
            doctorReport.consult(patient);
            sleep(patient.getConsultationTime() * 1000);
            treatedPatients++;
        }
        return doctorReport;
    }

    public Clinic getClinic(){
        return clinic;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
