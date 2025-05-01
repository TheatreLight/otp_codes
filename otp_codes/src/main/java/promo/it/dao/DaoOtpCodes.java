package promo.it.dao;

import promo.it.model.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoOtpCodes extends IDao {
    public DaoOtpCodes(Connection conn) {
        super(conn);
        try {
            var stmt = conn.createStatement();
            stmt.execute("create table if not exists otp_codes (id BIGSERIAL primary key not null, " +
                    "operation_id BIGSERIAL not null, " +
                    "user_id BIGSERIAL not null, " +
                    "code int, " +
                    "created_at date, " +
                    "expires_at date, " +
                    "is_verified bool, " +
                    "constraint operation_fkey foreign key (operation_id) references operations(id), " +
                    "constraint user_fkey foreign key (user_id) references users(id))");
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create otp_codes table, something goes wrong!");
        }
    }

    @Override
    public boolean isExist(String name) throws SQLException {
        return false;
    }

    @Override
    public int insert(int param, String... args) throws SQLException {
        return 0;
    }

    @Override
    public BaseEntity getEntityByID(int id) throws SQLException {
        return null;
    }
}
