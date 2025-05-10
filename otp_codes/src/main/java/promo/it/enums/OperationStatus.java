package promo.it.enums;

public enum OperationStatus {
    NEW,
    PROCESSING,
    CANCELLED,
    SUCCESS;

    public static OperationStatus fromInt(int i) throws IllegalArgumentException {
        OperationStatus[] statuses = OperationStatus.values();
        if (i < 0 || i >= statuses.length) {
            throw new IllegalArgumentException("Unknown operation status");
        }
        return statuses[i];
    }
}
