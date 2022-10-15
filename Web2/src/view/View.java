package view;

import java.util.ArrayList;
import java.util.Map;

public interface View {
    String getContent(Map<String, ArrayList<String>> params);

    static String getHeader(String returnCode, String body) {
        return "HTTP/1.1 " + returnCode
                + "\r\nContent-Length: " + body.length()
                + "\r\nContent-Type: text/html\r\n\r\n" + body;
    }
}
