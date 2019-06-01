package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                        System.out.println("Time: " + getCurrentTime());
                        sleep(1000);
                        T++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
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

    public static int getCurrentMinute() {
        return T;
    }

    public static String getCurrentTime() throws ParseException {
        String myTime = "8:00";
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(myTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, T);
        String newTime = df.format(cal.getTime());
        return newTime;
    }
}