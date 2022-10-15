package view;

import java.util.ArrayList;
import java.util.Map;

public class Error404 implements View {
    private static final Error404 SINGLE_INSTANCE = new Error404();
    private Error404() {}


    public static Error404 getInstance() {
        return SINGLE_INSTANCE;
    }


    @Override
    public String getContent(Map<String, ArrayList<String>> params) {
        return View.getHeader("404 Not Found", "<h1 style=\"text-align: center;\">404</h1>");
    }
}
