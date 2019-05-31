package utilities;

import clinic.Doctor;
import clinic.Patient;
import managment.Department;

import java.io.FileInputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ResFileReader implements Runnable {

    @Override
    public void run() {
        readFile();
    }

    public void readFile() {
        int patientId = 0;
        int previousPatientTime = 0;
        int doctorsAvailable = 0;
        boolean readPatients = false;
        int arrivalTime = previousPatientTime;
        int consultationTime = 0;
<<<<<<< HEAD
        ArrayList <Doctor> docs = new ArrayList();
=======
        ArrayList doctorList = new ArrayList();

>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90
        try {
            try (Scanner inputStream = new Scanner(new FileInputStream("input.txt"))) {
                inputStream.useDelimiter(",");

                while (inputStream.hasNextLine()) {

                    // if not signalled to read patients yet, read number of doctors
                    if (!readPatients) {
                        String doctor = inputStream.next();
                        if (doctor.equalsIgnoreCase("Patients")) {
                            System.out.println("............");
<<<<<<< HEAD
                            Department.setDoctorList(docs);
                            Department.setDoctorsAvailable(docs.size());
                            Department.readyDocs();
                            ReportGenerator.addToReport("Doctors Available: "+ doctors+System.lineSeparator());
                            for(Doctor currentDoctor : docs) {
                                ReportGenerator.addToReport("Doctor: "+currentDoctor.getId()+System.lineSeparator());
                            }

=======
                            Department.setDoctorListFromFile(doctorList);
                            Department.setDoctorsAvailable(doctorList.size());
                            Department.setDoctorsReady();
>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90
                            readPatients = true;
                        } else if (doctor.equalsIgnoreCase("Doctors")) {
                            inputStream.nextLine();
                            doctor = inputStream.next();
                        }

<<<<<<< HEAD
//                        if (readPatients)
//                            continue;
=======
                        System.out.println(doctor);
>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90
                        Doctor doc = new Doctor(doctor, null);
                        doctorList.add(doc);
                        doctorsAvailable++;
                    }
                    if (readPatients) {
                        arrivalTime = Integer.parseInt(inputStream.next().trim());
                        consultationTime = Integer.parseInt(inputStream.next().trim());

<<<<<<< HEAD
                        if (Timer.getCurrentMinute()==Timer.WORK_DURATION)
                            return;

                        if(Timer.getCurrentMinute() == 0){
                            sleep((arrivalTime - Timer.getCurrentMinute()) * 2000);
                        }
                        else {
                            sleep((arrivalTime - Timer.getCurrentMinute()) * 1000);
                        }
                        //previousPatientTime = arrivalTime;
=======

                        if (Timer.getCurranTime() >= Timer.WORK_DURATION)
                            return;

                        sleep((arrivalTime - Timer.getCurranTime()) * 1000);
>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90

                        Patient patient = new Patient(Integer.toString(++patientId), arrivalTime, consultationTime);

                        Department.patientQueue.put(patient);

                        System.out.println("Patient: " + patientId + " Arrived At: " + Timer.getCurrentTime() + " Consultation Time:" + consultationTime);

                    }

                    inputStream.nextLine();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}