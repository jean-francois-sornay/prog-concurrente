import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ClientCallable implements Callable<Boolean> {
    private final Socket client;
    private final int clientID;

    ClientCallable(Socket client, int ID) {
        this.client = client;
        this.clientID = ID;
    }

    private String readRequest() throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream is = client.getInputStream();
        byte[] bytes;
        boolean allRead = false;

        do {
            bytes = new byte[1024];
            int readBytes = is.read(bytes, 0, bytes.length);
            byte[] copiedArray = Arrays.copyOfRange(bytes, 0, readBytes);
            result.append(new String(copiedArray, StandardCharsets.UTF_8));

            if (result.toString().endsWith("\r\n\r\n")) {
                allRead = true;
            }
        } while (!allRead);

        System.out.println("[Client " + this.clientID + "] request received");
        return result.toString();
    }

    private void sendRequest(String request) throws IOException {
        OutputStream os = this.client.getOutputStream();
        os.write(request.getBytes(StandardCharsets.UTF_8));
        os.flush();
        System.out.println("[Client " + this.clientID + "] answered");
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("[Client " + this.clientID + "] processing request");
        String request = this.readRequest();

        // dispatch request to handler depending on url path received
        // a get path method to know wich page was asked
        // followed by a switch case to the different function
        // but not mandatory for this exercise
        TimeUnit.SECONDS.sleep(5);
        this.sendRequest(this.getHomeContent(request));

        this.client.close();
        return true;
    }

    private String getHomeContent(String request) {
        return """
                HTTP/1.1 200 OK\r
                Content-Length: 36\r
                Content-Type: text/html\r
                \r
                <h1>Homepage</h1>""";
    }
}
