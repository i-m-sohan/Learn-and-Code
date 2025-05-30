package com.InventorySystem.controller;

import com.InventorySystem.dao.impl.InMemoryProductDAO;
import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.service.ProductViewService;
import com.InventorySystem.service.ProductWriteService;
import com.InventorySystem.service.impl.ProductViewServiceImpl;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductViewController {

    @Autowired
    private ProductViewService productViewService;

    @GetMapping("/viewAll")
    public ResponseEntity<?> viewAllProduct(){
        List<ProductDTO> productDTOList = productViewService.getAllProduct();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable @Min(1) Integer id){
        ProductDTO productDTO = productViewService.getProductById(id);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }
}
