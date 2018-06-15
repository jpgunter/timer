import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TimesUpManager {

    public static final int CHECK_INTERVAL_MILLIS = 1000;
    private List<TimesUpDialog> timesUpDialogList;
    private String message;

    public TimesUpManager(String message) {
        this.message = message;
        timesUpDialogList = new ArrayList<>();
    }

    public void showDialogsIn(Duration duration) {
        long endTime = System.currentTimeMillis() + duration.toMillis();
        (new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(CHECK_INTERVAL_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(System.currentTimeMillis() > endTime){
                    showDone();
                    break;
                }
            }
        })).start();
    }

    private void showDone() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
        for (GraphicsDevice graphicsDevice : graphicsDevices) {
            TimesUpDialog timesUpDialog = new TimesUpDialog(message, this::dismiss, this::snooze);
            timesUpDialogList.add(timesUpDialog);
            Rectangle screen = graphicsDevice.getDefaultConfiguration().getBounds();
            int midX = (screen.width / 2) - (timesUpDialog.getWidth() / 2);
            int midY = (screen.height / 2) - (timesUpDialog.getHeight() / 2);

            timesUpDialog.setLocation(screen.getLocation().x + midX, screen.getLocation().y + midY);
            timesUpDialog.doShow();
        }
    }

    public void snooze(Duration snoozeDuration) {
        timesUpDialogList.stream().forEach(d -> d.dispose());
        timesUpDialogList = new ArrayList<>();

        showDialogsIn(snoozeDuration);
    }

    public void dismiss() {
        timesUpDialogList.stream().forEach(d -> d.dispose());
        timesUpDialogList = new ArrayList<>();
    }
}
