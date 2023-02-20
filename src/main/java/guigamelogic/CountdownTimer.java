package guigamelogic;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class CountdownTimer {
    private static long startTime;
    private static long endTime;
    private static boolean hasFinished = false;

    public static void startTimer(int minutes) {
        int seconds = 0;
        startTime = System.currentTimeMillis();
        endTime = startTime + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
    }

    public static String getTimeRemaining() {
        CountdownTimer.startTimer(5);
        long remainingTime = endTime - System.currentTimeMillis();
        long minutesRemaining = TimeUnit.MILLISECONDS.toMinutes(remainingTime);
        long secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(minutesRemaining);
        if(remainingTime<=0){
            hasFinished = true;
        }

        return String.format("%02d:%02d", minutesRemaining, secondsRemaining);
    }


//    public static JLabel getTimeRemaining() {
//        long remainingTime = endTime - System.currentTimeMillis();
//        long minutesRemaining = TimeUnit.MILLISECONDS.toMinutes(remainingTime);
//        long secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(minutesRemaining);
//        if(remainingTime<=0){
//            hasFinished = true;
//        }
//        JLabel label = new JLabel(String.format("%02d:%02d", minutesRemaining, secondsRemaining);
//        return label;
//    }


    public static boolean countdownFinished(){
        return hasFinished;
    }

}
