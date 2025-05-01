package promo.it.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import promo.it.dao.DaoManager;
import promo.it.enums.Roles;
import promo.it.model.User;
import promo.it.security.AuthService;
import promo.it.settings.SettingsManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class RegisterHandler extends BaseHandler {
    AuthService authService;
    public RegisterHandler(DaoManager daoManager, SettingsManager sm) {
        super(daoManager, sm);
        authService = new AuthService(daoManager, sm);
    }

    @Override
    protected void get(HttpExchange exchange) {
        try (exchange){
            sendResponse(exchange, "Register page");
        } catch(IOException e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void post(HttpExchange exchange) {
        CompletableFuture.runAsync(() -> {
            var daoUsers = daoManager.getDaoUsers();
            InputStream body = exchange.getRequestBody();
            try (exchange) {
                RequestReader reader = new RequestReader(body);
                var login = reader.getValue("login");
                var passwd = reader.getValue("pwd");
                Roles role = Roles.valueOf(reader.getValue("role").toUpperCase());
                var res = authService.register(new User(-1, login, passwd, role));
                sendResponse(exchange, new ObjectMapper().writeValueAsString(res));
            } catch (IOException e) {
                throw new RuntimeException("Can;t register new user: " + e.getMessage());
            }
        });
    }

    @Override
    protected void put(HttpExchange exchange) {}

    @Override
    protected void delete(HttpExchange exchange) {}
}
