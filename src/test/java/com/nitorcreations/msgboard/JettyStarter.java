package com.nitorcreations.msgboard;

import static java.lang.Integer.getInteger;
import static java.lang.System.getProperty;
import static java.util.concurrent.TimeUnit.MINUTES;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyStarter {
    private static final Logger LOG = LoggerFactory.getLogger(JettyStarter.class);

    public static void main(final String... args) throws Exception {
        new JettyStarter().start(getInteger("port", 8080), getProperty("env", "dev"));
    }

    public Server start(final int port, final String env) throws Exception {
        Server server = setupServer();
        setupServerConnector(server, port);
        setupHandlers(server);
        server.start();
        LOG.info("Succesfully started Jetty on port {} in environment {}", port, env);
        LOG.info("API available at http://localhost:" + port + "/");
        return server;
    }

    private Server setupServer() {
        Server server = new Server();
        server.setStopAtShutdown(true);
        return server;
    }

    private void setupServerConnector(final Server server, final int port) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setIdleTimeout(MINUTES.toMillis(2));
        connector.setReuseAddress(true);
        server.addConnector(connector);
    }

    private void setupHandlers(final Server server) {
        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/");
        context.setWar("src/main/webapp");
        context.setResourceBase("src/main/webapp");
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(context);
        server.setHandler(handlers);
    }
}
