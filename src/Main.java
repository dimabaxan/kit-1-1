public class Main {
    public static void main(String[] args) {
        IRepository repository = new ServerRepository();
        IServerController serverController = new ServerController(repository);
        ServerGUI serverGUI = new ServerGUI(serverController);

        IClientController clientController1 = new ClientController(serverController);
        ClientGUI clientGUI1 = new ClientGUI(clientController1, "Jora");

        IClientController clientController2 = new ClientController(serverController);
        ClientGUI clientGUI2 = new ClientGUI(clientController2, "Patribok");
    }
}
