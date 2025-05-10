package promo.it.senders;

import promo.it.enums.SenderType;
import promo.it.settings.SettingsManager;

public class SendersFabric {
    public static BaseSender createSender(SenderType type, SettingsManager sm) {
        switch (type) {
            case EMAIL -> {
                return new MailSender(sm);
            }
            case SMPP -> {
                return new SmppSender(sm);
            }
            case TELEGRAM -> {
                return new TelegramSender(sm);
            }
            case LOCAL -> {
                return new LocalFileSender(sm);
            }
        }
        throw new RuntimeException("Unknown sender's type");
    }
}
