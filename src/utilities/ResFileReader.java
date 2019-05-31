package utilities;

import clinic.Doctor;
import clinic.Patient;
import managment.Department;

import java.io.FileInputStream;
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
        int doctors = 0;
        boolean readPatients = false;
        int arrivalTime = previousPatientTime;
        int consultationTime = 0;
        ArrayList docs = new ArrayList();
        try {
            try (Scanner inputStream = new Scanner(new FileInputStream("input.txt"))) {
                inputStream.useDelimiter(",");

                while (inputStream.hasNextLine()) {


                    if (!readPatients) {
                        String doctor = inputStream.next();
                        if (doctor.equalsIgnoreCase("Patients")) {
                            System.out.println("............");
                            Department.setDoctorList(docs);
                            Department.setDoctorsAvailable(docs.size());
                            Department.readyDocs();
                            readPatients = true;
                            //continue;
                        } else if (doctor.equalsIgnoreCase("Doctors")) {
                            inputStream.nextLine();
                            doctor = inputStream.next();
                        }

//                        if (readPatients)
//                            continue;
                        System.out.println(doctor);
                        Doctor doc = new Doctor(doctor, null);
                        docs.add(doc);
                        doctors++;
                    }
                    //System.out.println(readPatients);
                    if (readPatients) {
                        arrivalTime = Integer.parseInt(inputStream.next().trim());
                        consultationTime = Integer.parseInt(inputStream.next().trim());

                        //stop reading
                        //if ((consultationTime + Timer.getCurranTime()) > Timer.WORK_DURATION) {
                        //    System.out.println("@@ Hospital closed @@");
                        //    return;
                        //}

                        if (Timer.getCurranTime()==Timer.WORK_DURATION)
                            return;

                        sleep((arrivalTime - Timer.getCurranTime()) * 1000);
                        //previousPatientTime = arrivalTime;

                        Patient patient = new Patient(Integer.toString(++patientId), arrivalTime, consultationTime);

                        Department.patientQueue.put(patient);

                        System.out.println("----------- >>  Patients : " + patientId + " Arrived At : " + arrivalTime + " Consultation Time :" + consultationTime);

                        //scheduling = false;
                    }

                    inputStream.nextLine();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Exception " + e.getMessage());
        }
    }
}