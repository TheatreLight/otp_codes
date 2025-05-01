package promo.it.dao;

import promo.it.model.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;

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

    @Override
    public int insert(int param, String... args) throws SQLException {
        return 0;
    }

    @Override
    public BaseEntity getEntityByID(int id) throws SQLException {
        return null;
    }
}
