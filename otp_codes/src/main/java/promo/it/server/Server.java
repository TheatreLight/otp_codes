package promo.it.server;

import com.sun.net.httpserver.HttpServer;
import promo.it.dao.DaoManager;
import promo.it.server.handlers.ConfigHandler;
import promo.it.server.handlers.LoginHandler;
import promo.it.server.handlers.RegisterHandler;
import promo.it.server.handlers.UsersHandler;
import promo.it.settings.SettingsManager;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public static void start(int port, DaoManager dao, SettingsManager sm) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/register", new RegisterHandler(dao, sm));
        server.createContext("/login", new LoginHandler(dao, sm));
        server.createContext("/users", new UsersHandler(dao, sm));
        server.createContext("/config", new ConfigHandler(dao, sm));
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }
}
