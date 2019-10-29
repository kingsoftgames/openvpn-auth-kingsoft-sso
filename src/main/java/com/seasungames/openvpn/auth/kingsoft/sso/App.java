package com.seasungames.openvpn.auth.kingsoft.sso;

import com.alibaba.fastjson.JSONObject;
import com.kingsoft.sso.api.SSORequest;

public class App {

    private static final int EXIT_OK = 0;
    private static final int EXIT_ERROR_ENV = 1;
    private static final int EXIT_ERROR_APP = 2;
    private static final int EXIT_ERROR_LOGIN = 3;

    private static String url;
    private static String appId;
    private static String appSecret;
    private static String username;
    private static String password;

    private static String tid;
    private static String publicKey;

    private static String getEnv(String name) {
        String value = System.getenv(name);
        if (value == null) {
            System.err.println("Missing environment variable - " + name);
            System.exit(EXIT_ERROR_ENV);
        }
        return value;
    }

    private static void getInputs() {
        url = getEnv("SSO_URL");
        appId = getEnv("SSO_APP_ID");
        appSecret = getEnv("SSO_APP_SECRET");
        username = getEnv("SSO_USERNAME");
        password = getEnv("SSO_PASSWORD");
    }

    private static void registerBaseUrl() {
        SSORequest.registBaseUrl(url);
    }

    private static void createAppToken() {
        JSONObject appTokenResponse = SSORequest.appTokenCreate(appId, appSecret);
        boolean appTokenSuccessful = appTokenResponse.getBooleanValue("success");
        if (!appTokenSuccessful) {
            System.err.println("Failed to create app token - " + appTokenResponse.getString("message"));
            System.exit(EXIT_ERROR_APP);
        } else {
            tid = appTokenResponse.getString("tid");
            publicKey = appTokenResponse.getString("loginPublicKey");
        }
    }

    private static void authenticateUser() {
        JSONObject userLoginResponse = SSORequest.userTokenLogin(username, password, publicKey, tid);
        boolean userLoginSuccessful = userLoginResponse.getBooleanValue("success");
        if (!userLoginSuccessful) {
            System.err.println("Failed to login user " + username + " - " + userLoginResponse.getString("message"));
            System.exit(EXIT_ERROR_LOGIN);
        } else {
            System.err.println("Successfully authenticated user - " + username);
            System.exit(EXIT_OK);
        }
    }

    public static void main(String[] args) {
        getInputs();
        registerBaseUrl();
        createAppToken();
        authenticateUser();
    }
}
