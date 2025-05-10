package promo.it.settings;

import io.github.cdimascio.dotenv.Dotenv;
import promo.it.model.OtpConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingsManager {
    private final Properties props = new Properties();
    private final Properties mailProps = new Properties();
    private final Properties smppProps = new Properties();
    private final Properties tgProps = new Properties();
    private final Properties localProps = new Properties();

    private void setPropertiesFromEnvironment(Properties p) {
        Dotenv dotenv = Dotenv.load();
        for (String key : p.stringPropertyNames()) {
            String value = p.getProperty(key);
            if (value != null && value.startsWith("${")
                    && value.endsWith("}")) {
                String envVar = value.substring(2, value.length() - 1);
                String envValue = dotenv.get(envVar);
                if (envValue != null) {
                    p.setProperty(key, envValue);
                }
            }
        }
    }

    public SettingsManager() {
        try {
            var mainInput = SettingsManager.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            var emailPropsInput = SettingsManager.class.getClassLoader()
                    .getResourceAsStream("email.properties");
            var smppPropsInput = SettingsManager.class.getClassLoader()
                    .getResourceAsStream("smpp.properties");
            var tgPropsInput = SettingsManager.class.getClassLoader()
                    .getResourceAsStream("tg.properties");
            var localPropsInput = SettingsManager.class.getClassLoader()
                    .getResourceAsStream("storage.properties");
            props.load(mainInput);
            mailProps.load(emailPropsInput);
            smppProps.load(smppPropsInput);
            tgProps.load(tgPropsInput);
            localProps.load(localPropsInput);
            setPropertiesFromEnvironment(props);
            setPropertiesFromEnvironment(mailProps);
            setPropertiesFromEnvironment(smppProps);
            setPropertiesFromEnvironment(tgProps);
            setPropertiesFromEnvironment(localProps);
        } catch (IOException e){
            throw new RuntimeException("Can't init SettingsManager: " + e.getMessage());
        }
    }

    public Properties getEMailProps() {
        return mailProps;
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

    public String getAlgorithm() { return props.getProperty("algorithm"); }

    public int getOtpLength() { return Integer.parseInt(props.getProperty("otp.length")); }

    public int getOtpLifeTime() { return Integer.parseInt(props.getProperty("otp.time")); }

    public String getEMailUserName() { return mailProps.getProperty("email.username"); }

    public String getEMailPassword() { return mailProps.getProperty("email.password"); }

    public String getEMailAddressFrom() { return mailProps.getProperty("email.from"); }

    public String getEMailAddressTo() { return mailProps.getProperty("email.to"); }

    public String getSmtpHost() { return mailProps.getProperty("email.host"); }

    public int getSmtpPort() { return Integer.parseInt(mailProps.getProperty("email.port")); }

    public String getSmppHost() { return smppProps.getProperty("smpp.host"); }

    public int getSmppPort() { return Integer.parseInt(smppProps.getProperty("smpp.port")); }

    public String getSmppSystemId() { return smppProps.getProperty("smpp.system_id"); }

    public String getSmppPassword() { return smppProps.getProperty("smpp.password"); }

    public  String getSmppSystemType() { return smppProps.getProperty("smpp.system_type"); }

    public  String getSmppSourceAddress() { return smppProps.getProperty("smpp.source_addr"); }

    public String getSmppDestinationAddress() { return smppProps.getProperty("smpp.dest_addr"); }

    public String getBotToken() { return tgProps.getProperty("tg.token"); }

    public String getChatId() { return tgProps.getProperty("tg.chatid"); }

    public String getTgUsername() { return tgProps.getProperty("tg.username"); }

    public String getLocalPath() { return localProps.getProperty("localpath"); }

    public String getFilename() { return localProps.getProperty("filename"); }
}
