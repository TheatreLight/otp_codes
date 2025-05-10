package promo.it.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import promo.it.dao.DaoManager;
import promo.it.enums.Roles;
import promo.it.model.User;
import promo.it.security.AuthService;
import promo.it.services.UsersService;
import promo.it.settings.SettingsManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UsersHandler extends BaseHandler {
    private AuthService authService;
    private UsersService userService;
    @Override
    protected void get(HttpExchange exchange) {
        String authHeaders = exchange.getRequestHeaders().getFirst("Authorization");
        Map<Object, Object> result = new HashMap<>();
        try (exchange) {
            var user = authService.authenticate(authHeaders);
            if (user == null || user.getRole() != Roles.ADMIN) {
                result.put("message", "Access denied");
                code = 401;
                sendResponse(exchange, new ObjectMapper().writeValueAsString(result));
                return;
            }
            var list = userService.getUsersList();
            result.put("message", "Success");
            result.put("users", list);
            sendResponse(exchange, new ObjectMapper().writeValueAsString(result));
        } catch (IOException e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void post(HttpExchange exchange) {}

    @Override
    protected void put(HttpExchange exchange) {}

    protected void delete(HttpExchange exchange) {}

    public UsersHandler(DaoManager daoManager, SettingsManager sm) {
        super(daoManager, sm);
        authService = new AuthService(daoManager, sm);
        userService = new UsersService(daoManager);
    }
}
