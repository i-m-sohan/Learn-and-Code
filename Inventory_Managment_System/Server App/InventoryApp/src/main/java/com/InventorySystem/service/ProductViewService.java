package com.InventorySystem.service;

import com.InventorySystem.dto.ProductDTO;

import java.util.List;

public interface ProductViewService {
    public List<ProductDTO> getAllProduct();
    public ProductDTO getProductById(Integer id);
}
