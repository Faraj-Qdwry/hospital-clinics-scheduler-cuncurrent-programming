package managment;

import clinic.Doctor;
import utilities.Timer;

import java.util.Iterator;

public class Scheduler implements Runnable {

    public volatile static boolean scheduling = true;

    @Override
    public void run() {

        while (Timer.getCurranTime() < 240) {
            //while (Department.patientQueue.size() > 0) {
                boolean patientAttended = false;

                Iterator doctorsIterator = Department.doctorsHeap.iterator();
                while (doctorsIterator.hasNext()) {
                    Doctor currentDoctor = null;
                    try {
                        currentDoctor = (Doctor) Department.doctorsHeap.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(currentDoctor.getId());
                    if (currentDoctor.isAvailable()) {
                        //System.out.println("--------" + currentDoctor.getTreatedPatients());
                        //System.out.println("--------" + Department.doctorsHeap.peek().getTreatedPatients());

                        //if (currentDoctor.getTreatedPatients()<

                        //if (currentDoctor.getTreatedPatients() - Department.doctorsHeap.peek().getTreatedPatients() < 3) {
                        boolean inserted = false;
                        try {
                            inserted = currentDoctor.getClinic().insertPatient(Department.patientQueue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //no need to notify cause doctor is just blocked till there is a new patient
                        //currentDoctor.notify();
                        if (inserted) {
                            patientAttended = true;
                            break;
                        }else
                            Department.doctorsHeap.add(currentDoctor);
                        //}
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
}
