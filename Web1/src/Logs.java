import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Logs {
    public static void info(String info) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("INFO : [" + timeStamp + "] : " + info);
    }

    public static void warning(String warning) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.err.println("WARNING : [" + timeStamp + "] : " + warning);
    }

    public static void error(String error) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.err.println("ERROR : [" + timeStamp + "] : " + error);
    }
}
