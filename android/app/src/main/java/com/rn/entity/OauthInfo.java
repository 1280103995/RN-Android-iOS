package com.rn.entity;

import android.util.Log;

import java.io.Serializable;

public class OauthInfo implements Serializable {

    private static final long serialVersionUID = -5335079480160486463L;
    /**
     * {
     "access_token": "01c2b02c-342b-416d-a2eb-08857a40b5e5",
     "token_type": "bearer",
     "refresh_token": "ae2c876e-8773-478b-880b-791c19daaae1",
     "expires_in": 22,
     "scope": "read write"
     }
     */

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String scope;
    private long expirationTime;
    private UserInfoResponse userInfo;
    private String token_clientid;
    private String token_clientsecret;
    private long tokenExpirationTime;

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_clientid() {
        return token_clientid;
    }

    public void setToken_clientid(String token_clientid) {
        this.token_clientid = token_clientid;
    }

    public String getToken_clientsecret() {
        return token_clientsecret;
    }

    public void setToken_clientsecret(String token_clientsecret) {
        this.token_clientsecret = token_clientsecret;
    }

    public UserInfoResponse getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResponse userInfo) {
        this.userInfo = userInfo;
    }

    public long getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(long tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    @Override
    public String toString() {
        return "{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", expirationTime=" + expirationTime +
                ", userInfo=" + userInfo +
                ", token_clientid='" + token_clientid + '\'' +
                ", token_clientsecret='" + token_clientsecret + '\'' +
                ", tokenExpirationTime=" + tokenExpirationTime +
                '}';
    }
}