import java.io.*;

public class ServerRepository implements IRepository {
    private static final String LOG_PATH = "log.txt";

    @Override
    public void saveInLog(String text) throws IOException {
        try (FileWriter writer = new FileWriter(new File(LOG_PATH), true)) {
            writer.write(text + "\n");
        }
    }

    @Override
    public String readLog() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(LOG_PATH);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileReader reader = new FileReader(file)) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
        }

        return stringBuilder.toString();
    }
}
