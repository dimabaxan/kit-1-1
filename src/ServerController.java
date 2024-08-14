import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerController implements IServerController {
    private final IRepository repository;
    private final List<ClientGUI> clientGUIList = new ArrayList<>();
    private boolean work;

    public ServerController(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean connectUser(ClientGUI clientGUI) {
        if (!work) {
            return false;
        }
        clientGUIList.add(clientGUI);
        return true;
    }

    @Override
    public void disconnectUser(ClientGUI clientGUI) {
        clientGUIList.remove(clientGUI);
        if (clientGUI != null) {
            clientGUI.disconnectFromServer();
        }
    }

    @Override
    public void message(String text) {
        if (!work) {
            return;
        }
        try {
            repository.saveInLog(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        answerAll(text);
    }

    @Override
    public String getLog() throws IOException {
        return repository.readLog();
    }

    private void answerAll(String text) {
        for (ClientGUI clientGUI : clientGUIList) {
            clientGUI.answer(text);
        }
    }

    public void startServer() {
        if (!work) {
            work = true;
            System.out.println("Сервер запущен!");
        }
    }

    public void stopServer() {
        if (work) {
            work = false;
            for (ClientGUI clientGUI : new ArrayList<>(clientGUIList)) {
                disconnectUser(clientGUI);
            }
            System.out.println("Сервер остановлен!");
        }
    }
}