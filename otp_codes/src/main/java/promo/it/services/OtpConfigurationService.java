package promo.it.services;

import promo.it.dao.DaoManager;
import promo.it.model.OtpConfig;
import promo.it.settings.SettingsManager;

public class OtpConfigurationService {
    private DaoManager daoManager;
    private SettingsManager settingsManager;
    private OtpConfig instance;

    public OtpConfigurationService(DaoManager dm, SettingsManager sm) {
        daoManager = dm;
        settingsManager = sm;
    }

    public OtpConfig getConfig() {
        OtpConfig newConfig = (OtpConfig)daoManager.getDaoOtpConfig().getEntityByID(1);
        if (newConfig != null) {
            instance = newConfig;
        } else {
            instance = new OtpConfig(settingsManager.getOtpLength(), settingsManager.getOtpLifeTime());
            daoManager.getDaoOtpConfig().update(instance.getCodeLength(), instance.getTtlSeconds());
        }
        return instance;
    }

    public void updateConfig(int length, int sec) {
        throw new RuntimeException("Not implemented yet");
    }
}
