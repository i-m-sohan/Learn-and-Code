package com.InventorySystem.controller;

import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.exception.ResourceNotFoundException;
import com.InventorySystem.service.ProductViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductViewController.class)
public class ProductViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductViewService productViewService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ProductDTO sampleProduct() {
        ProductDTO dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Router");
        dto.setQuantity(10);
        return dto;
    }

    @Test
    void viewAllProduct_shouldReturnProductList_whenAvailable() throws Exception {
        List<ProductDTO> products = List.of(sampleProduct());
        when(productViewService.getAllProduct()).thenReturn(products);

        mockMvc.perform(get("/api/products/viewAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getProductById_shouldReturnProduct_whenFound() throws Exception {
        when(productViewService.getProductById(1)).thenReturn(sampleProduct());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Router"));
    }

    @Test
    void getProductById_shouldReturnBadRequest_whenIdIsZero() throws Exception {
        mockMvc.perform(get("/api/products/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductById_shouldReturnNotFound_whenNotFound() throws Exception {
        when(productViewService.getProductById(99)).thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductById_shouldHandleMalformedJson() throws Exception {
        String invalidJson = "{ id: , name: Router }";

        mockMvc.perform(
                get("/api/products/1")
                        .content(invalidJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
