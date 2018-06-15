import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import javax.swing.*;

public class TimesUpDialog extends JFrame {

    private JPanel contentPane;
    private JButton buttonSnooze;
    private JComboBox dropdownSnooze;
    private JButton buttonDismiss;
    private JLabel labelMessage;

    private final DismissFunction dismissFunction;
    private final SnoozeFunction snoozeFunction;

    public TimesUpDialog(String message, DismissFunction dismissFunction, SnoozeFunction snoozeFunction) {
        this.snoozeFunction = snoozeFunction;
        this.dismissFunction = dismissFunction;

        labelMessage.setText(message);

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonDismiss);

        buttonSnooze.addActionListener(e -> onSnooze());

        buttonDismiss.addActionListener(e -> onDismiss());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onDismiss();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onDismiss(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSnooze() {
        String durationString = (String) dropdownSnooze.getSelectedItem();
        Duration duration = Duration.parse(durationString);
        snoozeFunction.snooze(duration);
    }

    private void onDismiss() {
        dismissFunction.dismiss();
    }

    public void doShow() {
        this.pack();
        this.toFront();
    }

    public interface SnoozeFunction {
        void snooze(Duration d);
    }

    public interface DismissFunction {
        void dismiss();
    }

    @Override
    public void toFront() {
        super.setVisible(true);
        int state = super.getExtendedState();
        state &= ~JFrame.ICONIFIED;
        super.setExtendedState(state);
        super.setAlwaysOnTop(true);
        super.toFront();
        super.requestFocus();
        super.setAlwaysOnTop(false);
    }

}
