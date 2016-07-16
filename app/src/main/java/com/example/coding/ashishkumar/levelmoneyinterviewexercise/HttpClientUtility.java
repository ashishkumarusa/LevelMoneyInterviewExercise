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
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates methods for requesting a server via HTTP GET/POST and
 * provides methods for parsing response from the server.
 *
 * @author www.codejava.net
 */
public class HttpClientUtility {

    /**
     * Represents an HTTP connection
     */
    private static HttpURLConnection httpConn;

    /**
     * Makes an HTTP request using GET method to the specified URL.
     *
     * @param requestURL the URL of the remote server
     * @return An HttpURLConnection object
     * @throws IOException thrown if any I/O error occurred
     */
    public static HttpURLConnection makeGetRequest(String requestURL)
            throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true); // true if we want to read server's response
        httpConn.setDoOutput(false); // false indicates this is a GET request

        return httpConn;
    }

    /**
     * Makes an HTTP request using POST method to the specified URL.
     *
     * @param requestURL the URL of the remote server
     * @return An HttpURLConnection object
     * @throws IOException thrown if any I/O error occurred
     */
    public static HttpURLConnection makePostRequest(String requestURL, JSONObject jsonObject) throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type","application/json");
        httpConn.setConnectTimeout(10000);
        httpConn.setReadTimeout(10000);
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true); // true indicates the server returns response
        httpConn.setDoOutput(true); // true indicates POST request

        // sends POST data
        OutputStreamWriter writer = new OutputStreamWriter(
                httpConn.getOutputStream());
        writer.write(jsonObject.toString());

        Log.v("Ashish jsonObject==",jsonObject.toString());
        writer.flush();
        writer.close();

        return httpConn;
    }

    /**
     * Returns only one line from the server's response. This method should be
     * used if the server returns only a single line of String.
     *
     * @return a String of the server's response
     * @throws IOException thrown if any I/O error occurred
     */
    public static String readSingleLineRespone() throws IOException {
        int HttpResult =httpConn.getResponseCode();
        Log.v("Ashish responseCode==",HttpResult+"");
        InputStream inputStream = null;
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

    /**
     * Returns an array of lines from the server's response. This method should
     * be used if the server returns multiple lines of String.
     *
     * @return an array of Strings of the server's response
     * @throws IOException thrown if any I/O error occurred
     */
    public static String[] readMultiLinesRespone() throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        List<String> response = new ArrayList<String>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();

        return (String[]) response.toArray(new String[0]);
    }

    /**
     * Closes the connection if opened
     */
    public static void disconnect() {
        if (httpConn != null) {
            httpConn.disconnect();
        }
    }
}