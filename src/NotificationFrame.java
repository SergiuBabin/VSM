import javax.swing.*;


public class NotificationFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JTextArea notificationsArea;

    public NotificationFrame(User user) {
        super("Notification");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        notificationsArea = new JTextArea();

        user.listNotifications(notificationsArea);

        notificationsArea.setEditable(false);

        add(notificationsArea);
    }

}
