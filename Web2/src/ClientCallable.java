import view.Error404;
import view.Error500;
import view.HomePage;
import view.View;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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


    private int sendRequest(String request) {
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
            sendRequest(Error500.getInstance().getContent(null));
            return 500;
        }

        Logs.info("[Client " + this.clientID + "] processing request2");
        String content = this.getContent(request);
        if (content == null)
        {
            Logs.warning("500 Internal Server Error content null");
            sendRequest(Error500.getInstance().getContent(null));
            return 500;
        }

        int error = this.sendRequest(content);
        try { this.client.close(); }
        catch (IOException e) {
            Logs.warning("[Client " + this.clientID + "] Problem while closing the connection");
            return 500;
        }
        return error;
    }


    private String getContent(String request) {
        String url = request.substring(request.indexOf("/"), request.indexOf(" HTTP"));
        String path = getPath(url);
        HashMap<String, ArrayList<String>> params = getQueries(url);
        
        File f = new File("."+path);
    
        if(f.exists() && f.canRead()){
            if (f.isDirectory()){
                StringBuilder fileList = new StringBuilder();
                for(String s : f.list()) {
                    fileList.append(s);
                }
                return View.getHeader("200 OK", fileList.toString()); 
            }
            else {
                try {
                    FileReader fr = new FileReader(f);   			
                    BufferedReader br = new BufferedReader(fr);  
                    StringBuffer sb = new StringBuffer();    
                    String line;
                    while((line = br.readLine()) != null)
                    {
                        sb.append(line);      
                        sb.append("\n");     
                    }
                    fr.close();    
                    return View.getHeader("200 OK", sb.toString());  
                }
                catch(IOException e){
                    return Error500.getInstance().getContent(null);
                }
            }
        }
        else {
            return Error404.getInstance().getContent(params);
        }
    }

    private String getPath(String requestPath) {
        int separatorIndex = requestPath.indexOf("?");
        if (separatorIndex < 0)
            return requestPath;
        return requestPath.substring(0, separatorIndex);
    }

    private HashMap<String, ArrayList<String>> getQueries(String requestPath) {
        HashMap<String, ArrayList<String>> parameters = new HashMap<>();

        int separatorIndex = requestPath.indexOf("?");
        if (separatorIndex < 0) {
            return parameters;
        }

        String parametersString = requestPath.substring(separatorIndex + 1);
        for (String param : parametersString.split("&")) {
            int indexOfEqual = param.indexOf("=");
            String paramName = param.substring(0, indexOfEqual);
            if(!parameters.containsKey(paramName)) {
                parameters.put(paramName, new ArrayList<>());
            }
            parameters.get(paramName).add(param.substring(indexOfEqual + 1));
        }

        return parameters;
    }
}
