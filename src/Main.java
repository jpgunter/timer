import java.time.Duration;
import javax.swing.*;

public class Main {

    public static final void main(String...args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           //ignore
        }

//        String durationString = args[0];
//        Duration duration = Duration.parse(durationString);
//
//
//        //new TimesUpManager().showDialogsIn(Duration.ofMinutes(sleepTime));
//        new TimesUpManager().showDialogsIn(duration);
        new TimerCreatorFrame().setVisible(true);
    }
}
