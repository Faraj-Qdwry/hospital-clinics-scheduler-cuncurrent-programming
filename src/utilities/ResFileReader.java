package utilities;

import clinic.Clinic;
import clinic.Doctor;
import clinic.Patient;
import managment.Department;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ResFileReader implements Runnable{

    @Override
    public void run() {
        readFile();
    }

    public void readFile() {
        int doctors = 0;
        boolean readPatients = false;
        int arrivalTime = 0;
        int consultationTime = 0;
        ArrayList <Doctor> docs = new ArrayList();
        try{
            try (Scanner inputStream = new Scanner(new FileInputStream("input.txt"))) {
                inputStream.useDelimiter(",");

                while(inputStream.hasNextLine()){


                    if(readPatients == false){
                        String doctor = inputStream.next();
                        if(doctor.equalsIgnoreCase("Patients")){
                            Department.setDoctorList(docs);
                            readPatients = true;
                            return;
                        }
                        if(doctor.equalsIgnoreCase("Doctors")){
                            inputStream.nextLine();
                            doctor = inputStream.next();
                        }
                        System.out.println(doctor);
                        Doctor doc = new Doctor(doctor, null);
                        docs.add(doc);
                        doctors++;
                    }


                    if(readPatients == true) {
                        arrivalTime = Integer.parseInt(inputStream.next());
                        consultationTime = Integer.parseInt(inputStream.next());
                        System.out.println(arrivalTime);
                        System.out.println(consultationTime);


                    }

                    inputStream.nextLine();

                }
            }

        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}