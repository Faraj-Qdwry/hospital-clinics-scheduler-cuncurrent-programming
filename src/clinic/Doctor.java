package clinic;

import managment.Department;
import utilities.*;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class Doctor implements Callable<DoctorReport> {
    private int BREAK_TIME = 15;
    private volatile boolean isAvailable = true;
    private volatile int treatedPatients = 0;
    private volatile int totalWorkingTime = 0;
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
                sleep(BREAK_TIME * 1000);
                isAvailable = true;
            } else {
                // get and consult next patient
                Patient patient = clinic.getNextPatient();

//                if (patient.getId().equalsIgnoreCase("1")) {
//                    ReportGenerator.addToReport(System.lineSeparator());
//                    ReportGenerator.addToReport("Doctors Report Log :" + System.lineSeparator());
//                }
//                String consultingPatient = Timer.getCurrentTime() + " - Dr. " + id + " is consulting patient " + patient.getId() + " at " + clinic.getId() + " - " + treatedPatients + " patients treated";
//                System.out.println(consultingPatient);
//                ReportGenerator.addToReport(consultingPatient + System.lineSeparator());

                doctorReport.consult(patient);
                sleep(patient.getConsultationTime() * 1000);
                treatedPatients++;
            }
        }
        doctorReport.setPatientsAvgWaitingTime(clinic.getPatientsAvgWaitingTime());
        doctorReport.setDoctorWorkingTime(totalWorkingTime);
        doctorReport.setTreatedPatients(treatedPatients);
        doctorReport.setPatientsAvgConsultationTime(clinic.getPatientsAvgConsultationTime());
        return doctorReport;
    }

    public String getId() {
        return id;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void assignPatient(Patient patient) {
        totalWorkingTime += patient.getConsultationTime();
        clinic.insertPatient(patient);

        //todo make sure
        if (treatedPatients % 8 == 0 && Department.isOpen) {
            totalWorkingTime += BREAK_TIME;
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean hasRoomForPatient() {
        return !clinic.isFull();
    }

    public int getTreatedPatients() {
        return treatedPatients;
    }

    public Doctor setClinic(Clinic clinic) {
        this.clinic = clinic;
        return this;
    }

    public boolean hasTimeFor(Patient patient) {
        return Timer.WORK_DURATION > (patient.getConsultationTime() + totalWorkingTime);
    }
}
