package promo.it.dao;

import promo.it.model.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class IDao {
    protected Connection conn;
    public IDao(Connection conn) {
        this.conn = conn;
    }

    public abstract boolean isExist(String name) throws SQLException;

    public abstract int insert(int param, String ... args) throws SQLException;

    public abstract BaseEntity getEntityByID(int id) throws SQLException;

    public BaseEntity getEntityByName(String name) throws SQLException {
        throw new RuntimeException("Not implemented for this entity");
    }
}
