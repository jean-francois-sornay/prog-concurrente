package view;

import java.util.ArrayList;
import java.util.Map;

public class Error500 implements View {
    private static final Error500 SINGLE_INSTANCE = new Error500();
    private Error500() {}


    public static Error500 getInstance() {
        return SINGLE_INSTANCE;
    }


    @Override
    public String getContent(Map<String, ArrayList<String>> params) {
        return View.getHeader("500 Internal Server Error", "<h1 style=\"text-align: center;\">500</h1>");
    }
}
