package utilities;

import clinic.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorReport {
    private String docId;
    private String clinicId;
    private List<Patient> patients;

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

    @Override
    public String toString() {
        String doctorReport = "doctor "+docId+" at clinic "+clinicId+" patients "+ patients.size();
        System.out.println("-----------------");
        try {
            ReportGenerator.addToReport(doctorReport + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorReport;
    }
}