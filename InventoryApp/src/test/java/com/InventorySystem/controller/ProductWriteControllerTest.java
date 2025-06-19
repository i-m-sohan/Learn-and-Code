package com.InventorySystem.controller;

import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.service.ProductWriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductWriteController.class)
class ProductWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductWriteService productWriteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct_ValidInput_ShouldReturn201() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1);
        productDTO.setName("Laptop");
        productDTO.setQuantity(10);

        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Product created successfully."));
    }

    @Test
    void testCreateProduct_InvalidInput_ShouldReturn400() throws Exception {
        ProductDTO productDTO = new ProductDTO();

        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid Values"));
    }

    @Test
    void testUpdateProduct_ValidInput_ShouldReturn200() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Name");
        productDTO.setQuantity(20);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testUpdateProduct_InvalidId_ShouldReturn400() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Name");
        productDTO.setQuantity(20);

        mockMvc.perform(put("/api/products/0") // ID is invalid (must be ≥ 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateStock_ValidInput_ShouldReturn200() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setQuantity(15);

        mockMvc.perform(patch("/api/products/1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product stock updated successfully."));
    }

    @Test
    void testUpdateStock_InvalidQuantity_ShouldReturn400() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setQuantity(-1);

        mockMvc.perform(patch("/api/products/1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateStock_InvalidId_ShouldReturn400() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setQuantity(5);

        mockMvc.perform(patch("/api/products/0/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }
}
