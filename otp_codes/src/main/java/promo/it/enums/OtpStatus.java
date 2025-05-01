package promo.it.enums;

public enum OtpStatus {
    ACTIVE,
    EXPIRED,
    USED;

    public static OtpStatus fromInt(int i) throws IllegalArgumentException {
        OtpStatus[] statuses = OtpStatus.values();
        if (i < 0 || i >= statuses.length) {
            throw new IllegalArgumentException("Unknown status type");
        }
        return statuses[i];
    }
}
