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

            //Doctor curruntDoctor = Department.doctorsHeap.

            /*while (Department.patientQueue.size() > 0) {
                boolean patientAttended = false;
                Iterator doctorsIterator = Department.doctorsHeap.iterator();

                IteratorLoop:
                while (doctorsIterator.hasNext()) {
                    try {
                        Doctor currentDoctor = Department.doctorsHeap.take();

                        System.out.println("Doc --> " + currentDoctor.getId() + " is Available -> " + currentDoctor.isAvailable());
                        boolean inserted = false;

                        //if (currentDoctor.isAvailable()) {

                        Patient patient = Department.patientQueue.take();

                        inserted = currentDoctor.getClinic().insertPatient(patient);

                        if (inserted)
                            Department.doctorsHeap.add(currentDoctor);

                        //while doctor's list is full see next doctor // stack is so to return taken doctors after while finish
                        if (!inserted && Department.doctorsHeap.peek() != null) {
                            int minTreated = Department.doctorsHeap.peek().getTreatedPatients();

                            // only for when doc is not inserted
                            Stack<Doctor> tempDocs = new Stack();
                            CLINIC_ASSIGNER:
                            while (!inserted && currentDoctor.getTreatedPatients() - minTreated < 3) {
                                //System.out.println("/* searching Available Doc , minTrated : "+ minTreated+" "+inserted);
                                tempDocs.push(currentDoctor);
                                currentDoctor = Department.doctorsHeap.poll();
                                if (currentDoctor == null)
                                    break CLINIC_ASSIGNER;
                                inserted = currentDoctor.getClinic().insertPatient(patient);
                            }
                            System.out.println("Inserted : " + inserted);

                            tempDocs.iterator().forEachRemaining((doc) ->
                                    Department.doctorsHeap.add(doc)
                            );

                            //System.out.println("++++ Doc heap size : "+ Department.doctorsHeap.size());
                        } else if (inserted) {
                            //todo put back to common waiting queue
                            Department.patientQueue.offer(patient);
                        }

                        //else
                        //    System.out.println("-------------this shit is null-----------");

                        //}
                        sleep(1000);
                        //if (!inserted) Department.doctorsHeap.add(currentDoctor);
                        System.out.println("++++ Doc heap size : " + Department.doctorsHeap.size());
                        System.out.println("it's a break ***");
                        break IteratorLoop;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            }*/
        }
    }
}