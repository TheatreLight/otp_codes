package promo.it.model;

public class OtpConfig extends BaseEntity {
    private int codeLength;
    private int ttlSeconds;
    public OtpConfig(int id, int length, int seconds) {
        super(id);
        codeLength = length;
        ttlSeconds = seconds;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int length) {
        codeLength = length;
    }

    public int getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(int seconds) {
        ttlSeconds = seconds;
    }
}
