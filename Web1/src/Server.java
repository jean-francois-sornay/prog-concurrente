import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.*;

public class Server {
    private ServerSocket server;
    private final ExecutorService taskPool = Executors.newFixedThreadPool(2);
    // May serve to know if the client request has been well answered
    private final Queue<Future<Integer>> requestAnswered = new LinkedBlockingQueue<>();


    public static boolean isPortAvailable(int port) {
        try (var ignored = new ServerSocket(port)) { return true; }
        catch (IOException e) { return false; }
    }


    public void start() throws IOException {
        int port = 2134;
        if (!Server.isPortAvailable(port)) {
            Logs.error("Server Error, port not available, server not launched");
            return;
        }

        this.server = new ServerSocket(port, 10);
        this.listen();
        this.end();
    }


    public void listen() throws IOException {
        int nextID = 0;
        while (!this.server.isClosed()) {
            Socket client = this.server.accept();
            Future<Integer> taskResult = this.taskPool.submit(new ClientCallable(client, nextID));
            this.requestAnswered.add(taskResult);
            Logs.info("[Client " + nextID + "] added to task pool");
            nextID++;
        }
    }


    public void end() {
        this.taskPool.shutdown();
        this.requestAnswered.clear();
        try {
            this.server.close();
        } catch (IOException e) {
            Logs.error("Server ended incorrectly" + e);
        }
    }
}
