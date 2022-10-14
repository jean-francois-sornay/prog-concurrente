import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public interface Server {
    void start() throws Exception;
    void end() throws Exception;

    static boolean isPortAvailable(int port) {
        try (var ignored = new ServerSocket(port)) { return true; }
        catch (IOException e) { return false; }
    }
}
