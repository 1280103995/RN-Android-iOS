package com.rn.util;

public class TokenInfo {

    private static TokenInfo mInstance;

    public static synchronized TokenInfo getInstance() {
        if (mInstance == null) {
            synchronized (TokenInfo.class) {
                if (mInstance == null) {
                    mInstance = new TokenInfo();
                }
            }
        }
        return mInstance;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
