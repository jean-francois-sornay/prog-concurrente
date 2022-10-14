import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class PublicServer implements Server {
    private ServerSocket server;
    private final ExecutorService taskPool = Executors.newFixedThreadPool(2);
    private final ServerObserver observer = new ServerObserver();
    private final int port;


    public PublicServer(int port) {
        this.port = port;
    }


    public void start() {
        if (!Server.isPortAvailable(this.port)) {
            Logs.error("Server Error, port not available, server not launched");
            return;
        }

        try { this.server = new ServerSocket(this.port, 10); }
        catch (IOException e) { Logs.warning("Server error while booting " + e); }
        try { this.listen(); }
        catch (IOException e) { Logs.warning("Server error while listening " + e); }
    }


    private void listen() throws IOException {
        int nextID = 0;
        while (!this.server.isClosed()) {
            Socket client = this.server.accept();
            this.taskPool.submit(new ClientCallable(client, nextID, this.observer));
            nextID++;
        }
    }


    public void end() {
        if (this.server.isClosed())
            return;

        this.taskPool.shutdownNow();
        try { this.server.close(); }
        catch (IOException e) { Logs.error("Server ended incorrectly" + e); }
    }
}
