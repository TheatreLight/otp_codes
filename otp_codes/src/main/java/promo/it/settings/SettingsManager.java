package promo.it.settings;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingsManager {
    private final Properties props = new Properties();

    public SettingsManager() {
        try(InputStream input = SettingsManager.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            props.load(input);
            Dotenv dotenv = Dotenv.load();
            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                if (value != null && value.startsWith("${")
                        && value.endsWith("}")) {
                    String envVar = value.substring(2, value.length() - 1);
                    String envValue = dotenv.get(envVar);
                    if (envValue != null) {
                        props.setProperty(key, envValue);
                    }
                }
            }
        } catch (IOException e){
            throw new RuntimeException("Can't init SettingsManager: " + e.getMessage());
        }
    }

    public String getDbUrl() {
        return props.getProperty("db.url");
    }

    public String getDbUserName() {
        return props.getProperty("db.username");
    }

    public String getDbPassword() {
        return props.getProperty("db.password");
    }

    public String getSecretKey() {
        return  props.getProperty("secretKey");
    }

    public int getExpirationTime() {
        return Integer.parseInt(props.getProperty("expirationTime"));
    }
}
