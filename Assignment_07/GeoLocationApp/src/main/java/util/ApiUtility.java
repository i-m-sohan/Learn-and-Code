package util;

import constant.GeocodingConstants;
import exception.LocationNotFoundException;
import exception.RequestConnectionException;
import wrapper.ApiRequestWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ApiUtility {

    public static String sendRequest(ApiRequestWrapper requestWrapper) throws Exception {
        HttpURLConnection requestConnection = prepareRequestConnection(requestWrapper);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestConnection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static HttpURLConnection prepareRequestConnection(ApiRequestWrapper requestWrapper){
        try {
            URL url = new URL(requestWrapper.getUrl());
            HttpURLConnection requestConnection = (HttpURLConnection) url.openConnection();
            requestConnection.setRequestMethod(requestWrapper.getMethod());
            for (Map.Entry<String, String> header : requestWrapper.getHeaders().entrySet()) {
                requestConnection.setRequestProperty(header.getKey(), header.getValue());
            }
            return requestConnection;
        }
        catch(Exception excep){
            System.out.println(excep.getMessage() + "\n" +excep.getStackTrace());
            throw new RequestConnectionException("Exception occured while preparing request connection"+"\n"+
                    "Message : " + excep.getMessage()+"\n"+
                    "Base-URL : " + GeocodingConstants.BASE_URL + "\n"+
                    "Method : " + requestWrapper.getMethod() + "\n"+
                    "Header : " + requestWrapper.getHeaders().toString()
            );
        }
    }
}