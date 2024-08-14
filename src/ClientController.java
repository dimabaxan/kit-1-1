import java.io.IOException;

public class ClientController implements IClientController {
    private final IServerController serverController;
    private ClientGUI clientGUI;
    private boolean connected = false;

    public ClientController(IServerController serverController) {
        this.serverController = serverController;
    }

    @Override
    public void connectToServer(String ipAddress, int port, String login, String password) throws IOException {
        // Cod pentru a stabili conexiunea la server (simulat în acest exemplu)
        connected = serverController.connectUser(clientGUI);
    }

    @Override
    public void disconnectFromServer() {
        if (connected) {
            serverController.disconnectUser(clientGUI);
            connected = false;
        }
    }

    @Override
    public void sendMessage(String message) {
        if (connected) {
            serverController.message(clientGUI.getName() + ": " + message);
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }
}
