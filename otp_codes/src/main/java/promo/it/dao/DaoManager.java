package promo.it.dao;

import java.sql.Connection;

public class DaoManager {
    private final DaoUsers daoUsers;
    private final DaoOtpConfig daoOtpConfig;
    private final DaoOperations daoOperations;
    private final DaoOtpCodes daoOtpCodes;

    public DaoManager(Connection conn) {
        daoUsers = new DaoUsers(conn);
        daoOtpConfig = new DaoOtpConfig(conn);
        daoOperations = new DaoOperations(conn);
        daoOtpCodes = new DaoOtpCodes(conn);
    }

    public DaoUsers getDaoUsers() {
        return daoUsers;
    }

    public DaoOtpConfig getDaoOtpConfig() {
        return daoOtpConfig;
    }

    public DaoOperations getDaoOperations() {
        return daoOperations;
    }

    public DaoOtpCodes getDaoOtpCodes() {
        return daoOtpCodes;
    }
}
