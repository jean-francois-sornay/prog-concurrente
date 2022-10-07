import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class ClientCallable implements Callable<Boolean> {
    private final Socket client;
    private final int clientID;

    ClientCallable(Socket client, int ID) {
        this.client = client;
        this.clientID = ID;
    }

    private String readRequest() {
        return "";
    }

    private void sendRequest(String request) throws IOException {
        this.client.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));
        this.client.getOutputStream().flush();
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Client " + this.clientID + " connected");
        String request = this.readRequest();

        // Thread.sleep(2 * 1000);
        // dispatch request to handler depending on url path received
        String path = "/";
        switch (path) {
            case "/end" -> this.sendRequest(this.getUnknownContent(request));
            case "/" -> this.sendRequest(this.getHomeContent(request));
            default -> this.sendRequest(this.getUnknownContent(request));
        }

        this.client.close();
        return true;
    }

    private String getUnknownContent(String request) {
        String content = """
                HTTP/1.1 200 OK\r
                Content-Length: 36\r
                Content-Type: text/html\r
                \r
                <h1>Unknown content</h1>""";
        System.out.println("[Client " + this.clientID + "]Message envoyé :\n" + content);
        return content;
    }

    private String getHomeContent(String request) {
        String content = """
                HTTP/1.1 200 OK\r
                Content-Length: 36\r
                Content-Type: text/html\r
                \r
                <h1>Home</h1>less""";
        System.out.println("[Client " + this.clientID + "]Message envoyé :\n" + content);
        return content;
    }
}
