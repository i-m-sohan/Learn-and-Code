package com.InventorySystem.dao;

import com.InventorySystem.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    public void save(Product product);
    public List<Product> getAll();
    public Optional<Product> findById(Integer id);
}
