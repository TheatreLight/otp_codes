package promo.it.model;

public class BaseEntity {
    protected int id;

    BaseEntity(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
