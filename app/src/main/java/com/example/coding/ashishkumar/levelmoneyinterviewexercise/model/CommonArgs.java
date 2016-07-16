package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashishkumar on 7/15/16.
 */
public class CommonArgs {
    @SerializedName("uid")
    private long userId = 0;

    @SerializedName("token")
    private String authToken = "";

    @SerializedName("api-token")
    private String apiToken = "";

    @SerializedName("json-strict-mode")
    private boolean jsonStrictMode = false;

    @SerializedName("json-verbose-response")
    private boolean jsonVerboseResponse = false;

    public CommonArgs() {
    }

    public CommonArgs(long userId, String authToken, String apiToken, boolean jsonStrictMode, boolean jsonVerboseResponse) {
        this.userId = userId;
        this.authToken = authToken;
        this.apiToken = apiToken;
        this.jsonStrictMode = jsonStrictMode;
        this.jsonVerboseResponse = jsonVerboseResponse;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public boolean isJsonStrictMode() {
        return jsonStrictMode;
    }

    public void setJsonStrictMode(boolean jsonStrictMode) {
        this.jsonStrictMode = jsonStrictMode;
    }

    public boolean isJsonVerboseResponse() {
        return jsonVerboseResponse;
    }

    public void setJsonVerboseResponse(boolean jsonVerboseResponse) {
        this.jsonVerboseResponse = jsonVerboseResponse;
    }
}
