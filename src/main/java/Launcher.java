import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.logging.LogManager;

public class Launcher {
    
    private static final int DEFAULT_SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {
        disableJavaLogging();

        Server server = new Server();
        SocketConnector connector = new SocketConnector();

        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(determineServerPort());
        server.setConnectors(new Connector[]{connector});

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/");

        ProtectionDomain protectionDomain = Launcher.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());

        server.setHandler(context);
        server.start();

        Desktop.getDesktop().browse(URI.create("http://localhost:" + connector.getPort() + "/"));
        
        server.join();
    }

    private static int determineServerPort() {
        final String port = System.getProperty("port");
        return (port == null || "".equals(port)) ? DEFAULT_SERVER_PORT : Integer.parseInt(port);
    }

    private static void disableJavaLogging() {
        LogManager.getLogManager().reset();
    }

}
