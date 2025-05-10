package promo.it.senders;

import promo.it.settings.SettingsManager;

public abstract class BaseSender {
    protected SettingsManager settingsManager;
    public BaseSender(SettingsManager sm) {
        settingsManager = sm;
    }
    public abstract void send(String code);
}
