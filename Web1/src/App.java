import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            Logs.error("Server down incorrectly : " + e);
        } finally {
            server.end();
        }
    }
}
