package promo.it.enums;

public enum OperationTypes {
    UNKNOWN,
    AUTHORIZE,
    PAYMENT,
    APPROVE;

    public static OperationTypes fromInt(int i) throws IllegalArgumentException {
        OperationTypes[] types = OperationTypes.values();
        if (i < 0 || i >= types.length) {
            throw new IllegalArgumentException("Unknown operation type");
        }
        return types[i];
    }
}
