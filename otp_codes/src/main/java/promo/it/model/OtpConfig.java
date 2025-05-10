package promo.it.model;


public class OtpConfig extends BaseEntity {
    private int codeLength;
    private long ttlSeconds;

    public OtpConfig(int length, long seconds) {
        super(1);
        codeLength = length;
        ttlSeconds = seconds;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int length) {
        codeLength = length;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(int seconds) {
        ttlSeconds = seconds;
    }
}
