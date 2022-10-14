import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class InternalServer implements Server {
    private ServerSocket server;
    private final Server serverToModerate;
    private final int port;


    public InternalServer(int port, Server server) {
        this.port = port;
        this.serverToModerate = server;
    }


    public void start() throws Exception {
        if (!Server.isPortAvailable(this.port)) {
            Logs.error("[ADMIN] Master server port not available, server not launched");
            return;
        }

        this.server = new ServerSocket(this.port, 1);
        this.listen();
    }


    private void listen() throws Exception {
        Socket client = this.server.accept();
        this.serverToModerate.end();
        this.server.close();
        Logs.info("[ADMIN] Servers have been shutdown");
    }


    public void end() {
        if (this.server.isClosed())
            return;

        try { this.server.close(); }
        catch (IOException e) { Logs.error("[ADMIN] Master server had an error while shutdown " + e); }
        Logs.error("[ADMIN] Master server has been shutdown");
    }
}
