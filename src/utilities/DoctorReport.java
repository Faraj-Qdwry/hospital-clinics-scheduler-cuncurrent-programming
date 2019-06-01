package utilities;

import clinic.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorReport {
    private String docId;
    private String clinicId;
    private List<Patient> patients;
    private double patientsAvgWaitingTime;
    private double patientsAvgConsultationTime;
    private int totalWorkingTime;

    public int getTreatedPatients() {
        return treatedPatients;
    }

    public void setTreatedPatients(int treatedPatients) {
        this.treatedPatients = treatedPatients;
    }

    private int treatedPatients;

    public DoctorReport(String docId, String clinicId) {
        this.docId = docId;
        this.clinicId = clinicId;
        this.patients = new ArrayList<>();
    }

    public void consult(Patient patient) {
        patients.add(patient);
    }

    public String getDocId() {
        return docId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public static String tableHeader(){
     return "Doctor | Clinic | Total Working Time | Treated Patients | Patients Avg Waiting Time | patients Avg Consultation Time";
    }

    @Override
    public String toString() {
        String doctorReport =  docId + "    |    " + clinicId +"     |     " +totalWorkingTime +
                "     |     " + getTreatedPatients() + "     |     " + patientsAvgWaitingTime + "     |     "+patientsAvgConsultationTime;
        try {
            ReportGenerator.addToReport(doctorReport + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorReport;
    }

    public void setPatientsAvgWaitingTime(double patientsAvgWaitingTime) {
        this.patientsAvgWaitingTime = patientsAvgWaitingTime;
    }

    public double getPatientsAvgWaitingTime() {
        return patientsAvgWaitingTime;
    }

    public void setDoctorWorkingTime(int totalWorkingTime) {
        this.totalWorkingTime = totalWorkingTime;
    }

    public double getPatientsAvgConsultationTime() {
        return patientsAvgConsultationTime;
    }

    public void setPatientsAvgConsultationTime(double patientsAvgConsultationTime) {
        this.patientsAvgConsultationTime = patientsAvgConsultationTime;
    }
}