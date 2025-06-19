package com.InventorySystem.service.impl;

import com.InventorySystem.dao.ProductDAO;
import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.exception.DuplicateIdException;
import com.InventorySystem.exception.ResourceNotFoundException;
import com.InventorySystem.mapper.ProductMapper;
import com.InventorySystem.model.Product;
import com.InventorySystem.service.ProductViewService;
import com.InventorySystem.service.ProductWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductWriteServiceImpl implements ProductWriteService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProduct(ProductDTO productDTO) {
        Optional<Product> productContainer;
        try {
            productContainer = productDAO.findById(productDTO.getId());
        }
        catch(DatabaseException databaseException){
            throw new DatabaseException(databaseException.getMessage());
        }

        if (productContainer.isPresent()) {
            String message = "Product with id " + productDTO.getId() + " already present";
            throw new DuplicateIdException(message);
        }

        Product product = productMapper.toEntity(productDTO);
        productDAO.save(product);
    }

    @Override
    public void updateProduct(Integer id, ProductDTO productDTO) {
        Optional<Product> productContainer;
        try {
            productContainer = productDAO.findById(id);
        }
        catch(DatabaseException databaseException){
            throw new DatabaseException(databaseException.getMessage());
        }

        if (!productContainer.isPresent()) {
            String message = "Product with id " + productDTO.getId() + " not Found";
            throw new ResourceNotFoundException(message);
        }

        productDTO.setId(id);
        Product product = productMapper.toEntity(productDTO);
        productDAO.save(product);
    }

    @Override
    public void updateStock(Integer id, Integer quantity){
        Optional<Product> productContainer;
        try {
            productContainer = productDAO.findById(id);
        }
        catch(DatabaseException databaseException){
            throw new DatabaseException(databaseException.getMessage());
        }

        if (!productContainer.isPresent()) {
            String message = "Product with id " + id + " not Found";
            throw new ResourceNotFoundException(message);
        }

        ProductDTO productDTO = productMapper.toDTO(productContainer.get());
        productDTO.setQuantity(quantity);

        Product product = productMapper.toEntity(productDTO);
        productDAO.save(product);
    }
}
