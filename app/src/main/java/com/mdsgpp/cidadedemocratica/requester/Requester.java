package com.mdsgpp.cidadedemocratica.requester;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
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

    public enum RequestMethod {
        POST, GET, PATCH, DELETE
    }

    public enum ValidProtocol {
        FTP, HTTP, HTTPS
    }

    private Thread requestThread = Thread.currentThread();
    private String url = "";
    private HashMap<String, String> parameters = new HashMap<>();
    private RequestResponseHandler responseHandler;

    public Requester(String url, RequestResponseHandler responseHandler) {
        this.url = url;
        this.responseHandler = responseHandler;

    }

    private void requestSync(RequestMethod method) {
        try {
            URL url = new URL(getUrlWithParameters());

            HttpURLConnection urlConnection = getHttpURLConnection(url, method);

            urlConnection.setRequestProperty("Authorization", "");
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println("loading...");
            }
            System.out.println("loaded...");
            br.close();

            String jsonString = sb.toString();

            JSONArray ja = new JSONArray(jsonString);

            int responseCode = urlConnection.getResponseCode();

            Map<String, List<String>> headers = urlConnection.getHeaderFields();

            if (responseCode >= 200 && responseCode - 200 < 100) {
                responseHandler.onSuccess(responseCode, headers, ja);
            } else {
                responseHandler.onFailure(responseCode, headers, ja);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            responseHandler.onFailure(500, null, null);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void getSync() {
        requestSync(RequestMethod.GET);
    }

    public void postAsync() {
        requestAsync(RequestMethod.POST);
    }

    public void postSync() {
        requestSync(RequestMethod.POST);
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection(URL url, RequestMethod method) throws IOException {
        HttpURLConnection urlConnection = null;
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(getRequestMethodDescription(method));
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(false);

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

    public String getUrlWithParameters() {
        String purl = this.url;

        String prefix = "?";
        for (String key : parameters.keySet()) {
            purl += prefix + key + "=" + parameters.get(key);
            prefix = "&";
        }

        return purl;
    }

    public void setParameter(String key, String parameter) {
        parameters.put(key, parameter);
    }
}
