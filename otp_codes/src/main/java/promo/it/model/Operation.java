package promo.it.model;

import promo.it.enums.OperationStatus;
import promo.it.enums.OperationTypes;

public class Operation extends BaseEntity {
    private User user;
    private OperationTypes operType;
    private OperationStatus operStatus;

    public Operation(int id, User user, OperationTypes type, OperationStatus status) {
        super(id);
        this.user = user;
        operType = type;
        operStatus = status;
    }

    public User getUser() {
        return  user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OperationTypes getType() {
        return operType;
    }

    public void setType(OperationTypes type) {
        operType = type;
    }

    public OperationStatus getStatus() {
        return operStatus;
    }

    public void setStatus(OperationStatus status) {
        operStatus = status;
    }
}
