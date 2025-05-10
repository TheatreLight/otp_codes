package promo.it.senders;

import promo.it.settings.SettingsManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LocalFileSender extends BaseSender {
    Path path;
    public LocalFileSender(SettingsManager sm) {
        super(sm);
        path = Path.of(sm.getFilename());
    }
    @Override
    public void send(String code) {
        try {
            Files.writeString(path, code, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't create file: " + e.getMessage());
        }
    }
}
