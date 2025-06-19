package com.InventorySystem.controller;

import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.service.ProductWriteService;
import com.InventorySystem.validate.OnCreate;
import com.InventorySystem.validate.OnStockUpdate;
import com.InventorySystem.validate.OnUpdate;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductWriteController {

    @Autowired
    private ProductWriteService productWriteService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Validated(OnCreate.class) @RequestBody ProductDTO productDTO){
        this.productWriteService.addProduct(productDTO);
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("message","Product created successfully.");
        return new ResponseEntity<>(responseBody,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable @Min(1) Integer id,@RequestBody @Validated(OnUpdate.class) ProductDTO productDTO){
        productWriteService.updateProduct(id,productDTO);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateProductStock(@PathVariable @Min(1) Integer id, @Validated(OnStockUpdate.class) @RequestBody ProductDTO productDTO) {
        productWriteService.updateStock(id,productDTO.getQuantity());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product stock updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
