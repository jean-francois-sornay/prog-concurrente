package view;

import java.util.ArrayList;
import java.util.Map;

public class HomePage implements View {
    private static final HomePage SINGLE_INSTANCE = new HomePage();
    private HomePage() {}


    public static HomePage getInstance() {
        return SINGLE_INSTANCE;
    }


    public String getContent(Map<String, ArrayList<String>> params) {
        if (params.containsKey("name")) {
            StringBuilder nameList = new StringBuilder();
            for(String name : params.get("name")) {
                nameList.append(name).append(", ");
            }
            return View.getHeader("200 OK", "<h1>Welcome " + nameList.substring(0, nameList.length() - 2) + "</h1>");
        }
        return View.getHeader("200 OK", "<h1>Homepage</h1>");
    }
}
