package com.InventoryConsoleApp.utils;

import com.InventoryConsoleApp.wrapper.ApiRequestWrapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ApiUtils {

    public static HttpResponse<String> fetchApiResponse(ApiRequestWrapper apiRequestWrapper) throws IOException, InterruptedException {
        HttpRequest httpRequest = prepareHttpRequest(apiRequestWrapper);
        HttpResponse<String> response = makApiCall(httpRequest);
        return response;
    }

    public static HttpResponse<String> makApiCall(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public static HttpRequest prepareHttpRequest(ApiRequestWrapper apiRequestWrapper) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(apiRequestWrapper.getUrl()));

        Map<String, String> headers = apiRequestWrapper.getHeaders();
        String method = apiRequestWrapper.getMethod();
        String url = apiRequestWrapper.getUrl();
        String jsonBody = apiRequestWrapper.getBody();

        if (headers != null) {
            headers.forEach(builder::header);
        }

        switch (method.toUpperCase()) {
            case "GET":
                builder.GET();
                break;
            case "POST":
                builder.POST(HttpRequest.BodyPublishers.ofString(jsonBody != null ? jsonBody : ""));
                break;
            case "PUT":
                builder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody != null ? jsonBody : ""));
                break;
            case "PATCH":
                builder.method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody != null ? jsonBody : ""));
                break;
            case "DELETE":
                builder.DELETE();
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return builder.build();
    }
}
