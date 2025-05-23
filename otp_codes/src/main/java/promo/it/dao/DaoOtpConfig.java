package promo.it.dao;

import promo.it.model.BaseEntity;
import promo.it.model.OtpConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoOtpConfig extends IDao {
    public DaoOtpConfig(Connection conn) {
        super(conn);
        try {
            var stmt = conn.createStatement();
            stmt.execute("create table if not exists otp_config (id int default 1, " +
                    "code_length int, " +
                    "ttl_seconds int)");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create otp_config table, something goes wrong!");
        }
    }

    @Override
    public boolean isExist(String name) throws SQLException {
        return false;
    }

    public int insert(int param, String... args) throws SQLException {
        return 0;
    }

    public int update(int length, long seconds) {
        try {
            var pstmt = conn.prepareStatement("UPDATE otp_config SET code_length = ? , ttl_seconds = ? WHERE id = 1");
            pstmt.setInt(1, length);
            pstmt.setLong(2, seconds);
            return pstmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Can;t update config: "
                    + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }

    @Override
    public BaseEntity getEntityByID(int id) {
        try {
            var pstmt = conn.prepareStatement("SELECT * FROM otp_codes WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            var res = pstmt.getResultSet();
            OtpConfig conf = null;
            if (res.next()) {
                conf = new OtpConfig(res.getInt(2), res.getInt(3));
            }
            return conf;
        } catch (SQLException e) {
            throw new RuntimeException("Can;t retrieve config from DB: "
                    + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }
}
