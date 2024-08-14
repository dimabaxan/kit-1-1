import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private final IServerController serverController;
    JTextArea log;
    JButton btnStart, btnStop;

    public ServerGUI(IServerController serverController) {
        this.serverController = serverController;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(e -> serverController.startServer());
        btnStop.addActionListener(e -> serverController.stopServer());

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }
}
