package utilities;

import static java.lang.Thread.sleep;

public class Timer {

    private static Timer timer;
    private static Thread worker;
    public static int WORK_DURATION = 240;
    private static volatile int T = 0;
    private static volatile boolean active = true;

    static {
        timer = new Timer();
        worker = new Thread(() -> {
            while (T < WORK_DURATION) {
                if (!active)
                    return;
                else
                    try {
                        sleep(1000);
                        T++;
                        System.out.println("Time : " + T);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    public static synchronized void startTimer() {
        if (T > 0) {
            System.out.println("Timer Already Started ! Call Stop(); to start it Again");
        } else {
            active = true;
            worker.start();
        }
    }

//    public static synchronized int stopTimer() {
//        active = false;
//        int scopedAt = T;
//        T = WORK_DURATION;
//        return scopedAt;
//    }

    public static int getCurranTime() {
        return T;
    }
}
