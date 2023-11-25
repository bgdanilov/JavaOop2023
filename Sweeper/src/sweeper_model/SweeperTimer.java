package sweeper_model;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class SweeperTimer {
    Timer timer;
    String yourTime = "00:00";

    public void setYourTime(String timeLine) {
        yourTime = timeLine;
    }

    public String getYourTime() {
        return yourTime;
    }

    public void start(JLabel time, int startTime) {
        timer = new Timer();

        // В экземпляре Таймера используем Метод, создающий анонимный объект класса TimerTask.
        timer.scheduleAtFixedRate(new TimerTask() {
            int sec = startTime;

            @Override
            public void run() {
                sec++;
                yourTime = String.format("%02d:%02d", sec / 60, sec % 60);
                time.setText(yourTime);
            }
        }, 0, 1000);
    }

    public void stop() {
        setYourTime(yourTime);

        if (timer != null) {
            timer.cancel();
        }

        timer = null;
    }
}