package com.InventorySystem.dao.impl;

import com.InventorySystem.dao.ProductDAO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.model.Product;
import org.springframework.boot.context.config.InactiveConfigDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Repository
public class InMemoryProductDAO implements ProductDAO {
    static Map<Integer, Product> idToProductMap;

    static{
        idToProductMap = new HashMap<>();
    }

    @Override
    public void save(Product product) {
        idToProductMap.put(product.getId(),product);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(idToProductMap.values());
    }

    @Override
    public Optional<Product> findById(Integer id) {
        try {
            return Optional.ofNullable(idToProductMap.get(id));
        }
        catch(Exception excep){
            throw new DatabaseException("Error Occured while finding Id In Database",excep);
        }
    }
}
