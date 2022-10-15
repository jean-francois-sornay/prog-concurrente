import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class ClientCallable implements Callable<Integer> {
    private final Socket client;
    private final int clientID;
    private final ServerObserver serverObserver;


    ClientCallable(Socket client, int ID, ServerObserver observer) {
        this.client = client;
        this.clientID = ID;
        this.serverObserver = observer;
        Logs.info("[Client " + ID + "] waiting for processing");
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

            if (result.toString().endsWith("\r\n\r\n"))
                allRead = true;
        } while (!allRead);

        Logs.info("[Client " + this.clientID + "] request well received");
        int lineNumber = result.toString().split("\r\n").length - 1;
        this.serverObserver.increment(lineNumber);
        return result.toString();
    }


    private int sendRequest(String returnCode, String body) {
        String request = "HTTP/1.1 " + returnCode
                + "\r\nContent-Length: " + body.length()
                + "\r\nContent-Type: text/html\r\n\r\n" + body;
        try {
            OutputStream os = this.client.getOutputStream();
            os.write(request.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException ex) {
            Logs.error("500 Internal Server Error, sendRequest failed : " + ex.getMessage());
            return 500;
        }
        Logs.info("[Client " + this.clientID + "] request sended");
        return 0;
    }


    @Override
    public Integer call() {
        Logs.info("[Client " + this.clientID + "] processing request");

        String request;
        try {
            request = this.readRequest();
        } catch (IOException e) {
            Logs.warning("500 Internal Server Error, readRequest failed : " + e.getMessage());
            return sendRequest("500 Internal Server Error", "Request not received correctly");
        }

        String content = this.getHomeContent(request);
        if (content == null)
            return 500;

        this.sendRequest("200 OK", content);

        try {
            this.client.close();
        } catch (IOException e) {
            Logs.warning("[Client " + this.clientID + "] Problem while closing the connection");
            return 100;
        }
        return 0;
    }


    private String getHomeContent(String request) {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException ie) {
            Logs.error("[Client " + this.clientID + "] 500 Internal Server Error, Request could not be computed");
            this.sendRequest("500 Internal Server Error", "Request could not be computed");
            return null;
        }
        String requestPath = request.substring(request.indexOf("/"), request.indexOf(" HTTP"));
        HashMap<String, String> params = parseRequestParameter(requestPath);
        String requestedPath = parseRequestPath(requestPath);

        //Todo change content to include the asked file if it exists

        if(params.containsKey("name")) {
            return "<h1>Welcome " + params.get("name") + "</h1>";
        }
        return "<h1>Homepage</h1>";
    }

    private String parseRequestPath(String requestPath) {
        int separatorIndex = requestPath.indexOf("?");
        if(separatorIndex < 0) {
            return requestPath.substring(0, requestPath.indexOf(" "));
        }
        return requestPath.substring(0, separatorIndex);
    }

    private HashMap<String, String> parseRequestParameter(String requestPath) {
        HashMap<String, String> parameters = new HashMap<>();

        int separatorIndex = requestPath.indexOf("?");
        if(separatorIndex < 0) {
            return parameters;
        }

        String parametersString = requestPath.substring(separatorIndex + 1);
        for(String param : parametersString.split("&")) {
            int indexOfEqual = param.indexOf("=");
            parameters.put(param.substring(0, indexOfEqual), param.substring(indexOfEqual + 1));
        }

        return parameters;
    }
}
