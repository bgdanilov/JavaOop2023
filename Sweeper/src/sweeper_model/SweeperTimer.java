package sweeper_model;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class SweeperTimer {
    Timer timer;
    String yourTimeAtWatch = "00:00";
    int yourTimeAtSeconds;

    public String getYourTimeAtWatch() {
        return yourTimeAtWatch;
    }

    public int getYourTimeAtSeconds() {
        return yourTimeAtSeconds;
    }

    public void start(JLabel time, int startTime) {
        timer = new Timer();
        // В экземпляре Таймера используем Метод, создающий анонимный объект класса TimerTask.
        timer.scheduleAtFixedRate(new TimerTask() {
            int seconds = startTime;
            @Override
            public void run() {
                seconds++;
                yourTimeAtSeconds = seconds;
                yourTimeAtWatch = String.format("%02d:%02d", seconds / 60, seconds % 60);
                time.setText(yourTimeAtWatch);
            }
        }, 0, 1000);
    }

    public void stop() {
       // setYourTimeInLine(yourTimeInLine);

        if (timer != null) {
            timer.cancel();
        }

        timer = null;
    }
}