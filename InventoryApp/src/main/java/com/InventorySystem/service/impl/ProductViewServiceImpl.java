package com.InventorySystem.service.impl;

import com.InventorySystem.dao.ProductDAO;
import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.exception.DuplicateIdException;
import com.InventorySystem.exception.ResourceNotFoundException;
import com.InventorySystem.mapper.ProductMapper;
import com.InventorySystem.model.Product;
import com.InventorySystem.service.ProductViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductViewServiceImpl implements ProductViewService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProduct(){
        List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();

        try {
            List<Product> productList = productDAO.getAll();
            for (Product product : productList) {
                ProductDTO productDTO = productMapper.toDTO(product);
                productDTOList.add(productDTO);
            }
            return productDTOList;
        }
        catch(DatabaseException databaseException){
            throw new DatabaseException("Issue occured while retrieving all products!");
        }
    }

    @Override
    public ProductDTO getProductById(Integer id){
        Optional<Product> optionalProduct;
        try {
            optionalProduct = productDAO.findById(id);
        }
        catch(DatabaseException databaseException){
            throw new DatabaseException(databaseException.getMessage());
        }

        if (!optionalProduct.isPresent()) {
            String message = "Product with id " + id + " is not Found";
            throw new ResourceNotFoundException(message);
        }

        ProductDTO productDTO = productMapper.toDTO(optionalProduct.get());
        return productDTO;
    }
}
