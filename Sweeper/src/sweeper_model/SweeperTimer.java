package sweeper_model;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class SweeperTimer {
    Timer timer;
    String yourTimeInLine = "00:00";
    int yourTimeInSeconds;

    public void setYourTimeInLine(String timeLine) {
        yourTimeInLine = timeLine;
    }

    public String getYourTimeInLine() {
        return yourTimeInLine;
    }

    public int getYourTimeInSeconds() {
        return yourTimeInSeconds;
    }

    public void start(JLabel time, int startTime) {
        timer = new Timer();

        // В экземпляре Таймера используем Метод, создающий анонимный объект класса TimerTask.
        timer.scheduleAtFixedRate(new TimerTask() {
            int sec = startTime;

            @Override
            public void run() {
                sec++;
                yourTimeInSeconds = sec;
                yourTimeInLine = String.format("%02d:%02d", sec / 60, sec % 60);
                time.setText(yourTimeInLine);
            }
        }, 0, 1000);
    }

    public void stop() {
        setYourTimeInLine(yourTimeInLine);

        if (timer != null) {
            timer.cancel();
        }

        timer = null;
    }
}