package com.InventorySystem.mapper;


import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper{

    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setQuantity(product.getQuantity());
        return dto;
    }

//    public void merge(ProductDTO dto, Product target) {
//        if (dto == null || target == null) return;
//
//        if (dto.getName() != null) {
//            target.setName(dto.getName());
//        }
//        if (dto.getQuantity() != null) {
//            target.setQuantity(dto.getQuantity());
//        }
//    }
}
