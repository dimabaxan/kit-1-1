import java.io.IOException;

public interface IClientController {
    void connectToServer(String ipAddress, int port, String login, String password) throws IOException;
    void disconnectFromServer();
    void sendMessage(String message);
    boolean isConnected();

    void setClientGUI(ClientGUI clientGUI);
}
