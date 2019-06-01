package managment;

import clinic.Doctor;
import clinic.Patient;
import utilities.Timer;

import java.util.Iterator;

import static java.lang.Thread.sleep;

public class Scheduler implements Runnable {

    public volatile static boolean scheduling = true;
    public volatile static boolean isAllDoctorsFull = false;


    @Override
    public void run() {

        System.out.println("/////////////////////////// Start Scheduling //////////////////////////");
        while (Timer.getCurrentMinute() < Timer.WORK_DURATION && !isAllDoctorsFull) {

            if (Department.doctorsHeap.isEmpty())
                isAllDoctorsFull = true;

            Iterator doctorsIterator = Department.doctorsHeap.iterator();

            //IteratorLoop:
            while (doctorsIterator.hasNext()) {
                try {

                    Doctor doctor = (Doctor) doctorsIterator.next();

                    if (doctor.isAvailable()) {

                        Patient patient = Department.patientQueue.takeFirst();
                        boolean patientAssigned = false;

                        // add to this doc if allowed
                        int minDoctorTreatedPatients = Department.doctorsHeap.peek().getTreatedPatients();

                        if (doctor.getTreatedPatients() - minDoctorTreatedPatients < 3) {
                            /**
                             * allowed to add patient to this doctor
                             * as this doctor is not different from minDoctorTreatedPatients by 3
                             * */

                            if (doctor.hasRoomForPatient()) {
                                // just add
                                if (doctor.hasTimeFor(patient)) {
                                    doctor.assignPatient(patient);
                                    System.out.println("Patient: " + patient.getId() + " assigned to clinic: " + doctor.getClinic().getId());
                                    patientAssigned = true;
                                } else {
                                    /** Remove doctor from heap as he can't accept anymore patients*/
                                    boolean remove = Department.doctorsHeap.remove(doctor);
                                    System.out.println("Dr. " + doctor.getId() + "**" + remove + " |||| has no more time for Patient |||| " + patient.getId() + " today!");
                                }
                            }
                        }

                        if (patientAssigned) {
                            // re-heapify
                            Department.doctorsHeap.add(Department.doctorsHeap.take());
                        } else {
                            Department.patientQueue.addFirst(patient);
                            if (!patient.isWaiting()) {
                                patient.goToCommonWaiting();
                                System.out.println("Patient: " + patient.getId() + " will go to common waiting room");
                            }
                        }
                    } else {
                        //System.out.println("Doctor : " + doctor.getId() + " is in 15 mins a break");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("/////////////////////////// End Scheduling //////////////////////////");
    }
}