package org.example.handle.currency;

public enum Currency {
    RUB("\u20BD"), EUR("\u20AC"), USD("\u0024"),
    GBP("\u00A3"), BYR("Rbl"), TRL("\u20BA"),
    BTC("\u20BF"), ETH("Ξ"), XRP("✕");

    private final String sign;
    Currency(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
