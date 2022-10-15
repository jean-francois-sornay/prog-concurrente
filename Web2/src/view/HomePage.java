package view;

import java.util.Map;

public class HomePage implements View {
    private static final HomePage SINGLE_INSTANCE = new HomePage();
    private HomePage() {}


    public static HomePage getInstance() {
        return SINGLE_INSTANCE;
    }


    public String getContent(Map<String, String> params) {
        if (params.containsKey("name")) {
            return View.getHeader("200 OK", "<h1>Welcome " + params.get("name") + "</h1>");
        }
        return View.getHeader("200 OK", "<h1>Homepage</h1>");
    }
}
