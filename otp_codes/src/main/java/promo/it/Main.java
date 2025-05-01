package promo.it;

import promo.it.dao.Database;
import promo.it.dao.DaoManager;
import promo.it.server.Server;
import promo.it.settings.SettingsManager;


public class Main {
    public static void main(String args[]) throws Exception {
        SettingsManager sm = new SettingsManager();
        Database db = new Database(sm);
        var daoManager = new DaoManager(db.connect());

        Server.start(8080, daoManager, sm);
    }
}
