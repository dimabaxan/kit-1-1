import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ClientGUI extends JFrame {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private final IClientController clientController;
    private String name;

    JTextArea log;
    JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btnLogin, btnSend;
    JPanel headerPanel;

    public ClientGUI(IClientController clientController, String name) {
        this.clientController = clientController;
        this.clientController.setClientGUI(this);

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        createPanel(name);
        setVisible(true);
    }

    public void answer(String text) {
        appendLog(text);
    }

    public void disconnectFromServer() {
        headerPanel.setVisible(true);
        appendLog("Вы были отключены от сервера!");
    }

    private void appendLog(String text) {
        log.append(text + "\n");
    }

    private void createPanel(String name) {
        add(createHeaderPanel(name), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel(String name) {
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField(name);
        password = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(e -> {
            try {
                clientController.connectToServer(
                        tfIPAddress.getText(),
                        Integer.parseInt(tfPort.getText()),
                        tfLogin.getText(),
                        new String(password.getPassword())
                );
                headerPanel.setVisible(false);
                this.name = tfLogin.getText();
            } catch (IOException ex) {
                appendLog("Подключение не удалось");
            }
        });

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    clientController.sendMessage(tfMessage.getText());
                    tfMessage.setText("");
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(e -> {
            clientController.sendMessage(tfMessage.getText());
            tfMessage.setText("");
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            clientController.disconnectFromServer();
        }
        super.processWindowEvent(e);
    }
}
