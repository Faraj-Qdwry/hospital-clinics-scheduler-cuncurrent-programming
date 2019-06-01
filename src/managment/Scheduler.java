package managment;

import clinic.Doctor;
import clinic.Patient;
import utilities.Timer;

import java.util.Iterator;

import static java.lang.Thread.sleep;

public class Scheduler implements Runnable {

    public volatile static boolean scheduling = true;

    @Override
    public void run() {

        while (Timer.getCurrentMinute() < Timer.WORK_DURATION) {

            Iterator doctorsIterator = Department.doctorsHeap.iterator();

            //IteratorLoop:
            while (doctorsIterator.hasNext()) {
                try {

                    Doctor doctor = (Doctor) doctorsIterator.next();

                    if (doctor.isAvailable()) {

                        Patient patient = Department.patientQueue.takeFirst();
                        boolean patientAssigned = false;

                        // add to this doc if allowed
                        int minTreated = Department.doctorsHeap.peek().getTreatedPatients();

                        if (doctor.getTreatedPatients() - minTreated < 3) {
                            // allowed to add

                            if (doctor.hasRoomForPatient()) {
                                // just add
                                if (doctor.hasTimeFor(patient)) {
                                    doctor.assignPatient(patient);
                                    System.out.println("Patient: " + patient.getId() + " assigned to clinic: " + doctor.getClinic().getId());
                                    patientAssigned = true;
                                } else {
                                    //PEINTS INFINITELY
                                    System.out.println("Dr. " + doctor.getId() + " has no more time for Patient " + patient.getId() + " today!");
                                }
                            }
                        }

                        if (patientAssigned) {
                            // re-heapify
                            Department.doctorsHeap.add(Department.doctorsHeap.take());
                        }
                        else {
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
    }
}