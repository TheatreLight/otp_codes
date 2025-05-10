package promo.it.model;

public class BaseEntity {
    protected long id;

    BaseEntity(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
