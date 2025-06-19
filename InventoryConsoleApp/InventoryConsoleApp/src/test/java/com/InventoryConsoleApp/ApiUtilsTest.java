package com.InventoryConsoleApp;

import com.InventoryConsoleApp.utils.ApiUtils;
import com.InventoryConsoleApp.wrapper.ApiRequestWrapper;
import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class ApiUtilsTest {

    @Test
    void testPrepareHttpRequest_WithGetMethod() {
        ApiRequestWrapper wrapper = new ApiRequestWrapper();
        wrapper.setMethod("GET");
        wrapper.setUrl("http://localhost:8080/test");

        HttpRequest request = ApiUtils.prepareHttpRequest(wrapper);

        assertEquals("GET", request.method());
        assertEquals("http://localhost:8080/test", request.uri().toString());
    }

    @Test
    void testPrepareHttpRequest_WithPostMethodAndBody() {
        ApiRequestWrapper wrapper = new ApiRequestWrapper();
        wrapper.setMethod("POST");
        wrapper.setUrl("http://localhost:8080/test");
        wrapper.setBody("{\"message\":\"Hello\"}");
        wrapper.getHeaders().put("Content-Type", "application/json");

        HttpRequest request = ApiUtils.prepareHttpRequest(wrapper);

        assertEquals("POST", request.method());
        assertEquals("http://localhost:8080/test", request.uri().toString());
        assertTrue(request.headers().map().containsKey("Content-Type"));
    }

    @Test
    void testPrepareHttpRequest_WithUnsupportedMethod_ShouldThrowException() {
        ApiRequestWrapper wrapper = new ApiRequestWrapper();
        wrapper.setMethod("TRACE");
        wrapper.setUrl("http://localhost:8080/test");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ApiUtils.prepareHttpRequest(wrapper);
        });

        assertEquals("Unsupported HTTP method: TRACE", exception.getMessage());
    }

    @Test
    void testFetchApiResponse_ShouldReturnHttpResponse() throws Exception {
        ApiRequestWrapper wrapper = new ApiRequestWrapper();
        wrapper.setMethod("GET");
        wrapper.setUrl("http://localhost:8080/api/products/viewAll");

        HttpResponse<String> response = ApiUtils.fetchApiResponse(wrapper);

        assertNotNull(response);
        assertTrue(response.statusCode() >= 200 && response.statusCode() < 500); // Accepting any known response
    }
}
