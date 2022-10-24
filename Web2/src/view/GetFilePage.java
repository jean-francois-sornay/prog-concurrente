package view;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class GetFilePage {
    private static final GetFilePage SINGLE_INSTANCE = new GetFilePage();
    private GetFilePage() {}


    public static GetFilePage getInstance() {
        return SINGLE_INSTANCE;
    }


    public static boolean isFileAccessible(File f) {
        File accessibleDir = new File("./Web2/src/view/HTML");
        try {
            if (f.getCanonicalPath().contains(accessibleDir.getCanonicalPath()))
                return true;
        } catch (IOException ignored) {}
        return false;
    }


    public String getContent(Map<String, ArrayList<String>> params) {
        File file;
        if (params.containsKey("path") && params.get("path").get(0) != null)
            file = new File("./Web2/src/view/HTML/" + params.get("path").get(0));
        else
            file = new File("./Web2/src/view/HTML");

        if (!file.exists() || !file.canRead() || !GetFilePage.isFileAccessible(file)) {
            // Error 404 cant read / file doesnt exist / file is out of reach
            return Error404.getInstance().getContent(null);
        }

        if (file.isDirectory()) {
            StringBuilder fileList = new StringBuilder().append("<ul>");
            for (String s : Objects.requireNonNull(file.list())) {
                fileList.append("<li>").append(s).append("</li>");
            }
            fileList.append("</ul>");
            return View.getHeader("200 OK", fileList.toString());
        }

        // It is a simple file
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            return Error500.getInstance().getContent(null);
        }
        return View.getHeader("200 OK", sb.toString());
    }
}
