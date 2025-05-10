package promo.it.services;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

import promo.it.dao.DaoManager;
import promo.it.model.Operation;
import promo.it.model.User;
import promo.it.settings.SettingsManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class OtpService {
    private DaoManager daoManager;
    private SettingsManager settingsManager;
    private OtpConfigurationService configService;

    private String generate() {
        try {
            var conf = configService.getConfig();
            KeyGenerator keyGenerator = KeyGenerator.getInstance(settingsManager.getAlgorithm());
            keyGenerator.init(100);
            SecretKey secretKey = keyGenerator.generateKey();
            TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator(
                    Duration.ofSeconds(conf.getTtlSeconds()), conf.getCodeLength());
            return String.format("%06d", totp.generateOneTimePassword(secretKey, Instant.now()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Wrong algorithm for generating: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    public OtpService(DaoManager dm, SettingsManager sm) {
        daoManager = dm;
        settingsManager = sm;
        configService = new OtpConfigurationService(dm, sm);
    }

    public String createCode(Operation oper) {
        String code = generate();
        Timestamp currTime = new Timestamp((System.currentTimeMillis()));
        Timestamp expTime = new Timestamp(currTime.getTime() + configService.getConfig().getTtlSeconds() * 1000);
        daoManager.getDaoOtpCodes().insert(oper.getId()
                , oper.getUser().getId()
                , code
                , currTime
                , expTime);
        return code;
    }

    public List<String> findCodesByUser(User user) {
        return daoManager.getDaoOtpCodes()
                .getCodesListByUserId(user
                        .getId());
    }

    public String findCodeByOperation(Operation operation) {
        return daoManager.getDaoOtpCodes()
                .getCodeByOperationId(operation.getId());
    }
}
