package promo.it.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import promo.it.dao.DaoManager;
import promo.it.enums.OperationStatus;
import promo.it.enums.OperationTypes;
import promo.it.enums.SenderType;
import promo.it.model.Operation;
import promo.it.security.AuthService;
import promo.it.services.OperationService;
import promo.it.settings.SettingsManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OperationHandler extends BaseHandler {
    OperationService operationService;
    AuthService authService;
    public OperationHandler(DaoManager daoManager, SettingsManager sm) {
        super(daoManager, sm);
        operationService = new OperationService(daoManager, sm);
        authService = new AuthService(daoManager, sm);
    }

    @Override
    protected void get(HttpExchange exchange) {
        try (exchange){
            sendResponse(exchange, "Operation page");
        } catch(IOException e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void post(HttpExchange exchange) {
        var path = exchange.getRequestURI().getPath();
        var body = exchange.getRequestBody();
        try (exchange){
            Map<Object, Object> result = new HashMap<>();
            if (path.equals("/operations/new")) {
                var user = authService.authenticate(exchange
                        .getRequestHeaders()
                        .getFirst("Authorization"));
                if (user == null) {
                    sendResponse(exchange, "unknown user. Access denied.");
                    exchange.close();
                    return;
                }
                RequestReader reader = new RequestReader(body);
                OperationTypes type = OperationTypes.valueOf(reader.getValue("operationType"));
                OperationStatus status = OperationStatus.valueOf(reader.getValue("operationStatus"));
                var types = reader.getArray("types")
                        .stream()
                        .map(String::toUpperCase)
                        .map(t -> {
                            try {
                                return SenderType.valueOf(t);
                            } catch(IllegalArgumentException e) {
                                //logger
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .toList();
                Operation operation = new Operation(-1, user, type, status);
                String code = operationService.createOperationWithCode(operation, types);
                result.put("message", "Successfully created operation");
                result.put("otp", code);
                sendResponse(exchange, new ObjectMapper().writeValueAsString(result));
            } else if (path.equals("/operations/list")) {
                // show the list of operations
                sendResponse(exchange, "Here will be a list of operations");
            }
        } catch (IOException e) {
            throw new RuntimeException("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void put(HttpExchange exchange) {

    }

    @Override
    protected void delete(HttpExchange exchange) {

    }
}
