package clinic;

import utilities.*;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class Doctor implements Callable<DoctorReport> {
    private int SLEEP_TIME = 15 * 1000;
    private volatile boolean isAvailable = true;
    private volatile int treatedPatients = 0;
    private Clinic clinic;
    private String id;


    public Doctor(String id, Clinic clinic) {
        this.id = id;
        this.clinic = clinic;
    }

    @Override
    public DoctorReport call() throws Exception {
        DoctorReport doctorReport = new DoctorReport(id, clinic.getId());
        while (Timer.getCurrentMinute() < Timer.WORK_DURATION) {
            //check if sleep time
            if (treatedPatients == 8) {
                isAvailable = false;
                sleep(SLEEP_TIME);
                isAvailable = true;
            } else {
                // get and consult next patient
                Patient patient = clinic.getNextPatient();
<<<<<<< HEAD
                if(patient.getId().equalsIgnoreCase("1")){
                    ReportGenerator.addToReport(System.lineSeparator());
                    ReportGenerator.addToReport("Doctors Report:"+System.lineSeparator());
                }
                String consultingPatient = Timer.getCurrentTime() + " - Dr. " + id + " is consulting patient " + patient.getId() + " at " + clinic.getId() +" - " + treatedPatients + " patients treated";
                System.out.println(consultingPatient);
                ReportGenerator.addToReport(consultingPatient+System.lineSeparator());
=======
                System.out.println("Doctor : " + id + " At Clinic : " + clinic.getId() + "Consulting patient : " + patient.getId() + " $$$ " + id + " Treated " + treatedPatients);
>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90
                doctorReport.consult(patient);
                sleep(patient.getConsultationTime() * 1000);
                treatedPatients++;
            }
        }
        return doctorReport;
    }

    public String getId() {
        return id;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getTreatedPatients() {
        return treatedPatients;
    }

    public Doctor setClinic(Clinic clinic) {
        this.clinic = clinic;
        return this;
    }
}
