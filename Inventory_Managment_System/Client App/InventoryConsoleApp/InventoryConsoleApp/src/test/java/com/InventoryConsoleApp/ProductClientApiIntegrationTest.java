package com.InventoryConsoleApp;
import com.InventoryConsoleApp.client.ProductClientApi;
import com.InventoryConsoleApp.utils.ApiUtils;
import com.InventoryConsoleApp.wrapper.ApiRequestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class ProductClientApiIntegrationTest {

    private ProductClientApi clientApi;

    @BeforeEach
    void setUp() {
        clientApi = new ProductClientApi();
    }

    @Test
    void testCreateProduct_Successful() throws Exception {
        HttpResponse<String> response = clientApi.createProduct(827635, "Mouse", 10);
        assertEquals(201, response.statusCode());
    }

    @Test
    void testViewAllProducts_Successful() throws Exception {
        HttpResponse<String> response = clientApi.viewAllProducts();
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Mouse"));
    }

    @Test
    void testGetProductById_Successful() throws Exception {
        HttpResponse<String> response = clientApi.getProductById(101);
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Mouse"));
    }

    @Test
    void testUpdateProduct_Successful() throws Exception {
        HttpResponse<String> response = clientApi.updateProduct(101, "Wireless Mouse", 15);
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Wireless Mouse"));
    }

    @Test
    void testUpdateStock_Successful() throws Exception {
        HttpResponse<String> response = clientApi.updateStock(101, 20);
        assertEquals(200, response.statusCode());
    }

    @Test
    void testCreateProduct_MissingName_ShouldReturnBadRequest() throws Exception {
        HttpResponse<String> response = clientApi.createProduct(102, "", 5);
        assertEquals(400, response.statusCode(), "Expected 400 Bad Request for missing name");
    }

    @Test
    void testGetProductById_NotFound_ShouldReturn404() throws Exception {
        HttpResponse<String> response = clientApi.getProductById(10011);
        assertEquals(404, response.statusCode(), "Expected 404 Not Found for non-existing ID");
    }
}
