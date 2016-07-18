package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientUtility {

    private static HttpURLConnection httpConn;

    public static HttpURLConnection makePostRequest(String requestURL, JSONObject jsonObject) throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Accept", "application/json");
        httpConn.setConnectTimeout(10000);
        httpConn.setReadTimeout(10000);
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true); // true indicates the server returns response
        httpConn.setDoOutput(true); // true indicates POST request

        OutputStreamWriter writer = new OutputStreamWriter(
                httpConn.getOutputStream());
        writer.write(jsonObject.toString());

        writer.flush();
        writer.close();

        return httpConn;
    }

    public static String readSingleLineRespone() throws IOException {
        int HttpResult = httpConn.getResponseCode();
        Log.v("Ashish responseCode==", HttpResult + "");
        InputStream inputStream;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));

        String response = reader.readLine();
        reader.close();

        return response;
    }

    public static void disconnect() {
        if (httpConn != null) {
            httpConn.disconnect();
        }
    }
}