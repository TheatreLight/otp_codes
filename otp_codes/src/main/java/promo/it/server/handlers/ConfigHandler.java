package promo.it.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import promo.it.dao.DaoManager;
import promo.it.enums.Roles;
import promo.it.security.AuthService;
import promo.it.settings.SettingsManager;

import java.io.IOException;


public class ConfigHandler extends BaseHandler {
    AuthService authService;
    @Override
    protected void get(HttpExchange exchange) {
        var headers = exchange.getRequestHeaders().getFirst("Authorization");
        try (exchange){
            var user = authService.authenticate(headers);
            String resp;
            if (user.getRole() == Roles.ADMIN) {
                resp = "Config page";
            } else {
                resp = "You don't have permissions to watch this page. Go fuckin' out!";
            }
            sendResponse(exchange, resp);
        } catch(IOException e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void post(HttpExchange exchange) {
        
    }

    @Override
    protected void put(HttpExchange exchange) {}

    @Override
    protected void delete(HttpExchange exchange) {}

    public ConfigHandler(DaoManager daoManager, SettingsManager sm) {
        super(daoManager, sm);
        authService = new AuthService(daoManager, sm);
    }
}
