import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private ServerSocket server;
    private ExecutorService taskPool = Executors.newFixedThreadPool(2);
    private Queue<Future<Boolean>> requestAnswered = new LinkedBlockingQueue<>();


    public static boolean isPortAvailable(int port) {
        try (var ignored = new ServerSocket(port)) { return true; }
        catch (IOException e) { return false; }
    }


    public void start() throws IOException {
        int port = 2134;
        if (!Server.isPortAvailable(port)) {
            System.out.println("Port not available, server not launched");
            return;
        }

        this.server = new ServerSocket(port, 10);
        this.listen();
        this.server.close();
    }


    public void listen() {
        int nextID = 0;
        while (!this.server.isClosed()) {
            try {
                Socket client = this.server.accept();
                System.out.println("Client " + nextID + " connected");
                Future<Boolean> taskResult = this.taskPool.submit(new ClientCallable(client, nextID));
                this.requestAnswered.add(taskResult);
                nextID++;
            } catch (IOException e) {
                System.out.println();
            }
        }
    }
}
