import java.io.IOException;

public class App {
    public static void main(String[] args) {
        PublicServer server = new PublicServer(2134);
        InternalServer serverAdmin = new InternalServer(8080, server);

        try {
            new Thread(server::start).start();
            serverAdmin.start();
        } catch (Exception e) {
            Logs.error("Server down incorrectly : " + e);
        } finally {
            server.end();
            serverAdmin.end();
        }
    }
}
