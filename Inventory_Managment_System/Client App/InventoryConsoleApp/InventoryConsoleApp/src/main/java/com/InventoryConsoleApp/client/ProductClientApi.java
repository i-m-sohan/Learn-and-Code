package com.InventoryConsoleApp.client;


import com.InventoryConsoleApp.utils.ApiUtils;
import com.InventoryConsoleApp.wrapper.ApiRequestWrapper;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class ProductClientApi {
    private final int CONNECT_TIMEOUT = 5000;
    private final int READ_TIMEOUT = 5000;
    private static final String BASE_URL = "http://localhost:8080/api/products";
    private final HttpClient client;

    public ProductClientApi() {
        this.client = HttpClient.newHttpClient();
    }

    public HttpResponse<String> viewAllProducts() throws Exception {
        ApiRequestWrapper apiRequestWrapper = new ApiRequestWrapper();
        apiRequestWrapper.setMethod("GET");
        apiRequestWrapper.setUrl(BASE_URL + "/viewAll");
        HttpResponse<String> response = ApiUtils.fetchApiResponse(apiRequestWrapper);
        return response;
    }

    public HttpResponse<String> getProductById(int id) throws Exception {
        ApiRequestWrapper apiRequestWrapper = new ApiRequestWrapper();
        apiRequestWrapper.setMethod("GET");
        apiRequestWrapper.setUrl(BASE_URL + "/" + id);
        HttpResponse<String> response = ApiUtils.fetchApiResponse(apiRequestWrapper);
        return response;
    }

    public HttpResponse<String> createProduct(int id, String name, int quantity) throws Exception {
        String jsonBody = String.format("""
            {
              "id": %d,
              "name": "%s",
              "quantity": %d
            }
        """, id, name, quantity);

        ApiRequestWrapper apiRequestWrapper = new ApiRequestWrapper();
        apiRequestWrapper.setMethod("POST");
        apiRequestWrapper.setUrl(BASE_URL + "/create");
        apiRequestWrapper.setBody(jsonBody);
        apiRequestWrapper.getHeaders().put("Content-Type", "application/json");

        HttpResponse<String> response = ApiUtils.fetchApiResponse(apiRequestWrapper);
        return response;
    }

    public HttpResponse<String> updateProduct(int id, String name, int quantity) throws Exception {
        String jsonBody = String.format("""
            {
              "name": "%s",
              "quantity": %d
            }
        """, name, quantity);

        ApiRequestWrapper apiRequestWrapper = new ApiRequestWrapper();
        apiRequestWrapper.setMethod("PUT");
        apiRequestWrapper.setUrl(BASE_URL + "/" + id);
        apiRequestWrapper.setBody(jsonBody);
        apiRequestWrapper.getHeaders().put("Content-Type", "application/json");

        HttpResponse<String> response = ApiUtils.fetchApiResponse(apiRequestWrapper);
        return response;
    }

    public HttpResponse<String> updateStock(int id, int newStock) throws Exception {
        String jsonBody = String.format("""
            {
              "quantity": %d
            }
        """, newStock);

        ApiRequestWrapper apiRequestWrapper = new ApiRequestWrapper();
        apiRequestWrapper.setMethod("PATCH");
        apiRequestWrapper.setUrl(BASE_URL + "/" + id + "/stock");
        apiRequestWrapper.setBody(jsonBody);
        apiRequestWrapper.getHeaders().put("Content-Type", "application/json");

        HttpResponse<String> response = ApiUtils.fetchApiResponse(apiRequestWrapper);
        return response;
    }
}
