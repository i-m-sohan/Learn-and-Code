package com.InventorySystem.service;

import com.InventorySystem.dto.ProductDTO;

public interface ProductWriteService {
    public void addProduct(ProductDTO productDTO);
    public void updateProduct(Integer id,ProductDTO productDTO);
    public void updateStock(Integer id, Integer quantity);
}
