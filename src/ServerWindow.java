import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final String LOG_PATH = "log.txt";

    List<ClientGUI> clientGUIList;

    JButton btnStart, btnStop;
    JTextArea log;
    boolean work;

    public ServerWindow() {
        clientGUIList = new ArrayList<>();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

    public boolean connectUser(ClientGUI clientGUI) {
        if (!work) {
            return false;
        }
        clientGUIList.add(clientGUI);
        return true;
    }

    public String getLog() throws IOException {
        return readLog();
    }

    public void disconnectUser(ClientGUI clientGUI) {
        clientGUIList.remove(clientGUI);
        if (clientGUI != null) {
            clientGUI.disconnectFromServer();
        }
    }

    public void message(String text) {
        System.out.println(text);
        if (!work) {
            return;
        }
        text += "";
        appendLog(text);
        answerAll(text);
        saveInLog(text);
    }

    private void answerAll(String text) {
        for (ClientGUI clientGUI : clientGUIList) {
            clientGUI.answer(text);
        }
    }

    private void saveInLog(String text) {
        File file = new File(LOG_PATH);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readLog() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(LOG_PATH);

        if (!file.exists()) {
            var status = file.createNewFile();
            if (status) {
                System.out.println("Has created new file " + file.getName());
            }
        }

        try (FileReader reader = new FileReader(file)) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            if (!stringBuilder.isEmpty()) {
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void appendLog(String text) {
        log.append(text + "\n");
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

        btnStart.addActionListener(e -> {
            if (work) {
                appendLog("Сервер уже был запущен");
            } else {
                work = true;
                appendLog("Сервер запущен!");
            }
        });

        btnStop.addActionListener(e -> {
            if (!work) {
                appendLog("Сервер уже был остановлен");
            } else {
                work = false;
                while (!clientGUIList.isEmpty()) {
                    disconnectUser(clientGUIList.getLast());
                }
                appendLog("Сервер остановлен!");
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }
}
