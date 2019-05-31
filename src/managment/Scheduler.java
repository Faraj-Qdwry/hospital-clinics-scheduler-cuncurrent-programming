package managment;

import clinic.Doctor;
import clinic.Patient;
import utilities.Timer;

import java.util.Iterator;
import java.util.Stack;

import static java.lang.Thread.sleep;

public class Scheduler implements Runnable {

    public volatile static boolean scheduling = true;

    @Override
    public void run() {

<<<<<<< HEAD
        while (Timer.getCurrentMinute() < 240) {
            //while (Department.patientQueue.size() > 0) {
            boolean patientAttended = false;
=======
        while (Timer.getCurranTime() < 240) {
            while (Department.patientQueue.size() > 0) {
                boolean patientAttended = false;
>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90

                Iterator doctorsIterator = Department.doctorsHeap.iterator();

                IteratorLoop:
                while (doctorsIterator.hasNext()) {
                    try {
                        Doctor currentDoctor = Department.doctorsHeap.take();

                        System.out.println("Doc --> " + currentDoctor.getId());

                        if (currentDoctor.isAvailable()) {

                            Patient patient = Department.patientQueue.take();

                            boolean inserted = currentDoctor.getClinic().insertPatient(patient);

                            //while doctor's list is full see next doctor // stack is so to return taken doctors after while finish
                            if (Department.doctorsHeap.peek() != null) {
                                int minTreated = Department.doctorsHeap.peek().getTreatedPatients();

                                Stack<Doctor> tempDocs = new Stack();
                                while (!inserted && currentDoctor.getTreatedPatients() - minTreated < 3) {
                                    tempDocs.push(currentDoctor);
                                    currentDoctor = Department.doctorsHeap.take();
                                    inserted = currentDoctor.getClinic().insertPatient(patient);
                                }
                                tempDocs.iterator().forEachRemaining((doc) -> {
                                    Department.doctorsHeap.add(doc);
                                });
                            }
                        }

                        sleep(1000);
                        Department.doctorsHeap.add(currentDoctor);
                        break IteratorLoop;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
<<<<<<< HEAD

                    sleep(1000);
                    Department.doctorsHeap.add(currentDoctor);
                    break IteratorLoop;

                } catch (InterruptedException e) {
                    e.printStackTrace();
=======
>>>>>>> 0f33571bf4e470cbabfd88d5946fb872c475fd90
                }
            }
        }
    }
}

