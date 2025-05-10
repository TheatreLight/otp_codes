package promo.it.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import promo.it.dao.Database;
import promo.it.enums.Roles;
import promo.it.model.BaseEntity;
import promo.it.model.User;

public class DaoUsers extends IDao {
    private User userRow;
    public DaoUsers(Connection conn) {
        super(conn);
        try {
            var stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY NOT NULL," +
                    "login VARCHAR(512)," +
                    "password VARCHAR(1024)," +
                    "role INT4)");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create table users");
        }
    }

    public boolean isAdminAlreadyRegistered() throws SQLException {
        var stmt = conn.createStatement();
        stmt.execute("SELECT COUNT(*) FROM users WHERE role = 0");
        var res = stmt.getResultSet();
        res.next();
        return res.getInt(1) != 0;
    }

    public int insert(/*Role*/int param, /*login, passwd*/String ... args) throws SQLException {
        var stmt = conn.prepareStatement("INSERT INTO users(login, password, role) VALUES(?, ?, ?)");
        int index = 1;
        for (var i : args) {
            stmt.setString(index, i);
            index++;
        }
        stmt.setInt(index, param);
        return stmt.executeUpdate();
    }

    @Override
    public boolean isExist(String login) throws SQLException {
        var stmt = conn.prepareStatement("SELECT id FROM users WHERE login = ?");
        stmt.setString(1, login);
        stmt.execute();
        var res = stmt.getResultSet();
        return res.next();
    }

    @Override
    public BaseEntity getEntityByID(int id) throws SQLException {
        var pstmt = conn.prepareStatement("SELECT id, login, password, role FROM users WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.execute();
        var res = pstmt.getResultSet();
        User user = null;
        if (res.next()) {
            user = new User(res.getInt(1)
                    , res.getString(2)
                    , res.getString(3)
                    , Roles.fromInt(res.getInt(4)));
        }
        return user;
    }

    @Override
    public BaseEntity getEntityByName(String name) throws SQLException {
        var pstmt = conn.prepareStatement("SELECT id, login, password, role FROM users WHERE login = ?");
        pstmt.setString(1, name);
        pstmt.execute();
        var res = pstmt.getResultSet();
        User user = null;
        if (res.next()) {
            user = new User(res.getInt(1)
                    , res.getString(2)
                    , res.getString(3)
                    , Roles.fromInt(res.getInt(4)));
        }
        return user;
    }

    public List<User> getUsersList() {
        try {
            var stmt = conn.createStatement();
            stmt.execute("SELECT * FROM users WHERE role != 0");
            var res = stmt.getResultSet();
            ArrayList<User> users = new ArrayList<User>();
            while (res.next()) {
                User user = new User(res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        Roles.fromInt(res.getInt(4)));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("DB error: " + e.getMessage() + ", STMT: " + e.getSQLState());
        }
    }
}
