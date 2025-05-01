package promo.it.enums;

public enum Roles {
    ADMIN,
    USER,
    UNKNOWN;

    public static Roles fromInt(int i) throws IllegalArgumentException {
        Roles[] roles = Roles.values();
        if (i < 0 || i >= roles.length) {
            throw new IllegalArgumentException("Unknowing role type");
        }
        return roles[i];
    }
}
