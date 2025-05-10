package promo.it.model;

import java.sql.Date;

public class Otp extends BaseEntity {
    private Operation operation;
    private User user;
    private String code;
    private Date createdAt;
    private  Date expiresAt;
    private boolean isVerified = false;

    public Otp(int id,
               Operation operation,
               User user,
               String code,
               Date create,
               Date expire) {
        super(id);
        this.operation = operation;
        this.user = user;
        this.code = code;
        createdAt = create;
        expiresAt = expire;
    }

    public Operation getOperation() {
        return  operation;
    }

    public void setOperation(Operation o) {
        operation = o;
    }

    public User getUser() {
        return  user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreationDate() {
        return createdAt;
    }

    public void setCreationDate(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpirationDate() {
        return expiresAt;
    }

    public void setExpirationDate(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
