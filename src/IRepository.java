import java.io.IOException;

public interface IRepository {
    void saveInLog(String text) throws IOException;
    String readLog() throws IOException;
}