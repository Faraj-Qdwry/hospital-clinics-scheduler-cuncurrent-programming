package managment;

import clinic.Doctor;
import clinic.Patient;
import utilities.DoctorReport;
import utilities.Timer;

import javax.print.Doc;
import java.util.Iterator;
import java.util.Stack;

import static java.lang.Thread.sleep;

public class Scheduler implements Runnable {

    public volatile static boolean scheduling = true;

    @Override
    public void run() {

        while (Timer.getCurranTime() < 240) {
            //while (Department.patientQueue.size() > 0) {
            boolean patientAttended = false;

            Iterator doctorsIterator = Department.doctorsHeap.iterator();

            IteratorLoop:
            while (doctorsIterator.hasNext()) {
                try {
                    Doctor currentDoctor = Department.doctorsHeap.take();

                    System.out.println("Doc --> " + currentDoctor.getId());

                    if (currentDoctor.isAvailable()) {
                        //if (currentDoctor.getTreatedPatients()<
                        //if (currentDoctor.getTreatedPatients() - Department.doctorsHeap.peek().getTreatedPatients() < 3) {

                        Patient patient = Department.patientQueue.take();

                        boolean inserted = currentDoctor.getClinic().insertPatient(patient);

                        //while doc is full see next doctor // stack is so to return taken doctors after while finish

                        //assert Department.doctorsHeap.peek() != null;
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

                        //no need to notify cause doctor is just blocked till there is a new patient
                        //currentDoctor.notify();

//                        if (inserted) {
//                            patientAttended = true;
//                            break IteratorLoop;
//                        }
                    }

                    sleep(1000);
                    Department.doctorsHeap.add(currentDoctor);
                    System.out.println("*****heap size*******" + Department.doctorsHeap.size());
                    break IteratorLoop;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        //todo heapify doctorsHeap
        //Department.doctorsHeap.add(Department.doctorsHeap.remove());

//                if (!patientAttended) {
//                    while (!scheduling) {
//                    }
//                }
    }
    //}
}

