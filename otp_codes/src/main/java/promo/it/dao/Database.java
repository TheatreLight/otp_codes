package promo.it.dao;

import org.apache.commons.lang3.StringEscapeUtils;
import promo.it.settings.SettingsManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final SettingsManager settingsManager;
    public Database(SettingsManager sm) {
        settingsManager = sm;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(settingsManager.getDbUrl(),
                settingsManager.getDbUserName(),
                settingsManager.getDbPassword());
    }
}
