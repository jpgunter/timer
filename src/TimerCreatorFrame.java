import java.time.Duration;
import javax.swing.*;

public class TimerCreatorFrame extends JFrame{
    private JTextField textFieldDuration;
    private JPanel contentPane;
    private JButton buttonCreateTimer;
    private JLabel labelError;
    private JTextField textFieldMessage;

    public TimerCreatorFrame(){
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(contentPane);

        this.pack();

        labelError.setText("");

        buttonCreateTimer.addActionListener(e -> createTimer());
    }

    public void createTimer(){
        try {
            String message = textFieldMessage.getText();
            Duration duration = Duration.parse(textFieldDuration.getText());
            new TimesUpManager(message).showDialogsIn(duration);
            labelError.setText("Timer will go off in " + duration.toString());
        } catch (Exception e){
            labelError.setText("Failed to parse duration");
        }
    }
}
