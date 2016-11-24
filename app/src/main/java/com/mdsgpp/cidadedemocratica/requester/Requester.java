package com.mdsgpp.cidadedemocratica.requester;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Requester {

    private static String userToken = null;

    public enum RequestMethod {
        POST, GET, PATCH, DELETE
    }

    public enum ValidProtocol {
        FTP, HTTP, HTTPS
    }

    private Thread requestThread = Thread.currentThread();
    private String url = "";
    private HashMap<String, String> parameters = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<>();
    private RequestResponseHandler responseHandler;



    public Requester(String url, RequestResponseHandler responseHandler) {
        this.url = url;
        this.responseHandler = responseHandler;

    }

    private void requestSync(RequestMethod method) {
        try {

            URL url = getUrl();

            HttpURLConnection urlConnection = getHttpURLConnection(url, method);

            putHeaders(urlConnection);


            if (method == RequestMethod.POST) {
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(getParameters());
                wr.flush();
                wr.close();
            } else {
                urlConnection.connect();
            }

            String jsonString = getResponseString(urlConnection);

            JSONArray ja = null;
            JSONObject jo = null;

            if (jsonString.charAt(0) == '[') { //JSONArray

                ja = new JSONArray(jsonString);
            } else if (jsonString.charAt(0) == '{') { //JSONObject

                jo = new JSONObject(jsonString);
            }

            int responseCode = urlConnection.getResponseCode();

            Map<String, List<String>> headers = urlConnection.getHeaderFields();

            boolean isSuccess = responseCode >= 200 && responseCode - 200 < 100;
            if (isSuccess) {
                if (ja != null) {
                    responseHandler.onSuccess(responseCode, headers, ja);
                } else if (jo != null) {
                    responseHandler.onSuccess(responseCode, headers, jo);
                } else {
                    responseHandler.onSuccess(responseCode, headers, jsonString);
                }
            } else {
                responseHandler.onFailure(responseCode, headers, ja);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            responseHandler.onFailure(500, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            //Not a json received
        }
    }

    @NonNull
    private String getResponseString(HttpURLConnection urlConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
            System.out.println("loading...");
        }
        System.out.println("loaded...");
        br.close();

        return sb.toString();
    }

    private void putHeaders(HttpURLConnection urlConnection) {
        if (userToken != null) {
            headers.put("Authorization", userToken);
        }
        for (String key : headers.keySet()) {
            urlConnection.setRequestProperty(key, headers.get(key));
        }
    }

    private void requestAsync(final RequestMethod method) {
        requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                requestSync(method);
            }
        });

        requestThread.start();
    }

    public void sync(RequestMethod method) {
        requestSync(method);
    }

    public void async(RequestMethod method) {
        requestAsync(method);
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection(URL url, RequestMethod method) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(getRequestMethodDescription(method));

        boolean doesOutput = false;

        switch (method) {
            case GET:
                doesOutput = false;
                break;
            case POST:
                doesOutput = true;
                break;
        }
        urlConnection.setDoOutput(doesOutput);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);


        return urlConnection;
    }

    public static String getRequestMethodDescription(RequestMethod method) {
        String description = "";
        switch (method) {
            case GET:
                description = "GET";
                break;
            case POST:
                description = "POST";
                break;
            case PATCH:
                description = "PATCH";
                break;
            case DELETE:
                description = "DELETE";
                break;
        }
        return description;
    }

    public static String getProtocolDescription(ValidProtocol protocol) {
        String description = "";
        switch (protocol) {
            case FTP:
                description = "ftp";
                break;
            case HTTP:
                description = "http";
                break;
            case HTTPS:
                description = "https";
                break;
        }
        return description;
    }

    public URL getUrl() throws MalformedURLException {

        String purl = this.url;

        String prefix = "?";
        String parameters = getParameters();
        if (parameters.length() > 0) {
            purl += prefix + parameters;
        }

        URL connectionUrl = new URL(purl);

        return connectionUrl;
    }

    public String getParameters() {

        String urlParameters = "";
        String prefix = "";

        for (String key : parameters.keySet()) {
            urlParameters += prefix + key + "=" + parameters.get(key);
            prefix = "&";
        }

        return urlParameters;
    }

    public void setParameter(String key, String parameter) {
        parameters.put(key, parameter);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public static String getUserToken() {
        return userToken;
    }

    public static void setUserToken(String userToken) {
        Requester.userToken = userToken;
    }
}
