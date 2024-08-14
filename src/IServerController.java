import java.io.IOException;

public interface IServerController {
    boolean connectUser(ClientGUI clientGUI);
    void disconnectUser(ClientGUI clientGUI);
    void message(String text);
    String getLog() throws IOException;

    void startServer();

    void stopServer();
}