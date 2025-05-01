package promo.it.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import promo.it.dao.DaoManager;
import promo.it.security.AuthService;
import promo.it.settings.SettingsManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class LoginHandler extends BaseHandler {
    private AuthService authService;
    @Override
    protected void get(HttpExchange exchange) {
        try (exchange){
            sendResponse(exchange, "Login page");
        } catch(IOException e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void post(HttpExchange exchange) {
        CompletableFuture.runAsync(()->{
            InputStream body = exchange.getRequestBody();
            try (exchange){
                RequestReader reader = new RequestReader(body);
                var login = reader.getValue("login");
                var passwd = reader.getValue("pwd");
                var result = authService.login(login, passwd);
                sendResponse(exchange, new ObjectMapper().writeValueAsString(result));
            } catch (IOException e) {
                throw new RuntimeException("Can't login. Internal server error: " + e.getMessage());
            }
        });
    }

    @Override
    protected void put(HttpExchange exchange) {}

    @Override
    protected void delete(HttpExchange exchange) {}

    public LoginHandler(DaoManager daoManager, SettingsManager sm) {
        super(daoManager, sm);
        authService = new AuthService(daoManager, sm);
    }
}
