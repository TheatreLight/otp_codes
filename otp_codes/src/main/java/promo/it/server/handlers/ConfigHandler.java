package promo.it.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import promo.it.dao.DaoManager;
import promo.it.settings.SettingsManager;


public class ConfigHandler extends BaseHandler {

    @Override
    protected void get(HttpExchange exchange) {}

    @Override
    protected void post(HttpExchange exchange) {}

    @Override
    protected void put(HttpExchange exchange) {}

    @Override
    protected void delete(HttpExchange exchange) {}

    public ConfigHandler(DaoManager daoManager, SettingsManager sm) {
        super(daoManager, sm);
    }
}
