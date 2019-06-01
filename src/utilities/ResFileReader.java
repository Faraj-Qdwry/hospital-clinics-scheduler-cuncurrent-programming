package utilities;

import clinic.Doctor;
import clinic.Patient;
import managment.Department;
import managment.Scheduler;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ResFileReader implements Runnable {

    public static volatile int patientNumber = 0;

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
        ArrayList<Doctor> doctorList = new ArrayList();
        try {
            try (Scanner inputStream = new Scanner(new FileInputStream("best input.txt"))) {
                inputStream.useDelimiter(",");

                while (inputStream.hasNextLine()) {

                    // if not signalled to read patients yet, read number of doctors
                    if (!readPatients) {
                        String doctor = inputStream.next();
                        if (doctor.equalsIgnoreCase("Patients")) {
                            Department.setDoctorListFromFile(doctorList);
                            Department.setDoctorsAvailable(doctorList.size());
                            Department.setDoctorsReady();

//                            ReportGenerator.addToReport("Doctors Available: " + doctorsAvailable + System.lineSeparator());
//                            for (Doctor currentDoctor : doctorList) {
//                                ReportGenerator.addToReport("Doctor: " + currentDoctor.getId() + System.lineSeparator());
//                            }

                            Department.setDoctorListFromFile(doctorList);
                            Department.setDoctorsAvailable(doctorList.size());
                            Department.setDoctorsReady();
                            readPatients = true;
                        } else if (doctor.equalsIgnoreCase("Doctors")) {
                            inputStream.nextLine();
                            doctor = inputStream.next();
                        }

//                        if (readPatients)
//                            continue;

                        System.out.println(doctor);
                        Doctor doc = new Doctor(doctor, null);
                        doctorList.add(doc);
                        doctorsAvailable++;
                    }
                    if (readPatients) {
                        arrivalTime = Integer.parseInt(inputStream.next().trim());
                        consultationTime = Integer.parseInt(inputStream.next().trim());

                        if (Timer.getCurrentMinute() == Timer.WORK_DURATION || Scheduler.isAllDoctorsFull) {
                            System.out.println("*******************************************************");
                            System.out.println("******************** Hospital Closed ******************");
                            System.out.println("*******************************************************");
                            Department.isOpen = false;
                            return;
                        }

                        if (Timer.getCurrentMinute() == 0) {
                            sleep((arrivalTime - Timer.getCurrentMinute()) * 2000);
                        } else {
                            sleep((arrivalTime - Timer.getCurrentMinute()) * 1000);
                        }

                        Patient patient = new Patient(Integer.toString(++patientId), arrivalTime, consultationTime);
                        Department.patientQueue.addLast(patient);
                        patientNumber++;
                        System.out.println("Patient: " + patientId + " Arrived At: " + Timer.getCurrentTime() + " Consultation Time: " + consultationTime + " minutes");
                    }

                    if (inputStream.hasNextLine())
                        inputStream.nextLine();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}