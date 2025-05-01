package promo.it.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import promo.it.dao.DaoManager;
import promo.it.settings.SettingsManager;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseHandler implements HttpHandler {
    protected DaoManager daoManager;
    protected SettingsManager settingsManager;
    protected int code = 200;

    protected void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(code, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    protected abstract void get(HttpExchange exchange);

    protected abstract void post(HttpExchange exchange);

    protected abstract  void put(HttpExchange exchange);

    protected abstract void delete(HttpExchange exchange);

    public BaseHandler(DaoManager daoManager, SettingsManager sm) {
        this.daoManager = daoManager;
        settingsManager = sm;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if ("GET".equalsIgnoreCase(method)) {
            get(exchange);
        } else if ("POST".equalsIgnoreCase(method)) {
            post(exchange);
        } else if ("PUT".equalsIgnoreCase(method)) {
            put(exchange);
        } else {
            delete(exchange);
        }
    }
}
