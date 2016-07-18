package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

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

    public static String getTransactionsResponse(String requestURL, JSONObject jsonObject) throws IOException {
        try {
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
            OutputStreamWriter writer = null;
            try {
                writer = new OutputStreamWriter(
                        httpConn.getOutputStream());
                writer.write(jsonObject.toString());


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String response = "";
        InputStream inputStream;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("connection is not successful.");
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            response = reader.readLine();

        } finally {
            if (reader != null)
                reader.close();
        }

        return response;
    }
}