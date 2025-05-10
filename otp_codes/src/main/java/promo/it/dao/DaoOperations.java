package promo.it.dao;

import promo.it.model.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoOperations extends IDao {
    public DaoOperations(Connection conn) {
        super(conn);
        try {
            var stmt = conn.createStatement();
            stmt.execute("create table if not exists operations (id BIGSERIAL primary key not null, " +
                    "user_id BIGSERIAL not null, " +
                    "operation_type int, " +
                    "status int, " +
                    "completed_at date, " +
                    "constraint user_fkey foreign key (user_id) references users (id))");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create operations table, something goes wrong!");
        }
    }

    @Override
    public boolean isExist(String name) throws SQLException {
        return false;
    }

    public long insert(long user_id, int type, int status) {
        try {
            var pstmt = conn.prepareStatement("INSERT INTO operations(user_id, operation_type, status) " +
                    "VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, user_id);
            pstmt.setInt(2, type);
            pstmt.setInt(3, status);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return 0;
            }
            var res = pstmt.getGeneratedKeys();
            if(res.next()) {
                return res.getLong(1);
            }
            return 0;
        } catch(SQLException e) {
            throw new RuntimeException("Can't create new operation - DB issue: "
                    + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }

    @Override
    public BaseEntity getEntityByID(int id) throws SQLException {
        return null;
    }
}
