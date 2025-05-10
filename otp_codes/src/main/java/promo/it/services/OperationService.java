package promo.it.services;

import promo.it.dao.DaoManager;
import promo.it.enums.SenderType;
import promo.it.model.Operation;
import promo.it.senders.SendersFabric;
import promo.it.settings.SettingsManager;

import java.util.List;

public class OperationService {
    private OtpService otpService;
    private DaoManager daoManager;
    private SettingsManager settingsManager;

    public OperationService(DaoManager dm, SettingsManager sm) {
        daoManager = dm;
        settingsManager = sm;
        otpService = new OtpService(dm, sm);
    }

    public String createOperationWithCode(Operation newOperation, List<SenderType> typesList) {
        long res = daoManager.getDaoOperations().insert(newOperation.getUser().getId()
                , newOperation.getType().ordinal()
                , newOperation.getStatus().ordinal());

        if (res == 0) {
            throw new RuntimeException("Can't create operation: can't insert the new one");
        }
        newOperation.setId(res);
        String code = otpService.createCode(newOperation);

        for (var t : typesList) {
            SendersFabric.createSender(t, settingsManager).send(code);
        }

        return code;
    }
}
