package promo.it.dao;

import promo.it.model.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DaoOtpCodes extends IDao {
    public DaoOtpCodes(Connection conn) {
        super(conn);
        try {
            var stmt = conn.createStatement();
            stmt.execute("create table if not exists otp_codes (id BIGSERIAL primary key not null, " +
                    "operation_id BIGSERIAL not null, " +
                    "user_id BIGSERIAL not null, " +
                    "code varchar(16), " +
                    "created_at timestamp, " +
                    "expires_at timestamp, " +
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

    public int insert(long operationId, long userId, String code, Timestamp createdAt, Timestamp expiresAt) {
        try {
            var pstmt = conn.prepareStatement("INSERT INTO otp_codes(operation_id, user_id, code, " +
                    "created_at, expires_at, is_verified) VALUES(?,?,?,?,?,?)");
            pstmt.setLong(1, operationId);
            pstmt.setLong(2, userId);
            pstmt.setString(3, code);
            pstmt.setTimestamp(4, createdAt);
            pstmt.setTimestamp(5, expiresAt);
            pstmt.setBoolean(6, false);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new otp code - DB issue: "
                    + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }

    @Override
    public BaseEntity getEntityByID(int id) throws SQLException {
        return null;
    }

    public List<String> getCodesListByUserId(long user_id) {
        try {
            var pstmt = conn.prepareStatement("SELECT code FROM otp_codes WHERE user_id = ?");
            pstmt.setLong(1, user_id);
            pstmt.execute();
            var res = pstmt.getResultSet();
            List<String> resultSet = new ArrayList<>();
            while(res.next()) {
                resultSet.add(res.getString(1));
            }
            return resultSet;
        } catch(SQLException e) {
            throw new RuntimeException("Can't get the otp code - DB issue: "
                    + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }

    public String getCodeByOperationId(long oper_id) {
        try {
            var pstmt = conn.prepareStatement("SELECT code FROM otp_codes WHERE operation_id = ?");
            pstmt.setLong(1, oper_id);
            pstmt.execute();
            var res = pstmt.getResultSet();
            if (res.next()) {
                return res.getString((1));
            }
            return "";
        } catch(SQLException e) {
            throw new RuntimeException("Can't get the otp code - DB issue: "
                    + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }
}
