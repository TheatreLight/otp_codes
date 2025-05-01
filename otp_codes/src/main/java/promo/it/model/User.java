package promo.it.model;

import promo.it.enums.Roles;

public class User extends BaseEntity{
    private String login;
    private String pwd;
    private Roles role;

    public User(int id, String login, String pwd, Roles role) {
        super(id);
        this.login = login;
        this.pwd = pwd;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String l) {
        login = l;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String p) {
        pwd = p;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles r) {
        role = r;
    }
}
